package com.kt289.client.render;

import com.kt289.client.cache.definition.ItemDefinition;
import com.kt289.client.graphic.Model;
import com.kt289.util.SignLink;

public class Item extends Renderable {

    private boolean aBoolean1494;
    public int anInt1495;
    public int anInt1496;

    public Item() {
        aBoolean1494 = true;
    }

    @Override
    public Model getRotatedModel(int i) {
        try {
            ItemDefinition class14 = ItemDefinition.method220(anInt1495);
            if (i != -37770) {
                aBoolean1494 = !aBoolean1494;
            }
            return class14.method224(anInt1496);
        } catch (RuntimeException runtimeexception) {
            SignLink.error("51746, " + i + ", " + runtimeexception.toString());
        }
        throw new RuntimeException();
    }
}
