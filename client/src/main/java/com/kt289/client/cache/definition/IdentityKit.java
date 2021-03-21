package com.kt289.client.cache.definition;

import com.kt289.util.buffer.Buffer;
import com.kt289.cache.Archive;
import com.kt289.client.render.Model;
import com.kt289.util.SignLink;

public class IdentityKit {

    private static boolean aBoolean462;
    public static int anInt463;
    public static IdentityKit[] cache;
    private final byte aByte460;
    public int anInt465;
    private int[] anIntArray466;
    private final int[] anIntArray467;
    private final int[] anIntArray468;
    private final int[] anIntArray469 = {-1, -1, -1, -1, -1};
    public boolean aBoolean470;

    private IdentityKit() {
        boolean aBoolean459 = false;
        aByte460 = 4;
        anInt465 = -1;
        anIntArray467 = new int[6];
        anIntArray468 = new int[6];
        aBoolean470 = false;
    }

    public static void method247(boolean flag, Archive class47) {
        try {
            Buffer class44_sub3_sub2 = new Buffer(class47.decompressFile("idk.dat"));
            if (!flag) {
                IdentityKit.aBoolean462 = !IdentityKit.aBoolean462;
            }
            IdentityKit.anInt463 = class44_sub3_sub2.readUnsignedShort();
            if (IdentityKit.cache == null) {
                IdentityKit.cache = new IdentityKit[IdentityKit.anInt463];
            }
            for (int i = 0; i < IdentityKit.anInt463; i++) {
                if (IdentityKit.cache[i] == null) {
                    IdentityKit.cache[i] = new IdentityKit();
                }
                IdentityKit.cache[i].method248(false, class44_sub3_sub2);
            }
            return;
        } catch (RuntimeException runtimeexception) {
            SignLink.error("42475, " + flag + ", " + class47 + ", " + runtimeexception.toString());
        }
        throw new RuntimeException();
    }

    private void method248(boolean flag, Buffer class44_sub3_sub2) {
        try {
            if (flag) {
                throw new NullPointerException();
            }
            do {
                int i = class44_sub3_sub2.readUnsignedByte();
                if (i == 0) {
                    return;
                }
                if (i == 1) {
                    anInt465 = class44_sub3_sub2.readUnsignedByte();
                } else if (i == 2) {
                    int j = class44_sub3_sub2.readUnsignedByte();
                    anIntArray466 = new int[j];
                    for (int k = 0; k < j; k++) {
                        anIntArray466[k] = class44_sub3_sub2.readUnsignedShort();
                    }
                } else if (i == 3) {
                    aBoolean470 = true;
                } else if (i >= 40 && i < 50) {
                    anIntArray467[i - 40] = class44_sub3_sub2.readUnsignedShort();
                } else if (i >= 50 && i < 60) {
                    anIntArray468[i - 50] = class44_sub3_sub2.readUnsignedShort();
                } else if (i >= 60 && i < 70) {
                    anIntArray469[i - 60] = class44_sub3_sub2.readUnsignedShort();
                } else {
                    System.out.println("Error unrecognised config code: " + i);
                }
            } while (true);
        } catch (RuntimeException runtimeexception) {
            SignLink.error("93901, " + flag + ", " + class44_sub3_sub2 + ", " + runtimeexception.toString());
        }
        throw new RuntimeException();
    }

    public boolean method249(int i) {
        try {
            if (anIntArray466 == null) {
                return true;
            }
            boolean flag = true;
            if (i != 9) {
                throw new NullPointerException();
            }
            for (int j = 0; j < anIntArray466.length; j++) {
                if (!Model.isCached(anIntArray466[j])) {
                    flag = false;
                }
            }
            return flag;
        } catch (RuntimeException runtimeexception) {
            SignLink.error("47258, " + i + ", " + runtimeexception.toString());
        }
        throw new RuntimeException();
    }

    public Model method250(int i) {
        try {
            if (anIntArray466 == null) {
                return null;
            }
            Model[] aclass44_sub3_sub4_sub4 = new Model[anIntArray466.length];
            if (i != 0) {
                int anInt461 = 428;
            }
            for (int j = 0; j < anIntArray466.length; j++) {
                aclass44_sub3_sub4_sub4[j] = Model.getModel(anIntArray466[j]);
            }
            Model class44_sub3_sub4_sub4;
            if (aclass44_sub3_sub4_sub4.length == 1) {
                class44_sub3_sub4_sub4 = aclass44_sub3_sub4_sub4[0];
            } else {
                class44_sub3_sub4_sub4 = new Model(aclass44_sub3_sub4_sub4, aclass44_sub3_sub4_sub4.length);
            }
            for (int k = 0; k < 6; k++) {
                if (anIntArray467[k] == 0) {
                    break;
                }
                class44_sub3_sub4_sub4.recolor(anIntArray467[k], anIntArray468[k]);
            }
            return class44_sub3_sub4_sub4;
        } catch (RuntimeException runtimeexception) {
            SignLink.error("95134, " + i + ", " + runtimeexception.toString());
        }
        throw new RuntimeException();
    }

    public boolean method251(byte byte0) {
        try {
            if (byte0 != 0) {
                throw new NullPointerException();
            }
            boolean flag = true;
            for (int i = 0; i < 5; i++) {
                if (anIntArray469[i] != -1 && !Model.isCached(anIntArray469[i])) {
                    flag = false;
                }
            }
            return flag;
        } catch (RuntimeException runtimeexception) {
            SignLink.error("34430, " + byte0 + ", " + runtimeexception.toString());
        }
        throw new RuntimeException();
    }

    public Model method252(byte byte0) {
        try {
            Model[] aclass44_sub3_sub4_sub4 = new Model[5];
            int i = 0;
            for (int j = 0; j < 5; j++) {
                if (anIntArray469[j] != -1) {
                    aclass44_sub3_sub4_sub4[i++] = Model.getModel(anIntArray469[j]);
                }
            }
            if (byte0 != -45) {
                for (int k = 1; k > 0; k++) {
                }
            }
            Model class44_sub3_sub4_sub4 = new Model(aclass44_sub3_sub4_sub4, i);
            for (int l = 0; l < 6; l++) {
                if (anIntArray467[l] == 0) {
                    break;
                }
                class44_sub3_sub4_sub4.recolor(anIntArray467[l], anIntArray468[l]);
            }
            return class44_sub3_sub4_sub4;
        } catch (RuntimeException runtimeexception) {
            SignLink.error("40216, " + byte0 + ", " + runtimeexception.toString());
        }
        throw new RuntimeException();
    }
}
