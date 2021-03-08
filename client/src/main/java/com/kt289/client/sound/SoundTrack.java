package com.kt289.client.sound;

import com.kt289.util.SignLink;
import com.kt289.util.buffer.Buffer;

public class SoundTrack {

    private static final SoundTrack[] aClass4Array91 = new SoundTrack[1000];
    public static final int[] anIntArray92 = new int[1000];
    private static byte[] aByteArray93;
    private static Buffer buffer;
    private boolean aBoolean89;
    private boolean aBoolean90;
    private final SoundTrackInstrument[] aClass6Array95;
    private int anInt96;
    private int anInt97;

    private SoundTrack(byte byte0) {
        boolean aBoolean88 = false;
        aBoolean89 = false;
        aBoolean90 = true;
        aClass6Array95 = new SoundTrackInstrument[10];
        try {
            if (byte0 != 58) {
                for (int i = 1; i > 0; i++) {
                }
            }
        } catch (RuntimeException runtimeexception) {
            SignLink.error("32850, " + byte0 + ", " + runtimeexception.toString());
            throw new RuntimeException();
        }
    }

    public static void method175(boolean flag, Buffer class44_sub3_sub2) {
        try {
            SoundTrack.aByteArray93 = new byte[0x6baa8];
            if (!flag) {
                return;
            }
            SoundTrack.buffer = new Buffer(SoundTrack.aByteArray93);
            SoundTrackInstrument.method187();
            do {
                int i = class44_sub3_sub2.readUnsignedShort();
                if (i == 65535) {
                    return;
                }
                SoundTrack.aClass4Array91[i] = new SoundTrack((byte) 58);
                SoundTrack.aClass4Array91[i].method177(false, class44_sub3_sub2);
                SoundTrack.anIntArray92[i] = SoundTrack.aClass4Array91[i].method178(368);
            } while (true);
        } catch (RuntimeException runtimeexception) {
            SignLink.error("47278, " + flag + ", " + class44_sub3_sub2 + ", " + runtimeexception.toString());
        }
        throw new RuntimeException();
    }

    public static Buffer method176(int i, int j, int k) {
        try {
            if (j != 7) {
                throw new NullPointerException();
            }
            if (SoundTrack.aClass4Array91[k] != null) {
                SoundTrack class4 = SoundTrack.aClass4Array91[k];
                return class4.method179(i, 873);
            } else {
                return null;
            }
        } catch (RuntimeException runtimeexception) {
            SignLink.error("54696, " + i + ", " + j + ", " + k + ", " + runtimeexception.toString());
        }
        throw new RuntimeException();
    }

    private void method177(boolean flag, Buffer class44_sub3_sub2) {
        try {
            for (int i = 0; i < 10; i++) {
                int j = class44_sub3_sub2.readUnsignedByte();
                if (j != 0) {
                    class44_sub3_sub2.offset--;
                    aClass6Array95[i] = new SoundTrackInstrument();
                    aClass6Array95[i].method190(false, class44_sub3_sub2);
                }
            }
            if (flag) {
                aBoolean90 = !aBoolean90;
            }
            anInt96 = class44_sub3_sub2.readUnsignedShort();
            anInt97 = class44_sub3_sub2.readUnsignedShort();
            return;
        } catch (RuntimeException runtimeexception) {
            SignLink.error("59639, " + flag + ", " + class44_sub3_sub2 + ", " + runtimeexception.toString());
        }
        throw new RuntimeException();
    }

    private int method178(int i) {
        try {
            int j = 0x98967f;
            if (i <= 0) {
                aBoolean89 = !aBoolean89;
            }
            for (int k = 0; k < 10; k++) {
                if (aClass6Array95[k] != null && aClass6Array95[k].anInt183 / 20 < j) {
                    j = aClass6Array95[k].anInt183 / 20;
                }
            }
            if (anInt96 < anInt97 && anInt96 / 20 < j) {
                j = anInt96 / 20;
            }
            if (j == 0x98967f || j == 0) {
                return 0;
            }
            for (int l = 0; l < 10; l++) {
                if (aClass6Array95[l] != null) {
                    aClass6Array95[l].anInt183 -= j * 20;
                }
            }
            if (anInt96 < anInt97) {
                anInt96 -= j * 20;
                anInt97 -= j * 20;
            }
            return j;
        } catch (RuntimeException runtimeexception) {
            SignLink.error("95859, " + i + ", " + runtimeexception.toString());
        }
        throw new RuntimeException();
    }

    private Buffer method179(int i, int j) {
        try {
            int k = method180(i);
            SoundTrack.buffer.offset = 0;
            SoundTrack.buffer.writeInt(0x52494646);
            SoundTrack.buffer.writeLEInt(36 + k);
            SoundTrack.buffer.writeInt(0x57415645);
            SoundTrack.buffer.writeInt(0x666d7420);
            SoundTrack.buffer.writeLEInt(16);
            SoundTrack.buffer.writeLEShort(1);
            SoundTrack.buffer.writeLEShort(1);
            SoundTrack.buffer.writeLEInt(22050);
            SoundTrack.buffer.writeLEInt(22050);
            j = 98 / j;
            SoundTrack.buffer.writeLEShort(1);
            SoundTrack.buffer.writeLEShort(8);
            SoundTrack.buffer.writeInt(0x64617461);
            SoundTrack.buffer.writeLEInt(k);
            SoundTrack.buffer.offset += k;
            return SoundTrack.buffer;
        } catch (RuntimeException runtimeexception) {
            SignLink.error("32783, " + i + ", " + j + ", " + runtimeexception.toString());
        }
        throw new RuntimeException();
    }

    private int method180(int i) {
        int j = 0;
        for (int k = 0; k < 10; k++) {
            if (aClass6Array95[k] != null && aClass6Array95[k].anInt182 + aClass6Array95[k].anInt183 > j) {
                j = aClass6Array95[k].anInt182 + aClass6Array95[k].anInt183;
            }
        }
        if (j == 0) {
            return 0;
        }
        int l = (22050 * j) / 1000;
        int i1 = (22050 * anInt96) / 1000;
        int j1 = (22050 * anInt97) / 1000;
        if (i1 < 0 || i1 > l || j1 < 0 || j1 > l || i1 >= j1) {
            i = 0;
        }
        int k1 = l + (j1 - i1) * (i - 1);
        for (int l1 = 44; l1 < k1 + 44; l1++) {
            SoundTrack.aByteArray93[l1] = -128;
        }
        for (int i2 = 0; i2 < 10; i2++) {
            if (aClass6Array95[i2] != null) {
                int j2 = (aClass6Array95[i2].anInt182 * 22050) / 1000;
                int i3 = (aClass6Array95[i2].anInt183 * 22050) / 1000;
                int[] ai = aClass6Array95[i2].method188(j2, aClass6Array95[i2].anInt182);
                for (int l3 = 0; l3 < j2; l3++) {
                    SoundTrack.aByteArray93[l3 + i3 + 44] += (byte) (ai[l3] >> 8);
                }
            }
        }
        if (i > 1) {
            i1 += 44;
            j1 += 44;
            l += 44;
            int k2 = (k1 += 44) - l;
            for (int j3 = l - 1; j3 >= j1; j3--) {
                SoundTrack.aByteArray93[j3 + k2] = SoundTrack.aByteArray93[j3];
            }
            for (int k3 = 1; k3 < i; k3++) {
                int l2 = (j1 - i1) * k3;
                for (int i4 = i1; i4 < j1; i4++) {
                    SoundTrack.aByteArray93[i4 + l2] = SoundTrack.aByteArray93[i4];
                }
            }
            k1 -= 44;
        }
        return k1;
    }

}
