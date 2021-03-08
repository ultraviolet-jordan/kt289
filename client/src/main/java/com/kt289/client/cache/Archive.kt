package com.kt289.client.cache

import com.kt289.bzip2.BZip2Decompressor
import com.kt289.util.buffer.Buffer

class Archive(data: ByteArray) {

    private lateinit var outputData: ByteArray
    private lateinit var hashes: IntArray
    private lateinit var decompressedSizes: IntArray
    private lateinit var compressedSizes: IntArray
    private lateinit var initialOffsets: IntArray

    private var fileCount = 0
    private var decompressed = false

    private fun decompress(data: ByteArray) {
        var buffer = Buffer(data)
        val compressedLength = buffer.readUnsignedTriByte()
        val decompressedLength = buffer.readUnsignedTriByte()
        if (decompressedLength != compressedLength) {
            val output = ByteArray(compressedLength)
            BZip2Decompressor.decompress(output, compressedLength, data, decompressedLength, 6)
            outputData = output
            buffer = Buffer(outputData)
            decompressed = true
        } else {
            outputData = data
            decompressed = false
        }
        fileCount = buffer.readUnsignedShort()
        hashes = IntArray(fileCount)
        decompressedSizes = IntArray(fileCount)
        compressedSizes = IntArray(fileCount)
        initialOffsets = IntArray(fileCount)
        var offset = buffer.offset + fileCount * 10
        (0 until fileCount).forEach {
            hashes[it] = buffer.readUnsignedInt()
            decompressedSizes[it] = buffer.readUnsignedTriByte()
            compressedSizes[it] = buffer.readUnsignedTriByte()
            initialOffsets[it] = offset
            offset += compressedSizes[it]
        }
    }

    fun decompressFile(name: String): ByteArray? {
        var hash = 0
        val fileName = name.toUpperCase()
        fileName.forEach {
            hash = hash * 61 + it.toInt() - 32
        }
        (0 until fileCount).forEach { file ->
            if (hashes[file] == hash) {
                val output = ByteArray(decompressedSizes[file])
                when {
                    !decompressed -> BZip2Decompressor.decompress(output, decompressedSizes[file], outputData, compressedSizes[file], initialOffsets[file])
                    else -> {
                        (0 until decompressedSizes[file]).forEach {
                            output[it] = outputData[initialOffsets[file] + it]
                        }
                    }
                }
                return output
            }
        }
        return null
    }

    init {
        decompress(data)
    }
}