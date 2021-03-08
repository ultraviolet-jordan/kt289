package com.kt289.client.graphic;

import com.kt289.util.SignLink;

import java.awt.*;
import java.awt.image.*;

public class ProducingGraphicsBuffer implements ImageProducer, ImageObserver {

    public int[] anIntArray582;
    private int anInt583;
    private int anInt584;
    private ColorModel aColorModel585;
    private ImageConsumer anImageConsumer586;
    private Image anImage587;

    public ProducingGraphicsBuffer(Component component, int i, int j, int k) {
        int anInt581 = 831;
        try {
            anInt583 = i;
            anInt584 = j;
            anIntArray582 = new int[i * j];
            aColorModel585 = new DirectColorModel(32, 0xff0000, 65280, 255);
            anImage587 = component.createImage(this);
            method274();
            if (k != 2) {
                anInt581 = 205;
            }
            component.prepareImage(anImage587, this);
            method274();
            component.prepareImage(anImage587, this);
            method274();
            component.prepareImage(anImage587, this);
            method272((byte) 5);
            return;
        } catch (RuntimeException runtimeexception) {
            SignLink.error("48102, " + component + ", " + i + ", " + j + ", " + k + ", "
                    + runtimeexception.toString());
        }
        throw new RuntimeException();
    }

    public void method272(byte byte0) {
        try {
            Rasterizer.method406(-78, anIntArray582, anInt583, anInt584);
            if (byte0 != 5) {
            }
        } catch (RuntimeException runtimeexception) {
            SignLink.error("43640, " + byte0 + ", " + runtimeexception.toString());
            throw new RuntimeException();
        }
    }

    public void method273(int i, int j, int k, Graphics g) {
        try {
            method274();
            while (i >= 0) {
                for (int l = 1; l > 0; l++) {
                }
            }
            g.drawImage(anImage587, j, k, this);
            return;
        } catch (RuntimeException runtimeexception) {
            SignLink.error("14457, " + i + ", " + j + ", " + k + ", " + g + ", " + runtimeexception.toString());
        }
        throw new RuntimeException();
    }

    @Override
    public synchronized void addConsumer(ImageConsumer imageconsumer) {
        anImageConsumer586 = imageconsumer;
        imageconsumer.setDimensions(anInt583, anInt584);
        imageconsumer.setProperties(null);
        imageconsumer.setColorModel(aColorModel585);
        imageconsumer.setHints(14);
    }

    @Override
    public synchronized boolean isConsumer(ImageConsumer imageconsumer) {
        return anImageConsumer586 == imageconsumer;
    }

    @Override
    public synchronized void removeConsumer(ImageConsumer imageconsumer) {
        if (anImageConsumer586 == imageconsumer) {
            anImageConsumer586 = null;
        }
    }

    @Override
    public void startProduction(ImageConsumer imageconsumer) {
        addConsumer(imageconsumer);
    }

    @Override
    public void requestTopDownLeftRightResend(ImageConsumer imageconsumer) {
        System.out.println("TDLR");
    }

    private synchronized void method274() {
        if (anImageConsumer586 == null) {
        } else {
            anImageConsumer586.setPixels(0, 0, anInt583, anInt584, aColorModel585, anIntArray582, 0, anInt583);
            anImageConsumer586.imageComplete(2);
        }
    }

    @Override
    public boolean imageUpdate(Image image, int i, int j, int k, int l, int i1) {
        return true;
    }
}
