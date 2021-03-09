package com.kt289.client.cache.definition;

import com.kt289.util.buffer.Buffer;
import com.kt289.cache.Archive;
import com.kt289.util.SignLink;

public class Varp {

    private static boolean aBoolean671 = true;
    private static final int anInt672 = 810;
    private static int anInt673;
    public static Varp[] cache;
    private static int anInt675;
    private static int[] anIntArray676;
    private int anInt670;
    private boolean aBoolean680;
    private boolean aBoolean681;
    public int anInt682;
    private boolean aBoolean683;
    public boolean aBoolean686;
    private int anInt687;

    private Varp() {
        anInt670 = -550;
        aBoolean680 = false;
        aBoolean681 = true;
        aBoolean683 = false;
        aBoolean686 = false;
        anInt687 = -1;
    }

    public static void method337(boolean flag, Archive class47) {
        try {
            if (!flag) {
                Varp.aBoolean671 = !Varp.aBoolean671;
            }
            Buffer class44_sub3_sub2 = new Buffer(class47.decompressFile("varp.dat"));
            Varp.anInt675 = 0;
            Varp.anInt673 = class44_sub3_sub2.readUnsignedShort();
            if (Varp.cache == null) {
                Varp.cache = new Varp[Varp.anInt673];
            }
            if (Varp.anIntArray676 == null) {
                Varp.anIntArray676 = new int[Varp.anInt673];
            }
            for (int i = 0; i < Varp.anInt673; i++) {
                if (Varp.cache[i] == null) {
                    Varp.cache[i] = new Varp();
                }
                Varp.cache[i].method338(Varp.anInt672, i, class44_sub3_sub2);
            }
            if (class44_sub3_sub2.offset != class44_sub3_sub2.payload.length) {
                System.out.println("varptype load mismatch");
            }
        } catch (RuntimeException runtimeexception) {
            SignLink.error("51793, " + flag + ", " + class47 + ", " + runtimeexception.toString());
            throw new RuntimeException();
        }
    }

    private void method338(int i, int j, Buffer class44_sub3_sub2) {
        try {
            if (i <= 0) {
                anInt670 = 68;
            }
            do {
                int k = class44_sub3_sub2.readUnsignedByte();
                if (k == 0) {
                    return;
                }
                int anInt685;
                if (k == 1) {
                    int anInt678 = class44_sub3_sub2.readUnsignedByte();
                } else if (k == 2) {
                    int anInt679 = class44_sub3_sub2.readUnsignedByte();
                } else if (k == 3) {
                    aBoolean680 = true;
                    Varp.anIntArray676[Varp.anInt675++] = j;
                } else if (k == 4) {
                    aBoolean681 = false;
                } else if (k == 5) {
                    anInt682 = class44_sub3_sub2.readUnsignedShort();
                } else if (k == 6) {
                    aBoolean683 = true;
                } else if (k == 7) {
                    int anInt684 = class44_sub3_sub2.readUnsignedInt();
                } else if (k == 8) {
                    anInt685 = 1;
                    aBoolean686 = true;
                } else if (k == 10) {
                    String aString677 = class44_sub3_sub2.readString();
                } else if (k == 11) {
                    aBoolean686 = true;
                } else if (k == 12) {
                    anInt687 = class44_sub3_sub2.readUnsignedInt();
                } else if (k == 13) {
                    anInt685 = 2;
                } else {
                    System.out.println("Error unrecognised config code: " + k);
                }
            } while (true);
        } catch (RuntimeException runtimeexception) {
            SignLink.error("69668, " + i + ", " + j + ", " + class44_sub3_sub2 + ", "
                    + runtimeexception.toString());
        }
        throw new RuntimeException();
    }

}
