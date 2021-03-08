package com.kt289.bzip2;

import java.util.stream.IntStream;

public class Bzip2Decompressor {

    private static final Bzip2DecompressionState aClass40_669 = new Bzip2DecompressionState();

    public static void decompress(byte[] abyte0, int i, byte[] abyte1, int j, int k) {
        synchronized (aClass40_669) {
            aClass40_669.aByteArray706 = abyte1;
            aClass40_669.anInt707 = k;
            aClass40_669.aByteArray711 = abyte0;
            aClass40_669.anInt712 = 0;
            aClass40_669.anInt708 = j;
            aClass40_669.anInt713 = i;
            aClass40_669.anInt720 = 0;
            aClass40_669.anInt719 = 0;
            aClass40_669.anInt709 = 0;
            aClass40_669.anInt710 = 0;
            aClass40_669.anInt714 = 0;
            aClass40_669.anInt715 = 0;
            aClass40_669.anInt722 = 0;
            method331();
        }
    }

    private static void method330() {
        byte byte4 = Bzip2Decompressor.aClass40_669.aByte716;
        int i = Bzip2Decompressor.aClass40_669.anInt717;
        int j = Bzip2Decompressor.aClass40_669.anInt727;
        int k = Bzip2Decompressor.aClass40_669.anInt725;
        int[] ai = Bzip2DecompressionState.anIntArray730;
        int l = Bzip2Decompressor.aClass40_669.anInt724;
        byte[] abyte0 = Bzip2Decompressor.aClass40_669.aByteArray711;
        int i1 = Bzip2Decompressor.aClass40_669.anInt712;
        int j1 = Bzip2Decompressor.aClass40_669.anInt713;
        int k1 = j1;
        int l1 = Bzip2Decompressor.aClass40_669.anInt744 + 1;
        label0:
        while (true) {
            if (i > 0) {
                while (true) {
                    if (j1 == 0) {
                        break label0;
                    }
                    if (i == 1) {
                        break;
                    }
                    abyte0[i1] = byte4;
                    i--;
                    i1++;
                    j1--;
                }
                abyte0[i1] = byte4;
                i1++;
                j1--;
            }
            boolean flag = true;
            while (flag) {
                flag = false;
                if (j == l1) {
                    i = 0;
                    break label0;
                }
                byte4 = (byte) k;
                l = ai[l];
                byte byte0 = (byte) (l & 0xff);
                l >>= 8;
                j++;
                if (byte0 != k) {
                    k = byte0;
                    if (j1 == 0) {
                        i = 1;
                    } else {
                        abyte0[i1] = byte4;
                        i1++;
                        j1--;
                        flag = true;
                        continue;
                    }
                    break label0;
                }
                if (j != l1) {
                    continue;
                }
                if (j1 == 0) {
                    i = 1;
                    break label0;
                }
                abyte0[i1] = byte4;
                i1++;
                j1--;
                flag = true;
            }
            i = 2;
            l = ai[l];
            byte byte1 = (byte) (l & 0xff);
            l >>= 8;
            if (++j != l1) {
                if (byte1 != k) {
                    k = byte1;
                } else {
                    i = 3;
                    l = ai[l];
                    byte byte2 = (byte) (l & 0xff);
                    l >>= 8;
                    if (++j != l1) {
                        if (byte2 != k) {
                            k = byte2;
                        } else {
                            l = ai[l];
                            byte byte3 = (byte) (l & 0xff);
                            l >>= 8;
                            j++;
                            i = (byte3 & 0xff) + 4;
                            l = ai[l];
                            k = (byte) (l & 0xff);
                            l >>= 8;
                            j++;
                        }
                    }
                }
            }
        }
        int i2 = Bzip2Decompressor.aClass40_669.anInt714;
        Bzip2Decompressor.aClass40_669.anInt714 += k1 - j1;
        if (Bzip2Decompressor.aClass40_669.anInt714 < i2) {
            Bzip2Decompressor.aClass40_669.anInt715++;
        }
        Bzip2Decompressor.aClass40_669.aByte716 = byte4;
        Bzip2Decompressor.aClass40_669.anInt717 = i;
        Bzip2Decompressor.aClass40_669.anInt727 = j;
        Bzip2Decompressor.aClass40_669.anInt725 = k;
        Bzip2DecompressionState.anIntArray730 = ai;
        Bzip2Decompressor.aClass40_669.anInt724 = l;
        Bzip2Decompressor.aClass40_669.aByteArray711 = abyte0;
        Bzip2Decompressor.aClass40_669.anInt712 = i1;
        Bzip2Decompressor.aClass40_669.anInt713 = j1;
    }

    private static void method331() {
        int k8;
        int[] ai;
        int[] ai1;
        int[] ai2;
        Bzip2Decompressor.aClass40_669.anInt721 = 1;
        if (Bzip2DecompressionState.anIntArray730 == null) {
            Bzip2DecompressionState.anIntArray730 = new int[Bzip2Decompressor.aClass40_669.anInt721 * 0x186a0];
        }
        boolean flag19 = true;
        while (flag19) {
            byte byte0 = method332();
            if (byte0 == 23) {
                return;
            }
            byte0 = method332();
            byte0 = method332();
            byte0 = method332();
            byte0 = method332();
            byte0 = method332();
            Bzip2Decompressor.aClass40_669.anInt722++;
            byte0 = method332();
            byte0 = method332();
            byte0 = method332();
            byte0 = method332();
            byte0 = method333();
            Bzip2Decompressor.aClass40_669.aBoolean718 = byte0 != 0;
            if (Bzip2Decompressor.aClass40_669.aBoolean718) {
                System.out.println("PANIC! RANDOMISED BLOCK!");
            }
            Bzip2Decompressor.aClass40_669.anInt723 = 0;
            byte0 = method332();
            Bzip2Decompressor.aClass40_669.anInt723 = Bzip2Decompressor.aClass40_669.anInt723 << 8 | byte0 & 0xff;
            byte0 = method332();
            Bzip2Decompressor.aClass40_669.anInt723 = Bzip2Decompressor.aClass40_669.anInt723 << 8 | byte0 & 0xff;
            byte0 = method332();
            Bzip2Decompressor.aClass40_669.anInt723 = Bzip2Decompressor.aClass40_669.anInt723 << 8 | byte0 & 0xff;
            for (int j = 0; j < 16; j++) {
                byte byte1 = method333();
                Bzip2Decompressor.aClass40_669.inUse16[j] = byte1 == 1;
            }
            for (int k = 0; k < 256; k++) {
                Bzip2Decompressor.aClass40_669.inUse[k] = false;
            }
            for (int l = 0; l < 16; l++) {
                if (Bzip2Decompressor.aClass40_669.inUse16[l]) {
                    for (int i3 = 0; i3 < 16; i3++) {
                        byte byte2 = method333();
                        if (byte2 == 1) {
                            Bzip2Decompressor.aClass40_669.inUse[l * 16 + i3] = true;
                        }
                    }
                }
            }
            method335();
            int i4 = Bzip2Decompressor.aClass40_669.anInt731 + 2;
            int j4 = method334(3);
            int k4 = method334(15);
            for (int i1 = 0; i1 < k4; i1++) {
                int j3 = 0;
                do {
                    byte byte3 = method333();
                    if (byte3 == 0) {
                        break;
                    }
                    j3++;
                } while (true);
                Bzip2Decompressor.aClass40_669.selectorMtf[i1] = (byte) j3;
            }

            byte[] abyte0 = new byte[6];
            for (byte byte16 = 0; byte16 < j4; byte16++) {
                abyte0[byte16] = byte16;
            }
            for (int j1 = 0; j1 < k4; j1++) {
                byte byte17 = Bzip2Decompressor.aClass40_669.selectorMtf[j1];
                byte byte15 = abyte0[byte17];
                for (; byte17 > 0; byte17--) {
                    abyte0[byte17] = abyte0[byte17 - 1];
                }
                abyte0[0] = byte15;
                Bzip2Decompressor.aClass40_669.selector[j1] = byte15;
            }
            for (int k3 = 0; k3 < j4; k3++) {
                int l6 = method334(5);
                for (int k1 = 0; k1 < i4; k1++) {
                    do {
                        byte byte4 = method333();
                        if (byte4 == 0) {
                            break;
                        }
                        byte4 = method333();
                        if (byte4 == 0) {
                            l6++;
                        } else {
                            l6--;
                        }
                    } while (true);
                    Bzip2Decompressor.aClass40_669.len[k3][k1] = (byte) l6;
                }

            }
            for (int l3 = 0; l3 < j4; l3++) {
                byte byte8 = 32;
                int i = 0;
                for (int l1 = 0; l1 < i4; l1++) {
                    if (Bzip2Decompressor.aClass40_669.len[l3][l1] > i) {
                        i = Bzip2Decompressor.aClass40_669.len[l3][l1];
                    }
                    if (Bzip2Decompressor.aClass40_669.len[l3][l1] < byte8) {
                        byte8 = Bzip2Decompressor.aClass40_669.len[l3][l1];
                    }
                }
                method336(Bzip2Decompressor.aClass40_669.limit[l3], Bzip2Decompressor.aClass40_669.base[l3],
                        Bzip2Decompressor.aClass40_669.perm[l3], Bzip2Decompressor.aClass40_669.len[l3], byte8, i, i4);
                Bzip2Decompressor.aClass40_669.minLens[l3] = byte8;
            }
            int l4 = Bzip2Decompressor.aClass40_669.anInt731 + 1;
            int i5 = -1;
            int j5;
            for (int i2 = 0; i2 <= 255; i2++) {
                Bzip2Decompressor.aClass40_669.unzftab[i2] = 0;
            }
            int j9 = 4095;
            for (int l8 = 15; l8 >= 0; l8--) {
                for (int i9 = 15; i9 >= 0; i9--) {
                    Bzip2Decompressor.aClass40_669.mtfa[j9] = (byte) (l8 * 16 + i9);
                    j9--;
                }
                Bzip2Decompressor.aClass40_669.mtfbase[l8] = j9 + 1;
            }
            int i6 = 0;
            i5++;
            j5 = 50;
            byte byte12 = Bzip2Decompressor.aClass40_669.selector[i5];
            k8 = Bzip2Decompressor.aClass40_669.minLens[byte12];
            ai = Bzip2Decompressor.aClass40_669.limit[byte12];
            ai2 = Bzip2Decompressor.aClass40_669.perm[byte12];
            ai1 = Bzip2Decompressor.aClass40_669.base[byte12];
            j5--;
            int i7 = k8;
            int l7;
            byte byte9;
            for (l7 = method334(i7); l7 > ai[i7]; l7 = l7 << 1 | byte9) {
                i7++;
                byte9 = method333();
            }
            for (int k5 = ai2[l7 - ai1[i7]]; k5 != l4; ) {
                if (k5 == 0 || k5 == 1) {
                    int j6 = -1;
                    int k6 = 1;
                    do {
                        if (k5 == 0) {
                            j6 += k6;
                        } else {
                            j6 += 2 * k6;
                        }
                        k6 *= 2;
                        if (j5 == 0) {
                            i5++;
                            j5 = 50;
                            byte byte13 = Bzip2Decompressor.aClass40_669.selector[i5];
                            k8 = Bzip2Decompressor.aClass40_669.minLens[byte13];
                            ai = Bzip2Decompressor.aClass40_669.limit[byte13];
                            ai2 = Bzip2Decompressor.aClass40_669.perm[byte13];
                            ai1 = Bzip2Decompressor.aClass40_669.base[byte13];
                        }
                        j5--;
                        int j7 = k8;
                        int i8;
                        byte byte10;
                        for (i8 = method334(j7); i8 > ai[j7]; i8 = i8 << 1 | byte10) {
                            j7++;
                            byte10 = method333();
                        }
                        k5 = ai2[i8 - ai1[j7]];
                    } while (k5 == 0 || k5 == 1);
                    j6++;
                    byte byte5 = Bzip2Decompressor.aClass40_669.seqToUnseq[Bzip2Decompressor.aClass40_669.mtfa[Bzip2Decompressor.aClass40_669.mtfbase[0]] & 0xff];
                    Bzip2Decompressor.aClass40_669.unzftab[byte5 & 0xff] += j6;
                    for (; j6 > 0; j6--) {
                        Bzip2DecompressionState.anIntArray730[i6] = byte5 & 0xff;
                        i6++;
                    }
                } else {
                    int j11 = k5 - 1;
                    byte byte6;
                    if (j11 < 16) {
                        int j10 = Bzip2Decompressor.aClass40_669.mtfbase[0];
                        byte6 = Bzip2Decompressor.aClass40_669.mtfa[j10 + j11];
                        for (; j11 > 3; j11 -= 4) {
                            int k11 = j10 + j11;
                            Bzip2Decompressor.aClass40_669.mtfa[k11] = Bzip2Decompressor.aClass40_669.mtfa[k11 - 1];
                            Bzip2Decompressor.aClass40_669.mtfa[k11 - 1] = Bzip2Decompressor.aClass40_669.mtfa[k11 - 2];
                            Bzip2Decompressor.aClass40_669.mtfa[k11 - 2] = Bzip2Decompressor.aClass40_669.mtfa[k11 - 3];
                            Bzip2Decompressor.aClass40_669.mtfa[k11 - 3] = Bzip2Decompressor.aClass40_669.mtfa[k11 - 4];
                        }
                        for (; j11 > 0; j11--) {
                            Bzip2Decompressor.aClass40_669.mtfa[j10 + j11] = Bzip2Decompressor.aClass40_669.mtfa[(j10 + j11) - 1];
                        }
                        Bzip2Decompressor.aClass40_669.mtfa[j10] = byte6;
                    } else {
                        int l10 = j11 / 16;
                        int i11 = j11 % 16;
                        int k10 = Bzip2Decompressor.aClass40_669.mtfbase[l10] + i11;
                        byte6 = Bzip2Decompressor.aClass40_669.mtfa[k10];
                        for (; k10 > Bzip2Decompressor.aClass40_669.mtfbase[l10]; k10--) {
                            Bzip2Decompressor.aClass40_669.mtfa[k10] = Bzip2Decompressor.aClass40_669.mtfa[k10 - 1];
                        }
                        Bzip2Decompressor.aClass40_669.mtfbase[l10]++;
                        for (; l10 > 0; l10--) {
                            Bzip2Decompressor.aClass40_669.mtfbase[l10]--;
                            Bzip2Decompressor.aClass40_669.mtfa[Bzip2Decompressor.aClass40_669.mtfbase[l10]] = Bzip2Decompressor.aClass40_669.mtfa[(Bzip2Decompressor.aClass40_669.mtfbase[l10 - 1] + 16) - 1];
                        }
                        Bzip2Decompressor.aClass40_669.mtfbase[0]--;
                        Bzip2Decompressor.aClass40_669.mtfa[Bzip2Decompressor.aClass40_669.mtfbase[0]] = byte6;
                        if (Bzip2Decompressor.aClass40_669.mtfbase[0] == 0) {
                            int i10 = 4095;
                            for (int k9 = 15; k9 >= 0; k9--) {
                                for (int l9 = 15; l9 >= 0; l9--) {
                                    Bzip2Decompressor.aClass40_669.mtfa[i10] = Bzip2Decompressor.aClass40_669.mtfa[Bzip2Decompressor.aClass40_669.mtfbase[k9] + l9];
                                    i10--;
                                }
                                Bzip2Decompressor.aClass40_669.mtfbase[k9] = i10 + 1;
                            }
                        }
                    }
                    Bzip2Decompressor.aClass40_669.unzftab[Bzip2Decompressor.aClass40_669.seqToUnseq[byte6 & 0xff] & 0xff]++;
                    Bzip2DecompressionState.anIntArray730[i6] = Bzip2Decompressor.aClass40_669.seqToUnseq[byte6 & 0xff] & 0xff;
                    i6++;
                    if (j5 == 0) {
                        i5++;
                        j5 = 50;
                        byte byte14 = Bzip2Decompressor.aClass40_669.selector[i5];
                        k8 = Bzip2Decompressor.aClass40_669.minLens[byte14];
                        ai = Bzip2Decompressor.aClass40_669.limit[byte14];
                        ai2 = Bzip2Decompressor.aClass40_669.perm[byte14];
                        ai1 = Bzip2Decompressor.aClass40_669.base[byte14];
                    }
                    j5--;
                    int k7 = k8;
                    int j8;
                    byte byte11;
                    for (j8 = method334(k7); j8 > ai[k7]; j8 = j8 << 1 | byte11) {
                        k7++;
                        byte11 = method333();
                    }
                    k5 = ai2[j8 - ai1[k7]];
                }
            }

            Bzip2Decompressor.aClass40_669.anInt717 = 0;
            Bzip2Decompressor.aClass40_669.aByte716 = 0;
            Bzip2Decompressor.aClass40_669.cftab[0] = 0;
            System.arraycopy(Bzip2Decompressor.aClass40_669.unzftab, 0, Bzip2Decompressor.aClass40_669.cftab, 1, 256);
            IntStream.rangeClosed(1, 256).forEach(k2 -> Bzip2Decompressor.aClass40_669.cftab[k2] += Bzip2Decompressor.aClass40_669.cftab[k2 - 1]);
            IntStream.range(0, i6).forEach(l2 -> {
                byte byte7 = (byte) (Bzip2DecompressionState.anIntArray730[l2] & 0xff);
                Bzip2DecompressionState.anIntArray730[Bzip2Decompressor.aClass40_669.cftab[byte7 & 0xff]] |= l2 << 8;
                Bzip2Decompressor.aClass40_669.cftab[byte7 & 0xff]++;
            });
            Bzip2Decompressor.aClass40_669.anInt724 = Bzip2DecompressionState.anIntArray730[Bzip2Decompressor.aClass40_669.anInt723] >> 8;
            Bzip2Decompressor.aClass40_669.anInt727 = 0;
            Bzip2Decompressor.aClass40_669.anInt724 = Bzip2DecompressionState.anIntArray730[Bzip2Decompressor.aClass40_669.anInt724];
            Bzip2Decompressor.aClass40_669.anInt725 = (byte) (Bzip2Decompressor.aClass40_669.anInt724 & 0xff);
            Bzip2Decompressor.aClass40_669.anInt724 >>= 8;
            Bzip2Decompressor.aClass40_669.anInt727++;
            Bzip2Decompressor.aClass40_669.anInt744 = i6;
            method330();
            flag19 = Bzip2Decompressor.aClass40_669.anInt727 == Bzip2Decompressor.aClass40_669.anInt744 + 1 && Bzip2Decompressor.aClass40_669.anInt717 == 0;
        }
    }

    private static byte method332() {
        return (byte) method334(8);
    }

    private static byte method333() {
        return (byte) method334(1);
    }

    private static int method334(int i) {
        int j;
        while (true) {
            if (Bzip2Decompressor.aClass40_669.anInt720 >= i) {
                int k = Bzip2Decompressor.aClass40_669.anInt719 >> Bzip2Decompressor.aClass40_669.anInt720 - i & (1 << i) - 1;
                Bzip2Decompressor.aClass40_669.anInt720 -= i;
                j = k;
                break;
            }
            Bzip2Decompressor.aClass40_669.anInt719 = Bzip2Decompressor.aClass40_669.anInt719 << 8 | Bzip2Decompressor.aClass40_669.aByteArray706[Bzip2Decompressor.aClass40_669.anInt707] & 0xff;
            Bzip2Decompressor.aClass40_669.anInt720 += 8;
            Bzip2Decompressor.aClass40_669.anInt707++;
            Bzip2Decompressor.aClass40_669.anInt708--;
            Bzip2Decompressor.aClass40_669.anInt709++;
            if (Bzip2Decompressor.aClass40_669.anInt709 == 0) {
                Bzip2Decompressor.aClass40_669.anInt710++;
            }
        }
        return j;
    }

    private static void method335() {
        Bzip2Decompressor.aClass40_669.anInt731 = 0;
        IntStream.range(0, 256).filter(i -> Bzip2Decompressor.aClass40_669.inUse[i]).forEach(i -> {
            Bzip2Decompressor.aClass40_669.seqToUnseq[Bzip2Decompressor.aClass40_669.anInt731] = (byte) i;
            Bzip2Decompressor.aClass40_669.anInt731++;
        });
    }

    private static void method336(int[] ai, int[] ai1, int[] ai2, byte[] abyte0, int i, int j, int k) {
        int l = 0;
        for (int i1 = i; i1 <= j; i1++) {
            for (int l2 = 0; l2 < k; l2++) {
                if (abyte0[l2] == i1) {
                    ai2[l] = l2;
                    l++;
                }
            }
        }
        IntStream.range(0, 23).forEach(j1 -> ai1[j1] = 0);
        IntStream.range(0, k).forEach(k1 -> ai1[abyte0[k1] + 1]++);
        IntStream.range(1, 23).forEach(l1 -> ai1[l1] += ai1[l1 - 1]);
        IntStream.range(0, 23).forEach(i2 -> ai[i2] = 0);
        int i3 = 0;
        for (int j2 = i; j2 <= j; j2++) {
            i3 += ai1[j2 + 1] - ai1[j2];
            ai[j2] = i3 - 1;
            i3 <<= 1;
        }
        IntStream.rangeClosed(i + 1, j).forEach(k2 -> ai1[k2] = (ai[k2 - 1] + 1 << 1) - ai1[k2]);
    }

}
