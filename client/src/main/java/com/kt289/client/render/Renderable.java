package com.kt289.client.render;

import com.kt289.util.SignLink;
import com.kt289.util.aggregation.CacheableNode;

public class Renderable extends CacheableNode {

    public static boolean aBoolean1413;
    private int anInt1410;
    public VertexNormal[] vertexNormals;
    public int modelHeight;

    protected Renderable() {
        anInt1410 = 772;
        modelHeight = 1000;
    }

    public void renderAtPoint(int i, int j, int k, int l, int i1, int j1, int k1, int l1, int i2) {
        Model class44_sub3_sub4_sub4 = getRotatedModel(-37770);
        if (class44_sub3_sub4_sub4 != null) {
            modelHeight = class44_sub3_sub4_sub4.modelHeight;
            class44_sub3_sub4_sub4.renderAtPoint(i, j, k, l, i1, j1, k1, l1, i2);
        }
    }

    Model getRotatedModel(int i) {
        try {
            if (i != -37770) {
                anInt1410 = 414;
            }
            return null;
        } catch (RuntimeException runtimeexception) {
            SignLink.error("59009, " + i + ", " + runtimeexception.toString());
        }
        throw new RuntimeException();
    }
}
