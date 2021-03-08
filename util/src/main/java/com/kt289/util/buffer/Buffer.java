package com.kt289.util.buffer;

import com.kt289.util.aggregation.CacheableNode;
import com.kt289.util.aggregation.LinkedList;
import com.kt289.isaac.ISAACRandom;

import java.math.BigInteger;

public class Buffer extends CacheableNode {

    private static int[] anIntArray1394;
    private static final int[] BIT_MASKS = {
            0, 1, 3, 7, 15, 31, 63, 127, 255, 511, 1023, 2047, 4095, 8191, 16383, 32767,
            65535, 0x1ffff, 0x3ffff, 0x7ffff, 0xfffff, 0x1fffff, 0x3fffff, 0x7fffff, 0xffffff,
            0x1ffffff, 0x3ffffff, 0x7ffffff, 0xfffffff, 0x1fffffff, 0x3fffffff, 0x7fffffff, -1};
    private static int cacheCountSmall;
    private static int cacheCountNormal;
    private static int cacheCountLarge;
    private static final LinkedList BUFFER_CACHE_SMALL = new LinkedList();
    private static final LinkedList BUFFER_CACHE_NORMAL = new LinkedList();
    private static final LinkedList BUFFER_CACHE_LARGE = new LinkedList();

    static {
        Buffer.anIntArray1394 = new int[256];
        for (int j = 0; j < 256; j++) {
            int i = j;
            for (int k = 0; k < 8; k++) {
                if ((i & 1) == 1) {
                    i = i >>> 1 ^ 0xedb88320;
                } else {
                    i >>>= 1;
                }
            }
            Buffer.anIntArray1394[j] = i;
        }
    }

    public byte[] payload;
    public int offset;
    public int bitOffset;
    public ISAACRandom encryptor;

    private Buffer() {
    }

    public Buffer(byte[] payload) {
        this.payload = payload;
        this.offset = 0;
    }

    public static Buffer create(int j) {
        synchronized (Buffer.BUFFER_CACHE_NORMAL) {
            Buffer buffer = null;
            if (j == 0 && Buffer.cacheCountSmall > 0) {
                Buffer.cacheCountSmall--;
                buffer = (Buffer) Buffer.BUFFER_CACHE_SMALL.pop();
            } else if (j == 1 && Buffer.cacheCountNormal > 0) {
                Buffer.cacheCountNormal--;
                buffer = (Buffer) Buffer.BUFFER_CACHE_NORMAL.pop();
            } else if (j == 2 && Buffer.cacheCountLarge > 0) {
                Buffer.cacheCountLarge--;
                buffer = (Buffer) Buffer.BUFFER_CACHE_LARGE.pop();
            }
            if (buffer != null) {
                buffer.offset = 0;
                Buffer copy = buffer;
                return copy;
            }
        }
        Buffer buffer = new Buffer();
        buffer.offset = 0;
        if (j == 0) {
            buffer.payload = new byte[100];
        } else if (j == 1) {
            buffer.payload = new byte[5000];
        } else {
            buffer.payload = new byte[30000];
        }
        return buffer;
    }

    public void writePacket(int value) {
        payload[offset++] = (byte) (value + encryptor.value());
    }

    public void writeByte(int value) {
        payload[offset++] = (byte) value;
    }

    public void writeShort(int value) {
        payload[offset++] = (byte) (value >> 8);
        payload[offset++] = (byte) value;
    }

    public void writeLEShort(int value) {
        payload[offset++] = (byte) value;
        payload[offset++] = (byte) (value >> 8);
    }

    public void writeTriByte(int value) {
        payload[offset++] = (byte) (value >> 16);
        payload[offset++] = (byte) (value >> 8);
        payload[offset++] = (byte) value;
    }

    public void writeInt(int value) {
        payload[offset++] = (byte) (value >> 24);
        payload[offset++] = (byte) (value >> 16);
        payload[offset++] = (byte) (value >> 8);
        payload[offset++] = (byte) value;
    }

    public void writeLEInt(int value) {
        payload[offset++] = (byte) value;
        payload[offset++] = (byte) (value >> 8);
        payload[offset++] = (byte) (value >> 16);
        payload[offset++] = (byte) (value >> 24);
    }

    public void writeLong(long value) {
        payload[offset++] = (byte) (int) (value >> 56);
        payload[offset++] = (byte) (int) (value >> 48);
        payload[offset++] = (byte) (int) (value >> 40);
        payload[offset++] = (byte) (int) (value >> 32);
        payload[offset++] = (byte) (int) (value >> 24);
        payload[offset++] = (byte) (int) (value >> 16);
        payload[offset++] = (byte) (int) (value >> 8);
        payload[offset++] = (byte) (int) value;
    }

    public void writeString(String value) {
        System.arraycopy(value.getBytes(), 0, payload, offset, value.length());
        offset += value.length();
        payload[offset++] = 10;
    }

    public void writeBytes(byte[] payload, int length, int start) {
        for (int index = start; index < start + length; index++) {
            this.payload[offset++] = payload[index];
        }
    }

    public void writeSizeByte(int i) {
        payload[offset - i - 1] = (byte) i;
    }

    public int readUnsignedByte() {
        return payload[offset++] & 0xff;
    }

    public byte readByte() {
        return payload[offset++];
    }

    public int readUnsignedShort() {
        offset += 2;
        return ((payload[offset - 2] & 0xff) << 8) + (payload[offset - 1] & 0xff);
    }

    public int readShort() {
        offset += 2;
        int value = ((payload[offset - 2] & 0xff) << 8) + (payload[offset - 1] & 0xff);
        if (value > 32767) {
            value -= 0x10000;
        }
        return value;
    }

    public int readUnsignedTriByte() {
        offset += 3;
        return ((payload[offset - 3] & 0xff) << 16) + ((payload[offset - 2] & 0xff) << 8) + (payload[offset - 1] & 0xff);
    }

    public int readUnsignedInt() {
        offset += 4;
        return ((payload[offset - 4] & 0xff) << 24) + ((payload[offset - 3] & 0xff) << 16) + ((payload[offset - 2] & 0xff) << 8) + (payload[offset - 1] & 0xff);
    }

    public long readLong() {
        long ms = readUnsignedInt() & 0xffffffffL;
        long ls = readUnsignedInt() & 0xffffffffL;
        return (ms << 32) + ls;
    }

    public String readString() {
        int tempOffset = offset;
        while (payload[offset++] != 10) {
        }
        return new String(payload, tempOffset, offset - tempOffset - 1);
    }

    public byte[] readBytes() {
        int tempOffset = offset;
        while (payload[offset++] != 10) {
        }
        byte[] buf = new byte[offset - tempOffset - 1];
        for (int index = tempOffset; index < offset - 1; index++) {
            buf[index - tempOffset] = payload[index];
        }
        return buf;
    }

    public void readBytes(byte[] payload, int start, int length) {
        for (int index = start; index < start + length; index++) {
            payload[index] = this.payload[offset++];
        }
    }

    public void initBitAccess() {
        bitOffset = offset * 8;
    }

    public int readBits(int amount) {
        int offset = bitOffset >> 3;
        int k = 8 - (bitOffset & 7);
        int bits = 0;
        bitOffset += amount;
        for (; amount > k; k = 8) {
            bits += (payload[offset++] & Buffer.BIT_MASKS[k]) << amount - k;
            amount -= k;
        }
        if (amount == k) {
            bits += payload[offset] & Buffer.BIT_MASKS[k];
        } else {
            bits += payload[offset] >> k - amount & Buffer.BIT_MASKS[amount];
        }
        return bits;
    }

    public void finishBitAccess() {
        offset = (bitOffset + 7) / 8;
    }

    public int readUnsignedSmartA() {
        int value = payload[offset] & 0xff;
        if (value < 128) {
            return readUnsignedByte() - 64;
        } else {
            return readUnsignedShort() - 49152;
        }
    }

    public int readUnsignedSmartB() {
        int value = payload[offset] & 0xff;
        if (value < 128) {
            return readUnsignedByte();
        } else {
            return readUnsignedShort() - 32768;
        }
    }

    public void generateKeys(BigInteger key, BigInteger modulus) {
        int tempOffset = offset;
        offset = 0;
        byte[] buffer = new byte[tempOffset];
        readBytes(buffer, 0, tempOffset);
        BigInteger bigInteger = new BigInteger(buffer);
        BigInteger result = bigInteger.modPow(modulus, key);
        byte[] finalBuffer = result.toByteArray();
        offset = 0;
        writeByte(finalBuffer.length);
        writeBytes(finalBuffer, finalBuffer.length, 0);
    }
}
