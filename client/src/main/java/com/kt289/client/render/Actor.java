package com.kt289.client.render;

import com.kt289.client.cache.definition.AnimationSequence;
import com.kt289.util.SignLink;

public class Actor extends Renderable {

    private boolean aBoolean1613;
    private boolean aBoolean1614;
    public int anInt1615;
    public int anInt1616;
    public int anInt1617;
    public boolean aBoolean1618;
    public int anInt1619;
    public int anInt1620;
    public int anInt1621;
    public int anInt1622;
    public int anInt1623;
    public int anInt1624;
    public int anInt1625;
    public int anInt1626;
    public String overheadTextMessage;
    public int textCycle;
    public int anInt1629;
    public int anInt1630;
    public final int[] anIntArray1631;
    public final int[] anIntArray1632;
    public final int[] anIntArray1633;
    public int loopCycleStatus;
    public int currentHealth;
    public int maxHealth;
    public int interactingEntity;
    public int faceTowardX;
    public int faceTowardY;
    public int anInt1640;
    public int anInt1641;
    public int anInt1642;
    public int animation;
    public int currentAnimationFrame;
    public int currentAnimationDuration;
    public int animationDelay;
    public int currentAnimationLoopCount;
    public int graphicId;
    public int currentAnimationId;
    public int currentAnimationTimeRemaining;
    public int graphicEndCycle;
    public int graphicHeight;
    public int anInt1653;
    public int anInt1654;
    public int anInt1655;
    public int anInt1656;
    public int anInt1657;
    public int anInt1658;
    public int anInt1659;
    public int lastUpdateTick;
    public int anInt1661;
    public int anInt1662;
    public int anInt1663;
    public int waypointCount;
    public final int[] waypointX;
    public final int[] waypointY;
    public final boolean[] aBooleanArray1667;
    public int anInt1668;
    public int stepsRemaining;

    Actor() {
        aBoolean1613 = false;
        aBoolean1614 = false;
        aBoolean1618 = false;
        anInt1619 = 1;
        anInt1620 = -1;
        anInt1621 = -1;
        anInt1622 = -1;
        anInt1623 = -1;
        anInt1624 = -1;
        anInt1625 = -1;
        anInt1626 = -1;
        textCycle = 100;
        anIntArray1631 = new int[4];
        anIntArray1632 = new int[4];
        anIntArray1633 = new int[4];
        loopCycleStatus = -1000;
        interactingEntity = -1;
        anInt1640 = -1;
        animation = -1;
        graphicId = -1;
        anInt1661 = 200;
        anInt1663 = 32;
        waypointX = new int[10];
        waypointY = new int[10];
        aBooleanArray1667 = new boolean[10];
    }

    public void setPos(int i, boolean flag, int j, byte byte0) {
        try {
            if (animation != -1 && AnimationSequence.cache[animation].anInt521 == 1) {
                animation = -1;
            }
            if (!flag) {
                int k = i - waypointX[0];
                int l = j - waypointY[0];
                if (k >= -8 && k <= 8 && l >= -8 && l <= 8) {
                    if (waypointCount < 9) {
                        waypointCount++;
                    }
                    for (int i1 = waypointCount; i1 > 0; i1--) {
                        waypointX[i1] = waypointX[i1 - 1];
                        waypointY[i1] = waypointY[i1 - 1];
                        aBooleanArray1667[i1] = aBooleanArray1667[i1 - 1];
                    }
                    waypointX[0] = i;
                    waypointY[0] = j;
                    aBooleanArray1667[0] = false;
                    return;
                }
            }
            waypointCount = 0;
            stepsRemaining = 0;
            anInt1668 = 0;
            waypointX[0] = i;
            waypointY[0] = j;
            anInt1615 = waypointX[0] * 128 + anInt1619 * 64;
            if (byte0 == 1) {
                byte0 = 0;
            } else {
                aBoolean1613 = !aBoolean1613;
            }
            anInt1616 = waypointY[0] * 128 + anInt1619 * 64;
            return;
        } catch (RuntimeException runtimeexception) {
            SignLink.error("61882, " + i + ", " + flag + ", " + j + ", " + byte0 + ", "
                    + runtimeexception.toString());
        }
        throw new RuntimeException();
    }

    public void move(boolean flag, int i, int j) {
        try {
            int k = waypointX[0];
            int l = waypointY[0];
            if (i == 0) {
                k--;
                l++;
            }
            if (i == 1) {
                l++;
            }
            if (i == 2) {
                k++;
                l++;
            }
            if (i == 3) {
                k--;
            }
            if (i == 4) {
                k++;
            }
            if (i == 5) {
                k--;
                l--;
            }
            if (i == 6) {
                l--;
            }
            if (i == 7) {
                k++;
                l--;
            }
            if (animation != -1 && AnimationSequence.cache[animation].anInt521 == 1) {
                animation = -1;
            }
            if (waypointCount < 9) {
                waypointCount++;
            }
            for (int i1 = waypointCount; i1 > 0; i1--) {
                waypointX[i1] = waypointX[i1 - 1];
                waypointY[i1] = waypointY[i1 - 1];
                aBooleanArray1667[i1] = aBooleanArray1667[i1 - 1];
            }
            waypointX[0] = k;
            waypointY[0] = l;
            aBooleanArray1667[0] = flag;
            if (j != -6002) {
            }
        } catch (RuntimeException runtimeexception) {
            SignLink.error("37957, " + flag + ", " + i + ", " + j + ", " + runtimeexception.toString());
            throw new RuntimeException();
        }
    }

    public void method534(boolean flag) {
        try {
            if (flag) {
                aBoolean1614 = !aBoolean1614;
            }
            waypointCount = 0;
            stepsRemaining = 0;
            return;
        } catch (RuntimeException runtimeexception) {
            SignLink.error("39799, " + flag + ", " + runtimeexception.toString());
        }
        throw new RuntimeException();
    }

    public boolean method535(boolean flag) {
        try {
            if (!flag) {
                throw new NullPointerException();
            } else {
                return false;
            }
        } catch (RuntimeException runtimeexception) {
            SignLink.error("30413, " + flag + ", " + runtimeexception.toString());
        }
        throw new RuntimeException();
    }

    public void updateHitData(int i, int j, int k, boolean flag) {
        try {
            if (flag) {
                return;
            }
            for (int l = 0; l < 4; l++) {
                if (anIntArray1633[l] <= j) {
                    anIntArray1631[l] = k;
                    anIntArray1632[l] = i;
                    anIntArray1633[l] = j + 70;
                    return;
                }
            }
            return;
        } catch (RuntimeException runtimeexception) {
            SignLink.error("15311, " + i + ", " + j + ", " + k + ", " + flag + ", " + runtimeexception.toString());
        }
        throw new RuntimeException();
    }
}
