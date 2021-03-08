package com.kt289.client.render;

import com.kt289.client.cache.definition.Animation;
import com.kt289.client.cache.definition.SpotAnimation;
import com.kt289.client.graphic.Model;
import com.kt289.util.SignLink;

public class AnimableGameObject extends Renderable {

    private int anInt1602;
    private SpotAnimation aClass32_1604;
    public int anInt1605;
    public int anInt1606;
    public int anInt1607;
    public int anInt1608;
    public int anInt1609;
    private int anInt1610;
    private int anInt1611;
    public boolean aBoolean1612;

    public AnimableGameObject(int i, int j, int k, int l, boolean flag, int i1, int j1, int k1) {
        int anInt1603 = 393;
        aBoolean1612 = false;
        try {
            aClass32_1604 = SpotAnimation.cache[j1];
            if (!flag) {
                anInt1603 = 385;
            }
            anInt1606 = j;
            anInt1607 = i;
            anInt1608 = i1;
            anInt1609 = k;
            anInt1605 = k1 + l;
            aBoolean1612 = false;
            return;
        } catch (RuntimeException runtimeexception) {
            SignLink.error("81124, " + i + ", " + j + ", " + k + ", " + l + ", " + flag + ", " + i1 + ", " + j1
                    + ", " + k1 + ", " + runtimeexception.toString());
        }
        throw new RuntimeException();
    }

    public void method531(int i, int j) {
        try {
            if (i <= 0) {
                return;
            }
            for (anInt1611 += j; anInt1611 > aClass32_1604.aClass26_563.method254(anInt1610, 24425); ) {
                anInt1611 -= aClass32_1604.aClass26_563.method254(anInt1610, 24425) + 1;
                anInt1610++;
                if (anInt1610 >= aClass32_1604.aClass26_563.anInt509
                        && (anInt1610 < 0 || anInt1610 >= aClass32_1604.aClass26_563.anInt509)) {
                    anInt1610 = 0;
                    aBoolean1612 = true;
                }
            }
            return;
        } catch (RuntimeException runtimeexception) {
            SignLink.error("93520, " + i + ", " + j + ", " + runtimeexception.toString());
        }
        throw new RuntimeException();
    }

    @Override
    public Model getRotatedModel(int i) {
        try {
            if (i != -37770) {
                for (int j = 1; j > 0; j++) {
                }
            }
            Model class44_sub3_sub4_sub4 = aClass32_1604.method271();
            if (class44_sub3_sub4_sub4 == null) {
                return null;
            }
            int k = aClass32_1604.aClass26_563.anIntArray510[anInt1610];
            Model model = new Model(class44_sub3_sub4_sub4, Animation.isNullFrame(k, 0), false, true);
            if (!aBoolean1612) {
                model.createBones();
                model.applyTransformation(k);
                model.triangleSkin = null;
                model.vertexSkin = null;
            }
            if (aClass32_1604.anInt566 != 128 || aClass32_1604.anInt567 != 128) {
                model.scaleT(aClass32_1604.anInt566, aClass32_1604.anInt566, aClass32_1604.anInt567);
            }
            if (aClass32_1604.anInt568 != 0) {
                if (aClass32_1604.anInt568 == 90) {
                    model.rotate90Degrees();
                }
                if (aClass32_1604.anInt568 == 180) {
                    model.rotate90Degrees();
                    model.rotate90Degrees();
                }
                if (aClass32_1604.anInt568 == 270) {
                    model.rotate90Degrees();
                    model.rotate90Degrees();
                    model.rotate90Degrees();
                }
            }
            model.applyLighting(64 + aClass32_1604.anInt569, 850 + aClass32_1604.anInt570, -30, -50, -30, true);
            return model;
        } catch (RuntimeException runtimeexception) {
            SignLink.error("834, " + i + ", " + runtimeexception.toString());
        }
        throw new RuntimeException();
    }
}
