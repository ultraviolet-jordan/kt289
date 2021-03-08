package com.kt289.client.cache

import java.io.IOException
import java.io.RandomAccessFile

class Index(
    private val dataFile: RandomAccessFile,
    private val maxSize: Int,
    private val indexFile: RandomAccessFile,
    private val storeId: Int
) {
    @Synchronized
    fun decompress(index: Int): ByteArray? {
        return try {
            seek(indexFile, index * 6)
            var start: Int
            var off = 0
            while (off < 6) {
                start = indexFile.read(buffer, off, 6 - off)
                if (start == -1) {
                    return null
                }
                off += start
            }
            val size = (buffer[0].toInt() and 0xff shl 16) + (buffer[1].toInt() and 0xff shl 8) + (buffer[2].toInt() and 0xff)
            var sector = (buffer[3].toInt() and 0xff shl 16) + (buffer[4].toInt() and 0xff shl 8) + (buffer[5].toInt() and 0xff)
            when {
                size < 0 || size > maxSize -> {
                    return null
                }
                sector <= 0 || sector > dataFile.length() / 520L -> {
                    return null
                }
                else -> {
                    val decompressed = ByteArray(size)
                    var read = 0
                    var part = 0
                    while (read < size) {
                        if (sector == 0) {
                            return null
                        }
                        seek(dataFile, sector * 520)
                        var dataOff = 0
                        var unread = size - read
                        if (unread > 512) {
                            unread = 512
                        }
                        var dataStart: Int
                        while (dataOff < unread + 8) {
                            dataStart = dataFile.read(buffer, dataOff, unread + 8 - dataOff)
                            if (dataStart == -1) {
                                return null
                            }
                            dataOff += dataStart
                        }
                        val decompressedIndex = (buffer[0].toInt() and 0xff shl 8) + (buffer[1].toInt() and 0xff)
                        val decompressedPart = (buffer[2].toInt() and 0xff shl 8) + (buffer[3].toInt() and 0xff)
                        val decompressedSector = (buffer[4].toInt() and 0xff shl 16) + (buffer[5].toInt() and 0xff shl 8) + (buffer[6].toInt() and 0xff)
                        val decompressedStoreId = buffer[7].toInt() and 0xff
                        when {
                            decompressedIndex != index || decompressedPart != part || decompressedStoreId != storeId -> {
                                return null
                            }
                            decompressedSector < 0 || decompressedSector > dataFile.length() / 520L -> {
                                return null
                            }
                            else -> {
                                (0 until unread).forEach {
                                    decompressed[read++] = buffer[it + 8]
                                }
                                sector = decompressedSector
                                part++
                            }
                        }
                    }
                    decompressed
                }
            }
        } catch (exception: IOException) {
            null
        }
    }

    @Synchronized
    fun put(data: ByteArray, size: Int, index: Int) {
        val flag = put(true, size, index, data)
        if (!flag) {
            put(false, size, index, data)
        }
    }

    @Synchronized
    private fun put(exists: Boolean, size: Int, index: Int, data: ByteArray): Boolean {
        var doesExist = exists
        return try {
            var sector: Int
            if (doesExist) {
                seek(indexFile, index * 6)
                var start: Int
                var off = 0
                while (off < 6) {
                    start = indexFile.read(buffer, off, 6 - off)
                    if (start == -1) {
                        return false
                    }
                    off += start
                }
                sector = (buffer[3].toInt() and 0xff shl 16) + (buffer[4].toInt() and 0xff shl 8) + (buffer[5].toInt() and 0xff)
                if (sector <= 0 || sector > dataFile.length() / 520L) {
                    return false
                }
            } else {
                sector = ((dataFile.length() + 519L) / 520L).toInt()
                if (sector == 0) {
                    sector = 1
                }
            }
            buffer[0] = (size shr 16).toByte()
            buffer[1] = (size shr 8).toByte()
            buffer[2] = size.toByte()
            buffer[3] = (sector shr 16).toByte()
            buffer[4] = (sector shr 8).toByte()
            buffer[5] = sector.toByte()
            seek(indexFile, index * 6)
            indexFile.write(buffer, 0, 6)
            var written = 0
            var part = 0
            while (written < size) {
                var decompressedSector = 0
                if (doesExist) {
                    seek(dataFile, sector * 520)
                    var dataStart: Int
                    var off = 0
                    while (off < 8) {
                        dataStart = dataFile.read(buffer, off, 8 - off)
                        if (dataStart == -1) {
                            break
                        }
                        off += dataStart
                    }
                    if (off == 8) {
                        val decompressedIndex = (buffer[0].toInt() and 0xff shl 8) + (buffer[1].toInt() and 0xff)
                        val decompressedPart = (buffer[2].toInt() and 0xff shl 8) + (buffer[3].toInt() and 0xff)
                        decompressedSector = (buffer[4].toInt() and 0xff shl 16) + (buffer[5].toInt() and 0xff shl 8) + (buffer[6].toInt() and 0xff)
                        val decompressedStoreId = buffer[7].toInt() and 0xff
                        when {
                            decompressedIndex != index || decompressedPart != part || decompressedStoreId != storeId -> {
                                return false
                            }
                            decompressedSector < 0 || decompressedSector > dataFile.length() / 520L -> {
                                return false
                            }
                        }
                    }
                }
                if (decompressedSector == 0) {
                    doesExist = false
                    decompressedSector = ((dataFile.length() + 519L) / 520L).toInt()
                    if (decompressedSector == 0) {
                        decompressedSector++
                    }
                    if (decompressedSector == sector) {
                        decompressedSector++
                    }
                }
                if (size - written <= 512) {
                    decompressedSector = 0
                }
                buffer[0] = (index shr 8).toByte()
                buffer[1] = index.toByte()
                buffer[2] = (part shr 8).toByte()
                buffer[3] = part.toByte()
                buffer[4] = (decompressedSector shr 16).toByte()
                buffer[5] = (decompressedSector shr 8).toByte()
                buffer[6] = decompressedSector.toByte()
                buffer[7] = storeId.toByte()
                seek(dataFile, sector * 520)
                dataFile.write(buffer, 0, 8)
                var unwritten = size - written
                if (unwritten > 512) {
                    unwritten = 512
                }
                dataFile.write(data, written, unwritten)
                written += unwritten
                sector = decompressedSector
                part++
            }
            true
        } catch (exception: IOException) {
            false
        }
    }

    @Synchronized
    @Throws(IOException::class)
    private fun seek(file: RandomAccessFile, position: Int) {
        var seekPosition = position
        if (seekPosition < 0 || seekPosition > 0x3c00000) {
            println("Badseek - pos:" + seekPosition + " len:" + file.length())
            seekPosition = 0x3c00000
            try {
                Thread.sleep(1000L)
            } catch (exception: Exception) {
            }
        }
        file.seek(seekPosition.toLong())
    }

    companion object {
        private val buffer = ByteArray(520)
    }
}