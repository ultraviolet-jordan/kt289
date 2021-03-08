package com.kt289.util.crypto;

public class IsaacRandom {

    private int anInt783;
    private int[] anIntArray784;
    private int[] anIntArray785;
    private int anInt786;
    private int anInt787;
    private int anInt788;

    public IsaacRandom(int[] ai, byte byte0) {
        anIntArray785 = new int[256];
        anIntArray784 = new int[256];
        System.arraycopy(ai, 0, anIntArray784, 0, ai.length);
        method547();
    }

    public int value() {
        if (anInt783-- == 0) {
            method546();
            anInt783 = 255;
        }
        return anIntArray784[anInt783];
    }

    private void method546() {
        anInt787 += ++anInt788;
        for (int i = 0; i < 256; i++) {
            int j = anIntArray785[i];
            switch (i & 3) {
                case 0: // '\0'
                    anInt786 ^= anInt786 << 13;
                    break;

                case 1: // '\001'
                    anInt786 ^= anInt786 >>> 6;
                    break;

                case 2: // '\002'
                    anInt786 ^= anInt786 << 2;
                    break;

                case 3: // '\003'
                    anInt786 ^= anInt786 >>> 16;
                    break;
            }
            anInt786 += anIntArray785[i + 128 & 0xff];
            int k;
            anIntArray785[i] = k = anIntArray785[(j & 0x3fc) >> 2] + anInt786 + anInt787;
            anIntArray784[i] = anInt787 = anIntArray785[(k >> 8 & 0x3fc) >> 2] + j;
        }
    }

    private void method547() {
        int i1;
        int j1;
        int k1;
        int l1;
        int i2;
        int j2;
        int k2;
        int l = i1 = j1 = k1 = l1 = i2 = j2 = k2 = 0x9e3779b9;
        for (int i = 0; i < 4; i++) {
            l ^= i1 << 11;
            k1 += l;
            i1 += j1;
            i1 ^= j1 >>> 2;
            l1 += i1;
            j1 += k1;
            j1 ^= k1 << 8;
            i2 += j1;
            k1 += l1;
            k1 ^= l1 >>> 16;
            j2 += k1;
            l1 += i2;
            l1 ^= i2 << 10;
            k2 += l1;
            i2 += j2;
            i2 ^= j2 >>> 4;
            l += i2;
            j2 += k2;
            j2 ^= k2 << 8;
            i1 += j2;
            k2 += l;
            k2 ^= l >>> 9;
            j1 += k2;
            l += i1;
        }
        for (int j = 0; j < 256; j += 8) {
            l += anIntArray784[j];
            i1 += anIntArray784[j + 1];
            j1 += anIntArray784[j + 2];
            k1 += anIntArray784[j + 3];
            l1 += anIntArray784[j + 4];
            i2 += anIntArray784[j + 5];
            j2 += anIntArray784[j + 6];
            k2 += anIntArray784[j + 7];
            l ^= i1 << 11;
            k1 += l;
            i1 += j1;
            i1 ^= j1 >>> 2;
            l1 += i1;
            j1 += k1;
            j1 ^= k1 << 8;
            i2 += j1;
            k1 += l1;
            k1 ^= l1 >>> 16;
            j2 += k1;
            l1 += i2;
            l1 ^= i2 << 10;
            k2 += l1;
            i2 += j2;
            i2 ^= j2 >>> 4;
            l += i2;
            j2 += k2;
            j2 ^= k2 << 8;
            i1 += j2;
            k2 += l;
            k2 ^= l >>> 9;
            j1 += k2;
            l += i1;
            anIntArray785[j] = l;
            anIntArray785[j + 1] = i1;
            anIntArray785[j + 2] = j1;
            anIntArray785[j + 3] = k1;
            anIntArray785[j + 4] = l1;
            anIntArray785[j + 5] = i2;
            anIntArray785[j + 6] = j2;
            anIntArray785[j + 7] = k2;
        }
        for (int k = 0; k < 256; k += 8) {
            l += anIntArray785[k];
            i1 += anIntArray785[k + 1];
            j1 += anIntArray785[k + 2];
            k1 += anIntArray785[k + 3];
            l1 += anIntArray785[k + 4];
            i2 += anIntArray785[k + 5];
            j2 += anIntArray785[k + 6];
            k2 += anIntArray785[k + 7];
            l ^= i1 << 11;
            k1 += l;
            i1 += j1;
            i1 ^= j1 >>> 2;
            l1 += i1;
            j1 += k1;
            j1 ^= k1 << 8;
            i2 += j1;
            k1 += l1;
            k1 ^= l1 >>> 16;
            j2 += k1;
            l1 += i2;
            l1 ^= i2 << 10;
            k2 += l1;
            i2 += j2;
            i2 ^= j2 >>> 4;
            l += i2;
            j2 += k2;
            j2 ^= k2 << 8;
            i1 += j2;
            k2 += l;
            k2 ^= l >>> 9;
            j1 += k2;
            l += i1;
            anIntArray785[k] = l;
            anIntArray785[k + 1] = i1;
            anIntArray785[k + 2] = j1;
            anIntArray785[k + 3] = k1;
            anIntArray785[k + 4] = l1;
            anIntArray785[k + 5] = i2;
            anIntArray785[k + 6] = j2;
            anIntArray785[k + 7] = k2;
        }
        method546();
        anInt783 = 256;
    }
}
