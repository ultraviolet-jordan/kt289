package com.kt289.client.cache.definition;

import com.kt289.util.buffer.Buffer;
import com.kt289.cache.Archive;
import com.kt289.client.render.Model;
import com.kt289.util.SignLink;
import com.kt289.util.aggregation.Cache;

public class SpotAnimation {

    private static int anInt558;
    public static SpotAnimation[] cache;
    public static Cache aClass39_571 = new Cache(30);
    private final byte aByte557;
    private int anInt560;
    private int anInt561;
    private int anInt562;
    public AnimationSequence aClass26_563;
    private final int[] anIntArray564;
    private final int[] anIntArray565;
    public int anInt566;
    public int anInt567;
    public int anInt568;
    public int anInt569;
    public int anInt570;

    private SpotAnimation() {
        boolean aBoolean556 = false;
        aByte557 = 4;
        anInt562 = -1;
        anIntArray564 = new int[6];
        anIntArray565 = new int[6];
        anInt566 = 128;
        anInt567 = 128;
    }

    public static void method269(boolean flag, Archive class47) {
        try {
            Buffer class44_sub3_sub2 = new Buffer(class47.decompressFile("spotanim.dat"));
            SpotAnimation.anInt558 = class44_sub3_sub2.readUnsignedShort();
            if (!flag) {
                return;
            }
            if (SpotAnimation.cache == null) {
                SpotAnimation.cache = new SpotAnimation[SpotAnimation.anInt558];
            }
            for (int i = 0; i < SpotAnimation.anInt558; i++) {
                if (SpotAnimation.cache[i] == null) {
                    SpotAnimation.cache[i] = new SpotAnimation();
                }
                SpotAnimation.cache[i].anInt560 = i;
                SpotAnimation.cache[i].method270(false, class44_sub3_sub2);
            }
            return;
        } catch (RuntimeException runtimeexception) {
            SignLink.error("58052, " + flag + ", " + class47 + ", " + runtimeexception.toString());
        }
        throw new RuntimeException();
    }

    private void method270(boolean flag, Buffer class44_sub3_sub2) {
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
                    anInt561 = class44_sub3_sub2.readUnsignedShort();
                } else if (i == 2) {
                    anInt562 = class44_sub3_sub2.readUnsignedShort();
                    if (AnimationSequence.cache != null) {
                        aClass26_563 = AnimationSequence.cache[anInt562];
                    }
                } else if (i == 4) {
                    anInt566 = class44_sub3_sub2.readUnsignedShort();
                } else if (i == 5) {
                    anInt567 = class44_sub3_sub2.readUnsignedShort();
                } else if (i == 6) {
                    anInt568 = class44_sub3_sub2.readUnsignedShort();
                } else if (i == 7) {
                    anInt569 = class44_sub3_sub2.readUnsignedByte();
                } else if (i == 8) {
                    anInt570 = class44_sub3_sub2.readUnsignedByte();
                } else if (i >= 40 && i < 50) {
                    anIntArray564[i - 40] = class44_sub3_sub2.readUnsignedShort();
                } else if (i >= 50 && i < 60) {
                    anIntArray565[i - 50] = class44_sub3_sub2.readUnsignedShort();
                } else {
                    System.out.println("Error unrecognised spotanim config code: " + i);
                }
            } while (true);
        } catch (RuntimeException runtimeexception) {
            SignLink.error("87905, " + flag + ", " + class44_sub3_sub2 + ", " + runtimeexception.toString());
        }
        throw new RuntimeException();
    }

    public Model method271() {
        Model class44_sub3_sub4_sub4 = (Model) SpotAnimation.aClass39_571.get(anInt560);
        if (class44_sub3_sub4_sub4 != null) {
            return class44_sub3_sub4_sub4;
        }
        class44_sub3_sub4_sub4 = Model.getModel(anInt561);
        if (class44_sub3_sub4_sub4 == null) {
            return null;
        }
        for (int i = 0; i < 6; i++) {
            if (anIntArray564[0] != 0) {
                class44_sub3_sub4_sub4.recolor(anIntArray564[i], anIntArray565[i]);
            }
        }
        SpotAnimation.aClass39_571.put(anInt560, class44_sub3_sub4_sub4);
        return class44_sub3_sub4_sub4;
    }

}
