package com.kt289.client.cache.definition;

import com.kt289.util.buffer.Buffer;
import com.kt289.cache.Archive;
import com.kt289.util.SignLink;

public class VarBit {

    private static final int anInt588 = 810;
    private static int anInt589;
    public static VarBit[] aClass35Array590;
    public int anInt592;
    public int anInt593;
    public int anInt594;
    private boolean aBoolean595;
    private int anInt596;

    private VarBit() {
        aBoolean595 = false;
        anInt596 = -1;
    }

    public static void method275(boolean flag, Archive class47) {
        try {
            if (!flag) {
                return;
            }
            Buffer class44_sub3_sub2 = new Buffer(class47.decompressFile("varbit.dat"));
            VarBit.anInt589 = class44_sub3_sub2.readUnsignedShort();
            if (VarBit.aClass35Array590 == null) {
                VarBit.aClass35Array590 = new VarBit[VarBit.anInt589];
            }
            for (int i = 0; i < VarBit.anInt589; i++) {
                if (VarBit.aClass35Array590[i] == null) {
                    VarBit.aClass35Array590[i] = new VarBit();
                }
                VarBit.aClass35Array590[i].method276(VarBit.anInt588, i, class44_sub3_sub2);
                if (VarBit.aClass35Array590[i].aBoolean595) {
                    Varp.cache[VarBit.aClass35Array590[i].anInt592].aBoolean686 = true;
                }
            }
            if (class44_sub3_sub2.offset != class44_sub3_sub2.payload.length) {
                System.out.println("varbit load mismatch");
            }
        } catch (RuntimeException runtimeexception) {
            SignLink.error("10921, " + flag + ", " + class47 + ", " + runtimeexception.toString());
            throw new RuntimeException();
        }
    }

    private void method276(int i, int j, Buffer class44_sub3_sub2) {
        try {
            i = 55 / i;
            do {
                int k = class44_sub3_sub2.readUnsignedByte();
                if (k == 0) {
                    return;
                }
                if (k == 1) {
                    anInt592 = class44_sub3_sub2.readUnsignedShort();
                    anInt593 = class44_sub3_sub2.readUnsignedByte();
                    anInt594 = class44_sub3_sub2.readUnsignedByte();
                } else if (k == 10) {
                    String aString591 = class44_sub3_sub2.readString();
                } else if (k == 2) {
                    aBoolean595 = true;
                } else if (k == 3) {
                    anInt596 = class44_sub3_sub2.readUnsignedInt();
                } else if (k == 4) {
                    int anInt597 = class44_sub3_sub2.readUnsignedInt();
                } else {
                    System.out.println("Error unrecognised config code: " + k);
                }
            } while (true);
        } catch (RuntimeException runtimeexception) {
            SignLink.error("19355, " + i + ", " + j + ", " + class44_sub3_sub2 + ", "
                    + runtimeexception.toString());
        }
        throw new RuntimeException();
    }

}
