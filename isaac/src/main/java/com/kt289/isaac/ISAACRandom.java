package com.kt289.isaac;

public class ISAACRandom {

    private final int[] rsl;
    private final int[] mem;

    private int count;
    private int a;
    private int b;
    private int c;

    public ISAACRandom(int[] seed) {
        mem = new int[256];
        rsl = new int[256];
        System.arraycopy(seed, 0, rsl, 0, seed.length);
        init();
    }

    public int next() {
        if (count-- == 0) {
            isaac();
            count = 255;
        }
        return rsl[count];
    }

    private void isaac() {
        b += ++c;
        for (int index = 0; index < 256; index++) {
            int x = mem[index];
            switch (index & 3) {
                case 0:
                    a ^= a << 13;
                    break;

                case 1:
                    a ^= a >>> 6;
                    break;

                case 2:
                    a ^= a << 2;
                    break;

                case 3:
                    a ^= a >>> 16;
                    break;
            }
            a += mem[index + 128 & 0xff];
            int y;
            mem[index] = y = mem[(x & 0x3fc) >> 2] + a + b;
            rsl[index] = b = mem[(y >> 8 & 0x3fc) >> 2] + x;
        }
    }

    private void init() {
        final int ratio = 0x9E3779B9;
        int a = ratio;
        int b = ratio;
        int c = ratio;
        int d = ratio;
        int e = ratio;
        int f = ratio;
        int g = ratio;
        int h = ratio;

        for (int i = 0; i < 4; i++) {
            a ^= b << 11;
            d += a;
            b += c;

            b ^= c >>> 2;
            e += b;
            c += d;

            c ^= d << 8;
            f += c;
            d += e;

            d ^= e >>> 16;
            g += d;
            e += f;

            e ^= f << 10;
            h += e;
            f += g;

            f ^= g >>> 4;
            a += f;
            g += h;

            g ^= h << 8;
            b += g;
            h += a;

            h ^= a >>> 9;
            c += h;
            a += b;
        }
        for (int index = 0; index < 256; index += 8) {
            a += rsl[index];
            b += rsl[index + 1];
            c += rsl[index + 2];
            d += rsl[index + 3];
            e += rsl[index + 4];
            f += rsl[index + 5];
            g += rsl[index + 6];
            h += rsl[index + 7];

            a ^= b << 11;
            d += a;
            b += c;

            b ^= c >>> 2;
            e += b;
            c += d;

            c ^= d << 8;
            f += c;
            d += e;

            d ^= e >>> 16;
            g += d;
            e += f;

            e ^= f << 10;
            h += e;
            f += g;

            f ^= g >>> 4;
            a += f;
            g += h;

            g ^= h << 8;
            b += g;
            h += a;

            h ^= a >>> 9;
            c += h;
            a += b;

            mem[index] = a;
            mem[index + 1] = b;
            mem[index + 2] = c;
            mem[index + 3] = d;
            mem[index + 4] = e;
            mem[index + 5] = f;
            mem[index + 6] = g;
            mem[index + 7] = h;
        }
        for (int index = 0; index < 256; index += 8) {
            a += mem[index];
            b += mem[index + 1];
            c += mem[index + 2];
            d += mem[index + 3];
            e += mem[index + 4];
            f += mem[index + 5];
            g += mem[index + 6];
            h += mem[index + 7];

            a ^= b << 11;
            d += a;
            b += c;

            b ^= c >>> 2;
            e += b;
            c += d;

            c ^= d << 8;
            f += c;
            d += e;

            d ^= e >>> 16;
            g += d;
            e += f;

            e ^= f << 10;
            h += e;
            f += g;

            f ^= g >>> 4;
            a += f;
            g += h;

            g ^= h << 8;
            b += g;
            h += a;

            h ^= a >>> 9;
            c += h;
            a += b;

            mem[index] = a;
            mem[index + 1] = b;
            mem[index + 2] = c;
            mem[index + 3] = d;
            mem[index + 4] = e;
            mem[index + 5] = f;
            mem[index + 6] = g;
            mem[index + 7] = h;
        }
        isaac();
        count = 256;
    }
}
