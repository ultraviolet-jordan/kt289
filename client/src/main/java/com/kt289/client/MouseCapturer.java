package com.kt289.client;

import com.kt289.util.SignLink;

public class MouseCapturer implements Runnable {

    private Client aClient256;
    public boolean aBoolean257;
    public final Object anObject258;
    public int anInt259;
    public final int[] anIntArray260;
    public final int[] anIntArray261;

    public MouseCapturer(int i, Client client1) {
        aBoolean257 = true;
        anObject258 = new Object();
        anIntArray260 = new int[500];
        anIntArray261 = new int[500];
        try {
            i = 31 / i;
            aClient256 = client1;
            return;
        } catch (RuntimeException runtimeexception) {
            SignLink.error("34650, " + i + ", " + client1 + ", " + runtimeexception.toString());
        }
        throw new RuntimeException();
    }

    @Override
    public void run() {
        while (aBoolean257) {
            synchronized (anObject258) {
                if (anInt259 < 500) {
                    anIntArray260[anInt259] = aClient256.anInt21;
                    anIntArray261[anInt259] = aClient256.anInt22;
                    anInt259++;
                }
            }
            try {
                Thread.sleep(50L);
            } catch (Exception _ex) {
            }
        }
    }
}
