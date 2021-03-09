package com.kt289.cache;

import java.io.IOException;
import java.io.RandomAccessFile;

public class Index {

    private final byte[] buffer = new byte[520];
    private final RandomAccessFile dataFile;
    private final RandomAccessFile indexFile;
    private final int storeId;
    private final int maxSize;

    public Index(RandomAccessFile dataFile, int maxSize, RandomAccessFile indexFile, int storeId) {
        this.storeId = storeId;
        this.dataFile = dataFile;
        this.indexFile = indexFile;
        this.maxSize = maxSize;
    }

    public synchronized byte[] decompress(int index) {
        try {
            seek(indexFile, index * 6);
            int in;
            for (int i = 0; i < 6; i += in) {
                in = indexFile.read(buffer, i, 6 - i);
                if (in == -1) {
                    return null;
                }
            }
            int size = ((buffer[0] & 0xff) << 16) + ((buffer[1] & 0xff) << 8) + (buffer[2] & 0xff);
            int sector = ((buffer[3] & 0xff) << 16) + ((buffer[4] & 0xff) << 8) + (buffer[5] & 0xff);
            if (size > maxSize) {
                return null;
            }
            if (sector <= 0 || sector > dataFile.length() / 520L) {
                return null;
            }
            byte[] decompressed = new byte[size];
            int read = 0;
            for (int part = 0; read < size; part++) {
                if (sector == 0) {
                    return null;
                }
                seek(dataFile, sector * 520);
                int i = 0;
                int unread = size - read;
                if (unread > 512) {
                    unread = 512;
                }
                int i2;
                for (; i < unread + 8; i += i2) {
                    i2 = dataFile.read(buffer, i, (unread + 8) - i);
                    if (i2 == -1) {
                        return null;
                    }
                }
                int decompressedIndex = ((buffer[0] & 0xff) << 8) + (buffer[1] & 0xff);
                int decompressedPart = ((buffer[2] & 0xff) << 8) + (buffer[3] & 0xff);
                int decompressedSector = ((buffer[4] & 0xff) << 16) + ((buffer[5] & 0xff) << 8) + (buffer[6] & 0xff);
                int decompressedStoreId = buffer[7] & 0xff;
                if (decompressedIndex != index || decompressedPart != part || decompressedStoreId != storeId) {
                    return null;
                }
                if (decompressedSector < 0 || decompressedSector > dataFile.length() / 520L) {
                    return null;
                }
                for (int r = 0; r < unread; r++) {
                    decompressed[read++] = buffer[r + 8];
                }
                sector = decompressedSector;
            }
            return decompressed;
        } catch (IOException exception) {
            return null;
        }
    }

    public synchronized void put(byte[] data, int size, int index) {
        boolean flag = put(true, size, index, data);
        if (!flag) {
            put(false, size, index, data);
        }
    }

    private synchronized boolean put(boolean exists, int size, int index, byte[] data) {
        try {
            int sector;
            if (exists) {
                seek(indexFile, index * 6);
                int in;
                for (int i = 0; i < 6; i += in) {
                    in = indexFile.read(buffer, i, 6 - i);
                    if (in == -1) {
                        return false;
                    }
                }
                sector = ((buffer[3] & 0xff) << 16) + ((buffer[4] & 0xff) << 8) + (buffer[5] & 0xff);
                if (sector <= 0 || sector > dataFile.length() / 520L) {
                    return false;
                }
            } else {
                sector = (int) ((dataFile.length() + 519L) / 520L);
                if (sector == 0) {
                    sector = 1;
                }
            }
            buffer[0] = (byte) (size >> 16);
            buffer[1] = (byte) (size >> 8);
            buffer[2] = (byte) size;
            buffer[3] = (byte) (sector >> 16);
            buffer[4] = (byte) (sector >> 8);
            buffer[5] = (byte) sector;
            seek(indexFile, index * 6);
            indexFile.write(buffer, 0, 6);
            int written = 0;
            for (int part = 0; written < size; part++) {
                int decompressedSector = 0;
                if (exists) {
                    seek(dataFile, sector * 520);
                    int read;
                    int in;
                    for (read = 0; read < 8; read += in) {
                        in = dataFile.read(buffer, read, 8 - read);
                        if (in == -1) {
                            break;
                        }
                    }
                    if (read == 8) {
                        int decompressedIndex = ((buffer[0] & 0xff) << 8) + (buffer[1] & 0xff);
                        int decompressedPart = ((buffer[2] & 0xff) << 8) + (buffer[3] & 0xff);
                        decompressedSector = ((buffer[4] & 0xff) << 16) + ((buffer[5] & 0xff) << 8) + (buffer[6] & 0xff);
                        int decompressedStoreId = buffer[7] & 0xff;
                        if (decompressedIndex != index || decompressedPart != part || decompressedStoreId != storeId) {
                            return false;
                        }
                        if (decompressedSector < 0 || decompressedSector > dataFile.length() / 520L) {
                            return false;
                        }
                    }
                }
                if (decompressedSector == 0) {
                    exists = false;
                    decompressedSector = (int) ((dataFile.length() + 519L) / 520L);
                    if (decompressedSector == 0) {
                        decompressedSector++;
                    }
                    if (decompressedSector == sector) {
                        decompressedSector++;
                    }
                }
                if (size - written <= 512) {
                    decompressedSector = 0;
                }
                buffer[0] = (byte) (index >> 8);
                buffer[1] = (byte) index;
                buffer[2] = (byte) (part >> 8);
                buffer[3] = (byte) part;
                buffer[4] = (byte) (decompressedSector >> 16);
                buffer[5] = (byte) (decompressedSector >> 8);
                buffer[6] = (byte) decompressedSector;
                buffer[7] = (byte) storeId;
                seek(dataFile, sector * 520);
                dataFile.write(buffer, 0, 8);
                int unwritten = size - written;
                if (unwritten > 512) {
                    unwritten = 512;
                }
                dataFile.write(data, written, unwritten);
                written += unwritten;
                sector = decompressedSector;
            }
            return true;
        } catch (IOException exception) {
            return false;
        }
    }

    private synchronized void seek(RandomAccessFile file, int position) throws IOException {
        if (position < 0 || position > 0x3c00000) {
            System.out.println("Badseek - pos:" + position + " len:" + file.length());
            position = 0x3c00000;
            try {
                Thread.sleep(1000L);
            } catch (Exception ignored) {
            }
        }
        file.seek(position);
    }

}
