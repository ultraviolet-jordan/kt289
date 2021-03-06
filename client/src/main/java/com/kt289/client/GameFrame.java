package com.kt289.client;

import java.awt.*;

@SuppressWarnings("serial")
class GameFrame extends Frame {

    private final GameShell gameShell;

    public GameFrame(int width, int height, GameShell gameShell) {
        this.gameShell = gameShell;
        setTitle(Settings.NAME);
        setResizable(false);
        setVisible(true);
        toFront();
        setSize(width + 8, height + 28);
    }

    @Override
    public Graphics getGraphics() {
        Graphics g = super.getGraphics();
        g.translate(4, 24);
        return g;
    }

    @Override
    public void update(Graphics g) {
        gameShell.update(g);
    }

    @Override
    public void paint(Graphics g) {
        gameShell.paint(g);
    }
}
