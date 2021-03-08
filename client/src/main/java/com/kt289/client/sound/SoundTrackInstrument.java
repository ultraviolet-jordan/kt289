package com.kt289.client.sound;

import com.kt289.util.SignLink;
import com.kt289.util.buffer.Buffer;

class SoundTrackInstrument {

    private static int[] anIntArray184;
    private static int[] anIntArray185;
    private static int[] anIntArray186;
    private static final int[] anIntArray187 = new int[5];
    private static final int[] anIntArray188 = new int[5];
    private static final int[] anIntArray189 = new int[5];
    private static final int[] anIntArray190 = new int[5];
    private static final int[] anIntArray191 = new int[5];
    private int anInt165;
    private final int anInt166;
    private SoundTrackEnvelope aClass1_167;
    private SoundTrackEnvelope aClass1_168;
    private SoundTrackEnvelope aClass1_169;
    private SoundTrackEnvelope aClass1_170;
    private SoundTrackEnvelope aClass1_171;
    private SoundTrackEnvelope aClass1_172;
    private SoundTrackEnvelope aClass1_173;
    private SoundTrackEnvelope aClass1_174;
    private final int[] anIntArray175;
    private final int[] anIntArray176;
    private final int[] anIntArray177;
    private int anInt178;
    private int anInt179;
    private SoundFilter aClass2_180;
    private SoundTrackEnvelope aClass1_181;
    public int anInt182;
    public int anInt183;

    public SoundTrackInstrument() {
        boolean aBoolean164 = false;
        anInt165 = 188;
        anInt166 = 810;
        anIntArray175 = new int[5];
        anIntArray176 = new int[5];
        anIntArray177 = new int[5];
        anInt179 = 100;
        anInt182 = 500;
    }

    public static void method187() {
        SoundTrackInstrument.anIntArray185 = new int[32768];
        for (int i = 0; i < 32768; i++) {
            if (Math.random() > 0.5D) {
                SoundTrackInstrument.anIntArray185[i] = 1;
            } else {
                SoundTrackInstrument.anIntArray185[i] = -1;
            }
        }
        SoundTrackInstrument.anIntArray186 = new int[32768];
        for (int j = 0; j < 32768; j++) {
            SoundTrackInstrument.anIntArray186[j] = (int) (Math.sin(j / 5215.1903000000002D) * 16384D);
        }
        SoundTrackInstrument.anIntArray184 = new int[0x35d54];
    }

    public int[] method188(int i, int j) {
        for (int k = 0; k < i; k++) {
            SoundTrackInstrument.anIntArray184[k] = 0;
        }
        if (j < 10) {
            return SoundTrackInstrument.anIntArray184;
        }
        double d = i / (j + 0.0D);
        aClass1_167.method150(-9520);
        aClass1_168.method150(-9520);
        int l = 0;
        int i1 = 0;
        int j1 = 0;
        if (aClass1_169 != null) {
            aClass1_169.method150(-9520);
            aClass1_170.method150(-9520);
            l = (int) (((aClass1_169.anInt42 - aClass1_169.anInt41) * 32.768000000000001D) / d);
            i1 = (int) ((aClass1_169.anInt41 * 32.768000000000001D) / d);
        }
        int k1 = 0;
        int l1 = 0;
        int i2 = 0;
        if (aClass1_171 != null) {
            aClass1_171.method150(-9520);
            aClass1_172.method150(-9520);
            k1 = (int) (((aClass1_171.anInt42 - aClass1_171.anInt41) * 32.768000000000001D) / d);
            l1 = (int) ((aClass1_171.anInt41 * 32.768000000000001D) / d);
        }
        for (int j2 = 0; j2 < 5; j2++) {
            if (anIntArray175[j2] != 0) {
                SoundTrackInstrument.anIntArray187[j2] = 0;
                SoundTrackInstrument.anIntArray188[j2] = (int) (anIntArray177[j2] * d);
                SoundTrackInstrument.anIntArray189[j2] = (anIntArray175[j2] << 14) / 100;
                SoundTrackInstrument.anIntArray190[j2] = (int) (((aClass1_167.anInt42 - aClass1_167.anInt41) * 32.768000000000001D * Math
                        .pow(1.0057929410678534D, anIntArray176[j2])) / d);
                SoundTrackInstrument.anIntArray191[j2] = (int) ((aClass1_167.anInt41 * 32.768000000000001D) / d);
            }
        }
        for (int k2 = 0; k2 < i; k2++) {
            int l2 = aClass1_167.method151(0, i);
            int j4 = aClass1_168.method151(0, i);
            if (aClass1_169 != null) {
                int j5 = aClass1_169.method151(0, i);
                int j6 = aClass1_170.method151(0, i);
                l2 += method189(j6, 0, j1, aClass1_169.anInt43) >> 1;
                j1 += (j5 * l >> 16) + i1;
            }
            if (aClass1_171 != null) {
                int k5 = aClass1_171.method151(0, i);
                int k6 = aClass1_172.method151(0, i);
                j4 = j4 * ((method189(k6, 0, i2, aClass1_171.anInt43) >> 1) + 32768) >> 15;
                i2 += (k5 * k1 >> 16) + l1;
            }
            for (int l5 = 0; l5 < 5; l5++) {
                if (anIntArray175[l5] != 0) {
                    int l6 = k2 + SoundTrackInstrument.anIntArray188[l5];
                    if (l6 < i) {
                        SoundTrackInstrument.anIntArray184[l6] += method189(
                                j4 * SoundTrackInstrument.anIntArray189[l5] >> 15, 0,
                                SoundTrackInstrument.anIntArray187[l5], aClass1_167.anInt43);
                        SoundTrackInstrument.anIntArray187[l5] += (l2 * SoundTrackInstrument.anIntArray190[l5] >> 16)
                                + SoundTrackInstrument.anIntArray191[l5];
                    }
                }
            }
        }
        if (aClass1_173 != null) {
            aClass1_173.method150(-9520);
            aClass1_174.method150(-9520);
            int i3 = 0;
            boolean flag1 = true;
            for (int i7 = 0; i7 < i; i7++) {
                int k7 = aClass1_173.method151(0, i);
                int i8 = aClass1_174.method151(0, i);
                int k4;
                if (flag1) {
                    k4 = aClass1_173.anInt41 + ((aClass1_173.anInt42 - aClass1_173.anInt41) * k7 >> 8);
                } else {
                    k4 = aClass1_173.anInt41 + ((aClass1_173.anInt42 - aClass1_173.anInt41) * i8 >> 8);
                }
                if ((i3 += 256) >= k4) {
                    i3 = 0;
                    flag1 = !flag1;
                }
                if (flag1) {
                    SoundTrackInstrument.anIntArray184[i7] = 0;
                }
            }
        }
        if (anInt178 > 0 && anInt179 > 0) {
            int j3 = (int) (anInt178 * d);
            for (int l4 = j3; l4 < i; l4++) {
                SoundTrackInstrument.anIntArray184[l4] += (SoundTrackInstrument.anIntArray184[l4 - j3] * anInt179) / 100;
            }
        }
        if (aClass2_180.anIntArray52[0] > 0 || aClass2_180.anIntArray52[1] > 0) {
            aClass1_181.method150(-9520);
            int k3 = aClass1_181.method151(0, i + 1);
            int i5 = aClass2_180.method155(0, 0, k3 / 65536F);
            int i6 = aClass2_180.method155(1, 0, k3 / 65536F);
            if (i >= i5 + i6) {
                int j7 = 0;
                int l7 = i6;
                if (l7 > i - i5) {
                    l7 = i - i5;
                }
                for (; j7 < l7; j7++) {
                    int j8 = (int) ((long) SoundTrackInstrument.anIntArray184[j7 + i5] * (long) SoundFilter.anInt59 >> 16);
                    for (int k8 = 0; k8 < i5; k8++) {
                        j8 += (int) ((long) SoundTrackInstrument.anIntArray184[(j7 + i5) - 1 - k8]
                                * (long) SoundFilter.anIntArrayArray57[0][k8] >> 16);
                    }
                    for (int j9 = 0; j9 < j7; j9++) {
                        j8 -= (int) ((long) SoundTrackInstrument.anIntArray184[j7 - 1 - j9]
                                * (long) SoundFilter.anIntArrayArray57[1][j9] >> 16);
                    }
                    SoundTrackInstrument.anIntArray184[j7] = j8;
                    k3 = aClass1_181.method151(0, i + 1);
                }
                char c = '\200';
                l7 = c;
                do {
                    if (l7 > i - i5) {
                        l7 = i - i5;
                    }
                    for (; j7 < l7; j7++) {
                        int l8 = (int) ((long) SoundTrackInstrument.anIntArray184[j7 + i5] * (long) SoundFilter.anInt59 >> 16);
                        for (int k9 = 0; k9 < i5; k9++) {
                            l8 += (int) ((long) SoundTrackInstrument.anIntArray184[(j7 + i5) - 1 - k9]
                                    * (long) SoundFilter.anIntArrayArray57[0][k9] >> 16);
                        }
                        for (int i10 = 0; i10 < i6; i10++) {
                            l8 -= (int) ((long) SoundTrackInstrument.anIntArray184[j7 - 1 - i10]
                                    * (long) SoundFilter.anIntArrayArray57[1][i10] >> 16);
                        }
                        SoundTrackInstrument.anIntArray184[j7] = l8;
                        k3 = aClass1_181.method151(0, i + 1);
                    }
                    if (j7 >= i - i5) {
                        break;
                    }
                    i5 = aClass2_180.method155(0, 0, k3 / 65536F);
                    i6 = aClass2_180.method155(1, 0, k3 / 65536F);
                    l7 += c;
                } while (true);
                for (; j7 < i; j7++) {
                    int i9 = 0;
                    for (int l9 = (j7 + i5) - i; l9 < i5; l9++) {
                        i9 += (int) ((long) SoundTrackInstrument.anIntArray184[(j7 + i5) - 1 - l9]
                                * (long) SoundFilter.anIntArrayArray57[0][l9] >> 16);
                    }
                    for (int j10 = 0; j10 < i6; j10++) {
                        i9 -= (int) ((long) SoundTrackInstrument.anIntArray184[j7 - 1 - j10]
                                * (long) SoundFilter.anIntArrayArray57[1][j10] >> 16);
                    }
                    SoundTrackInstrument.anIntArray184[j7] = i9;
                    aClass1_181.method151(0, i + 1);
                }
            }
        }
        for (int i4 = 0; i4 < i; i4++) {
            if (SoundTrackInstrument.anIntArray184[i4] < -32768) {
                SoundTrackInstrument.anIntArray184[i4] = -32768;
            }
            if (SoundTrackInstrument.anIntArray184[i4] > 32767) {
                SoundTrackInstrument.anIntArray184[i4] = 32767;
            }
        }
        return SoundTrackInstrument.anIntArray184;
    }

    private int method189(int i, int j, int k, int l) {
        try {
            if (j != 0) {
                anInt165 = 302;
            }
            if (l == 1) {
                if ((k & 0x7fff) < 16384) {
                    return i;
                } else {
                    return -i;
                }
            }
            if (l == 2) {
                return SoundTrackInstrument.anIntArray186[k & 0x7fff] * i >> 14;
            }
            if (l == 3) {
                return ((k & 0x7fff) * i >> 14) - i;
            }
            if (l == 4) {
                return SoundTrackInstrument.anIntArray185[k / 2607 & 0x7fff] * i;
            } else {
                return 0;
            }
        } catch (RuntimeException runtimeexception) {
            SignLink.error("32871, " + i + ", " + j + ", " + k + ", " + l + ", " + runtimeexception.toString());
        }
        throw new RuntimeException();
    }

    public void method190(boolean flag, Buffer class44_sub3_sub2) {
        try {
            if (flag) {
                throw new NullPointerException();
            }
            aClass1_167 = new SoundTrackEnvelope();
            aClass1_167.method148(false, class44_sub3_sub2);
            aClass1_168 = new SoundTrackEnvelope();
            aClass1_168.method148(false, class44_sub3_sub2);
            int i = class44_sub3_sub2.readUnsignedByte();
            if (i != 0) {
                class44_sub3_sub2.offset--;
                aClass1_169 = new SoundTrackEnvelope();
                aClass1_169.method148(false, class44_sub3_sub2);
                aClass1_170 = new SoundTrackEnvelope();
                aClass1_170.method148(false, class44_sub3_sub2);
            }
            i = class44_sub3_sub2.readUnsignedByte();
            if (i != 0) {
                class44_sub3_sub2.offset--;
                aClass1_171 = new SoundTrackEnvelope();
                aClass1_171.method148(false, class44_sub3_sub2);
                aClass1_172 = new SoundTrackEnvelope();
                aClass1_172.method148(false, class44_sub3_sub2);
            }
            i = class44_sub3_sub2.readUnsignedByte();
            if (i != 0) {
                class44_sub3_sub2.offset--;
                aClass1_173 = new SoundTrackEnvelope();
                aClass1_173.method148(false, class44_sub3_sub2);
                aClass1_174 = new SoundTrackEnvelope();
                aClass1_174.method148(false, class44_sub3_sub2);
            }
            for (int j = 0; j < 10; j++) {
                int k = class44_sub3_sub2.readUnsignedSmartB();
                if (k == 0) {
                    break;
                }
                anIntArray175[j] = k;
                anIntArray176[j] = class44_sub3_sub2.readUnsignedSmartA();
                anIntArray177[j] = class44_sub3_sub2.readUnsignedSmartB();
            }
            anInt178 = class44_sub3_sub2.readUnsignedSmartB();
            anInt179 = class44_sub3_sub2.readUnsignedSmartB();
            anInt182 = class44_sub3_sub2.readUnsignedShort();
            anInt183 = class44_sub3_sub2.readUnsignedShort();
            aClass2_180 = new SoundFilter();
            aClass1_181 = new SoundTrackEnvelope();
            aClass2_180.method156(anInt166, aClass1_181, class44_sub3_sub2);
            return;
        } catch (RuntimeException runtimeexception) {
            SignLink.error("77565, " + flag + ", " + class44_sub3_sub2 + ", " + runtimeexception.toString());
        }
        throw new RuntimeException();
    }

}
