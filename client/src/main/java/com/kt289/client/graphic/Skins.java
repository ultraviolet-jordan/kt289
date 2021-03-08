package com.kt289.client.graphic;

import com.kt289.util.buffer.Buffer;
import com.kt289.util.SignLink;

public class Skins {

    public int[] opcodes;
    public int[][] skinList;

    public Skins(Buffer buffer, int i) {
        try {
            int anInt253 = buffer.readUnsignedByte();
            opcodes = new int[anInt253];
            if (i != 0) {
                throw new NullPointerException();
            }
            skinList = new int[anInt253][];
            for (int j = 0; j < anInt253; j++) {
                opcodes[j] = buffer.readUnsignedByte();
            }
            for (int k = 0; k < anInt253; k++) {
                int l = buffer.readUnsignedByte();
                skinList[k] = new int[l];
                for (int i1 = 0; i1 < l; i1++) {
                    skinList[k][i1] = buffer.readUnsignedByte();
                }
            }
            return;
        } catch (RuntimeException runtimeexception) {
            SignLink.error("27585, " + buffer + ", " + i + ", " + runtimeexception.toString());
        }
        throw new RuntimeException();
    }
}
