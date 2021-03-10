package com.kt289.util.buffer;

import com.kt289.util.aggregation.CacheableNode;
import com.kt289.util.aggregation.LinkedList;
import com.kt289.isaac.ISAACRandom;

import java.math.BigInteger;
import java.util.stream.IntStream;

public class Buffer extends CacheableNode {

    private static final int[] BIT_MASKS = {
            0, 1, 3, 7, 15, 31, 63, 127, 255, 511, 1023, 2047, 4095, 8191, 16383, 32767,
            65535, 0x1ffff, 0x3ffff, 0x7ffff, 0xfffff, 0x1fffff, 0x3fffff, 0x7fffff, 0xffffff,
            0x1ffffff, 0x3ffffff, 0x7ffffff, 0xfffffff, 0x1fffffff, 0x3fffffff, 0x7fffffff, -1};
    private static final LinkedList small = new LinkedList();
    private static final LinkedList normal = new LinkedList();
    private static final LinkedList large = new LinkedList();
    private static int cacheCountSmall;
    private static int cacheCountNormal;
    private static int cacheCountLarge;

    public byte[] payload;
    public int offset;
    public int bitOffset;
    public ISAACRandom cipher;

    public Buffer(byte[] payload) {
        this.payload = payload;
        this.offset = 0;
    }

    public static Buffer create(int type) {
        synchronized (normal) {
            Buffer buffer = null;
            if (type == 0 && cacheCountSmall > 0) {
                cacheCountSmall--;
                buffer = (Buffer) small.pop();
            } else if (type == 1 && cacheCountNormal > 0) {
                cacheCountNormal--;
                buffer = (Buffer) normal.pop();
            } else if (type == 2 && cacheCountLarge > 0) {
                cacheCountLarge--;
                buffer = (Buffer) large.pop();
            }
            if (buffer != null) {
                buffer.offset = 0;
                return buffer;
            }
        }
        Buffer buffer = new Buffer(null);
        buffer.offset = 0;
        if (type == 0) {
            buffer.payload = new byte[100];
        } else if (type == 1) {
            buffer.payload = new byte[5000];
        } else {
            buffer.payload = new byte[30000];
        }
        return buffer;
    }

    public void writePacket(int value) {
        payload[offset++] = (byte) (value + cipher.next());
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
        IntStream.range(start, start + length).forEach(index -> this.payload[offset++] = payload[index]);
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

    public int readInt() {
        offset += 4;
        return ((payload[offset - 4] & 0xff) << 24) + ((payload[offset - 3] & 0xff) << 16) + ((payload[offset - 2] & 0xff) << 8) + (payload[offset - 1] & 0xff);
    }

    public long readLong() {
        long ms = readInt() & 0xffffffffL;
        long ls = readInt() & 0xffffffffL;
        return (ms << 32) + ls;
    }

    public String readString() {
        int tempOffset = offset;
        while (true) {
            if (payload[offset++] == 10) {
                break;
            }
        }
        return new String(payload, tempOffset, offset - tempOffset - 1);
    }

    public byte[] readStringRaw() {
        int tempOffset = offset;
        while (true) {
            if (payload[offset++] == 10) {
                break;
            }
        }
        byte[] buf = new byte[offset - tempOffset - 1];
        if (offset - 1 - tempOffset >= 0) {
            System.arraycopy(payload, tempOffset, buf, 0, offset - 1 - tempOffset);
        }
        return buf;
    }

    public void readStringRaw(byte[] payload, int start, int length) {
        IntStream.range(start, start + length).forEach(index -> payload[index] = this.payload[offset++]);
    }

    public void initBitAccess() {
        bitOffset = offset * 8;
    }

    public int readBits(int amount) {
        int offset = bitOffset >> 3;
        int k = 8 - (bitOffset & 7);
        int bits = 0;
        bitOffset += amount;
        while (amount > k) {
            bits += (payload[offset++] & BIT_MASKS[k]) << amount - k;
            amount -= k;
            k = 8;
        }
        if (amount == k) {
            bits += payload[offset] & BIT_MASKS[k];
        } else {
            bits += payload[offset] >> k - amount & BIT_MASKS[amount];
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

    public void rsa(BigInteger key, BigInteger modulus) {
        int tempOffset = offset;
        offset = 0;
        byte[] buffer = new byte[tempOffset];
        readStringRaw(buffer, 0, tempOffset);
        BigInteger bigInteger = new BigInteger(buffer);
        BigInteger result = bigInteger.modPow(modulus, key);
        byte[] finalBuffer = result.toByteArray();
        offset = 0;
        writeByte(finalBuffer.length);
        writeBytes(finalBuffer, finalBuffer.length, 0);
    }
}
