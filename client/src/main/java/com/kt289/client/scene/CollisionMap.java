package com.kt289.client.scene;

import com.kt289.util.SignLink;

public class CollisionMap {

    private boolean aBoolean406;
    private int anInt408;
    private int anInt409;
    private final int anInt412;
    private int anInt414;
    private int anInt415;
    private int anInt416;
    private int anInt417;
    public int[][] anIntArrayArray418;

    public CollisionMap(int i, int j, int k) {
        int anInt405 = -550;
        aBoolean406 = false;
        anInt408 = 8;
        anInt409 = 70;
        byte aByte410 = 99;
        boolean aBoolean411 = true;
        anInt412 = 1;
        boolean aBoolean413 = true;
        try {
            anInt414 = 0;
            anInt415 = 0;
            anInt416 = k;
            anInt417 = j;
            anIntArrayArray418 = new int[anInt416][anInt417];
            i = 13 / i;
            method231(3);
        } catch (RuntimeException runtimeexception) {
            SignLink.error("80242, " + i + ", " + j + ", " + k + ", " + runtimeexception.toString());
            throw new RuntimeException();
        }
    }

    public void method231(int i) {
        try {
            for (int j = 0; j < anInt416; j++) {
                for (int k = 0; k < anInt417; k++) {
                    if (j == 0 || k == 0 || j == anInt416 - 1 || k == anInt417 - 1) {
                        anIntArrayArray418[j][k] = 0xffffff;
                    } else {
                        anIntArrayArray418[j][k] = 0;
                    }
                }
            }
            if (i != 3) {
            }
        } catch (RuntimeException runtimeexception) {
            SignLink.error("70688, " + i + ", " + runtimeexception.toString());
            throw new RuntimeException();
        }
    }

    public void method232(boolean flag, int i, boolean flag1, int j, int k, int l) {
        try {
            k -= anInt414;
            l -= anInt415;
            if (!flag) {
                anInt409 = -69;
            }
            if (j == 0) {
                if (i == 0) {
                    method235(k, l, 128);
                    method235(k - 1, l, 8);
                }
                if (i == 1) {
                    method235(k, l, 2);
                    method235(k, l + 1, 32);
                }
                if (i == 2) {
                    method235(k, l, 8);
                    method235(k + 1, l, 128);
                }
                if (i == 3) {
                    method235(k, l, 32);
                    method235(k, l - 1, 2);
                }
            }
            if (j == 1 || j == 3) {
                if (i == 0) {
                    method235(k, l, 1);
                    method235(k - 1, l + 1, 16);
                }
                if (i == 1) {
                    method235(k, l, 4);
                    method235(k + 1, l + 1, 64);
                }
                if (i == 2) {
                    method235(k, l, 16);
                    method235(k + 1, l - 1, 1);
                }
                if (i == 3) {
                    method235(k, l, 64);
                    method235(k - 1, l - 1, 4);
                }
            }
            if (j == 2) {
                if (i == 0) {
                    method235(k, l, 130);
                    method235(k - 1, l, 8);
                    method235(k, l + 1, 32);
                }
                if (i == 1) {
                    method235(k, l, 10);
                    method235(k, l + 1, 32);
                    method235(k + 1, l, 128);
                }
                if (i == 2) {
                    method235(k, l, 40);
                    method235(k + 1, l, 128);
                    method235(k, l - 1, 2);
                }
                if (i == 3) {
                    method235(k, l, 160);
                    method235(k, l - 1, 2);
                    method235(k - 1, l, 8);
                }
            }
            if (flag1) {
                if (j == 0) {
                    if (i == 0) {
                        method235(k, l, 0x10000);
                        method235(k - 1, l, 4096);
                    }
                    if (i == 1) {
                        method235(k, l, 1024);
                        method235(k, l + 1, 16384);
                    }
                    if (i == 2) {
                        method235(k, l, 4096);
                        method235(k + 1, l, 0x10000);
                    }
                    if (i == 3) {
                        method235(k, l, 16384);
                        method235(k, l - 1, 1024);
                    }
                }
                if (j == 1 || j == 3) {
                    if (i == 0) {
                        method235(k, l, 512);
                        method235(k - 1, l + 1, 8192);
                    }
                    if (i == 1) {
                        method235(k, l, 2048);
                        method235(k + 1, l + 1, 32768);
                    }
                    if (i == 2) {
                        method235(k, l, 8192);
                        method235(k + 1, l - 1, 512);
                    }
                    if (i == 3) {
                        method235(k, l, 32768);
                        method235(k - 1, l - 1, 2048);
                    }
                }
                if (j == 2) {
                    if (i == 0) {
                        method235(k, l, 0x10400);
                        method235(k - 1, l, 4096);
                        method235(k, l + 1, 16384);
                    }
                    if (i == 1) {
                        method235(k, l, 5120);
                        method235(k, l + 1, 16384);
                        method235(k + 1, l, 0x10000);
                    }
                    if (i == 2) {
                        method235(k, l, 20480);
                        method235(k + 1, l, 0x10000);
                        method235(k, l - 1, 1024);
                    }
                    if (i == 3) {
                        method235(k, l, 0x14000);
                        method235(k, l - 1, 1024);
                        method235(k - 1, l, 4096);
                    }
                }
            }
        } catch (RuntimeException runtimeexception) {
            SignLink.error("37924, " + flag + ", " + i + ", " + flag1 + ", " + j + ", " + k + ", " + l + ", "
                    + runtimeexception.toString());
            throw new RuntimeException();
        }
    }

    public void method233(int i, int j, boolean flag, int k, int l, int i1, int j1) {
        try {
            int k1 = 256;
            if (flag) {
                k1 += 0x20000;
            }
            l -= anInt414;
            i -= anInt415;
            if (j1 == 1 || j1 == 3) {
                int l1 = j;
                j = i1;
                i1 = l1;
            }
            for (int i2 = l; i2 < l + j; i2++) {
                if (i2 >= 0 && i2 < anInt416) {
                    for (int j2 = i; j2 < i + i1; j2++) {
                        if (j2 >= 0 && j2 < anInt417) {
                            method235(i2, j2, k1);
                        }
                    }
                }
            }
            if (k != -24520) {
                for (int k2 = 1; k2 > 0; k2++) {
                }
            }
        } catch (RuntimeException runtimeexception) {
            SignLink.error("81800, " + i + ", " + j + ", " + flag + ", " + k + ", " + l + ", " + i1 + ", " + j1
                    + ", " + runtimeexception.toString());
            throw new RuntimeException();
        }
    }

    public void method234(int i, byte byte0, int j) {
        try {
            i -= anInt414;
            if (byte0 != 0) {
                return;
            } else {
                j -= anInt415;
                anIntArrayArray418[i][j] |= 0x200000;
                return;
            }
        } catch (RuntimeException runtimeexception) {
            SignLink.error("80528, " + i + ", " + byte0 + ", " + j + ", " + runtimeexception.toString());
        }
        throw new RuntimeException();
    }

    private void method235(int i, int j, int k) {
        anIntArrayArray418[i][j] |= k;
    }

    public void method236(int i, int j, int k, int l, boolean flag, byte byte0) {
        try {
            l -= anInt414;
            if (byte0 != 3) {
                for (int i1 = 1; i1 > 0; i1++) {
                }
            }
            k -= anInt415;
            if (j == 0) {
                if (i == 0) {
                    method238(l, k, 0, 128);
                    method238(l - 1, k, 0, 8);
                }
                if (i == 1) {
                    method238(l, k, 0, 2);
                    method238(l, k + 1, 0, 32);
                }
                if (i == 2) {
                    method238(l, k, 0, 8);
                    method238(l + 1, k, 0, 128);
                }
                if (i == 3) {
                    method238(l, k, 0, 32);
                    method238(l, k - 1, 0, 2);
                }
            }
            if (j == 1 || j == 3) {
                if (i == 0) {
                    method238(l, k, 0, 1);
                    method238(l - 1, k + 1, 0, 16);
                }
                if (i == 1) {
                    method238(l, k, 0, 4);
                    method238(l + 1, k + 1, 0, 64);
                }
                if (i == 2) {
                    method238(l, k, 0, 16);
                    method238(l + 1, k - 1, 0, 1);
                }
                if (i == 3) {
                    method238(l, k, 0, 64);
                    method238(l - 1, k - 1, 0, 4);
                }
            }
            if (j == 2) {
                if (i == 0) {
                    method238(l, k, 0, 130);
                    method238(l - 1, k, 0, 8);
                    method238(l, k + 1, 0, 32);
                }
                if (i == 1) {
                    method238(l, k, 0, 10);
                    method238(l, k + 1, 0, 32);
                    method238(l + 1, k, 0, 128);
                }
                if (i == 2) {
                    method238(l, k, 0, 40);
                    method238(l + 1, k, 0, 128);
                    method238(l, k - 1, 0, 2);
                }
                if (i == 3) {
                    method238(l, k, 0, 160);
                    method238(l, k - 1, 0, 2);
                    method238(l - 1, k, 0, 8);
                }
            }
            if (flag) {
                if (j == 0) {
                    if (i == 0) {
                        method238(l, k, 0, 0x10000);
                        method238(l - 1, k, 0, 4096);
                    }
                    if (i == 1) {
                        method238(l, k, 0, 1024);
                        method238(l, k + 1, 0, 16384);
                    }
                    if (i == 2) {
                        method238(l, k, 0, 4096);
                        method238(l + 1, k, 0, 0x10000);
                    }
                    if (i == 3) {
                        method238(l, k, 0, 16384);
                        method238(l, k - 1, 0, 1024);
                    }
                }
                if (j == 1 || j == 3) {
                    if (i == 0) {
                        method238(l, k, 0, 512);
                        method238(l - 1, k + 1, 0, 8192);
                    }
                    if (i == 1) {
                        method238(l, k, 0, 2048);
                        method238(l + 1, k + 1, 0, 32768);
                    }
                    if (i == 2) {
                        method238(l, k, 0, 8192);
                        method238(l + 1, k - 1, 0, 512);
                    }
                    if (i == 3) {
                        method238(l, k, 0, 32768);
                        method238(l - 1, k - 1, 0, 2048);
                    }
                }
                if (j == 2) {
                    if (i == 0) {
                        method238(l, k, 0, 0x10400);
                        method238(l - 1, k, 0, 4096);
                        method238(l, k + 1, 0, 16384);
                    }
                    if (i == 1) {
                        method238(l, k, 0, 5120);
                        method238(l, k + 1, 0, 16384);
                        method238(l + 1, k, 0, 0x10000);
                    }
                    if (i == 2) {
                        method238(l, k, 0, 20480);
                        method238(l + 1, k, 0, 0x10000);
                        method238(l, k - 1, 0, 1024);
                    }
                    if (i == 3) {
                        method238(l, k, 0, 0x14000);
                        method238(l, k - 1, 0, 1024);
                        method238(l - 1, k, 0, 4096);
                    }
                }
            }
        } catch (RuntimeException runtimeexception) {
            SignLink.error("35289, " + i + ", " + j + ", " + k + ", " + l + ", " + flag + ", " + byte0 + ", "
                    + runtimeexception.toString());
            throw new RuntimeException();
        }
    }

    public void method237(int i, int j, int k, boolean flag, int l, int i1, int j1) {
        try {
            int k1 = 256;
            if (flag) {
                k1 += 0x20000;
            }
            k -= anInt414;
            j -= anInt415;
            if (l != 8) {
                return;
            }
            if (i1 == 1 || i1 == 3) {
                int l1 = j1;
                j1 = i;
                i = l1;
            }
            for (int i2 = k; i2 < k + j1; i2++) {
                if (i2 >= 0 && i2 < anInt416) {
                    for (int j2 = j; j2 < j + i; j2++) {
                        if (j2 >= 0 && j2 < anInt417) {
                            method238(i2, j2, 0, k1);
                        }
                    }
                }
            }
            return;
        } catch (RuntimeException runtimeexception) {
            SignLink.error("63255, " + i + ", " + j + ", " + k + ", " + flag + ", " + l + ", " + i1 + ", " + j1
                    + ", " + runtimeexception.toString());
        }
        throw new RuntimeException();
    }

    private void method238(int i, int j, int k, int l) {
        try {
            anIntArrayArray418[i][j] &= 0xffffff - l;
            if (k != 0) {
                aBoolean406 = !aBoolean406;
            }
        } catch (RuntimeException runtimeexception) {
            SignLink.error("32560, " + i + ", " + j + ", " + k + ", " + l + ", " + runtimeexception.toString());
            throw new RuntimeException();
        }
    }

    public void method239(int i, boolean flag, int j) {
        try {
            if (!flag) {
                return;
            } else {
                j -= anInt414;
                i -= anInt415;
                anIntArrayArray418[j][i] &= 0xdfffff;
                return;
            }
        } catch (RuntimeException runtimeexception) {
            SignLink.error("56700, " + i + ", " + flag + ", " + j + ", " + runtimeexception.toString());
        }
        throw new RuntimeException();
    }

    public boolean method240(int i, int j, int k, int l, int i1, int j1, int k1) {
        try {
            if (j1 == l && i1 == i) {
                return true;
            }
            j1 -= anInt414;
            i1 -= anInt415;
            l -= anInt414;
            i -= anInt415;
            if (k1 >= 0) {
                aBoolean406 = !aBoolean406;
            }
            if (k == 0) {
                if (j == 0) {
                    if (j1 == l - 1 && i1 == i) {
                        return true;
                    }
                    if (j1 == l && i1 == i + 1 && (anIntArrayArray418[j1][i1] & 0x280120) == 0) {
                        return true;
                    }
                    if (j1 == l && i1 == i - 1 && (anIntArrayArray418[j1][i1] & 0x280102) == 0) {
                        return true;
                    }
                } else if (j == 1) {
                    if (j1 == l && i1 == i + 1) {
                        return true;
                    }
                    if (j1 == l - 1 && i1 == i && (anIntArrayArray418[j1][i1] & 0x280108) == 0) {
                        return true;
                    }
                    if (j1 == l + 1 && i1 == i && (anIntArrayArray418[j1][i1] & 0x280180) == 0) {
                        return true;
                    }
                } else if (j == 2) {
                    if (j1 == l + 1 && i1 == i) {
                        return true;
                    }
                    if (j1 == l && i1 == i + 1 && (anIntArrayArray418[j1][i1] & 0x280120) == 0) {
                        return true;
                    }
                    if (j1 == l && i1 == i - 1 && (anIntArrayArray418[j1][i1] & 0x280102) == 0) {
                        return true;
                    }
                } else if (j == 3) {
                    if (j1 == l && i1 == i - 1) {
                        return true;
                    }
                    if (j1 == l - 1 && i1 == i && (anIntArrayArray418[j1][i1] & 0x280108) == 0) {
                        return true;
                    }
                    if (j1 == l + 1 && i1 == i && (anIntArrayArray418[j1][i1] & 0x280180) == 0) {
                        return true;
                    }
                }
            }
            if (k == 2) {
                if (j == 0) {
                    if (j1 == l - 1 && i1 == i) {
                        return true;
                    }
                    if (j1 == l && i1 == i + 1) {
                        return true;
                    }
                    if (j1 == l + 1 && i1 == i && (anIntArrayArray418[j1][i1] & 0x280180) == 0) {
                        return true;
                    }
                    if (j1 == l && i1 == i - 1 && (anIntArrayArray418[j1][i1] & 0x280102) == 0) {
                        return true;
                    }
                } else if (j == 1) {
                    if (j1 == l - 1 && i1 == i && (anIntArrayArray418[j1][i1] & 0x280108) == 0) {
                        return true;
                    }
                    if (j1 == l && i1 == i + 1) {
                        return true;
                    }
                    if (j1 == l + 1 && i1 == i) {
                        return true;
                    }
                    if (j1 == l && i1 == i - 1 && (anIntArrayArray418[j1][i1] & 0x280102) == 0) {
                        return true;
                    }
                } else if (j == 2) {
                    if (j1 == l - 1 && i1 == i && (anIntArrayArray418[j1][i1] & 0x280108) == 0) {
                        return true;
                    }
                    if (j1 == l && i1 == i + 1 && (anIntArrayArray418[j1][i1] & 0x280120) == 0) {
                        return true;
                    }
                    if (j1 == l + 1 && i1 == i) {
                        return true;
                    }
                    if (j1 == l && i1 == i - 1) {
                        return true;
                    }
                } else if (j == 3) {
                    if (j1 == l - 1 && i1 == i) {
                        return true;
                    }
                    if (j1 == l && i1 == i + 1 && (anIntArrayArray418[j1][i1] & 0x280120) == 0) {
                        return true;
                    }
                    if (j1 == l + 1 && i1 == i && (anIntArrayArray418[j1][i1] & 0x280180) == 0) {
                        return true;
                    }
                    if (j1 == l && i1 == i - 1) {
                        return true;
                    }
                }
            }
            if (k == 9) {
                if (j1 == l && i1 == i + 1 && (anIntArrayArray418[j1][i1] & 0x20) == 0) {
                    return true;
                }
                if (j1 == l && i1 == i - 1 && (anIntArrayArray418[j1][i1] & 2) == 0) {
                    return true;
                }
                if (j1 == l - 1 && i1 == i && (anIntArrayArray418[j1][i1] & 8) == 0) {
                    return true;
                }
                if (j1 == l + 1 && i1 == i && (anIntArrayArray418[j1][i1] & 0x80) == 0) {
                    return true;
                }
            }
            return false;
        } catch (RuntimeException runtimeexception) {
            SignLink.error("58304, " + i + ", " + j + ", " + k + ", " + l + ", " + i1 + ", " + j1 + ", " + k1
                    + ", " + runtimeexception.toString());
        }
        throw new RuntimeException();
    }

    public boolean method241(byte byte0, int i, int j, int k, int l, int i1, int j1) {
        try {
            if (j1 == i && j == k) {
                return true;
            }
            j1 -= anInt414;
            if (byte0 != -58) {
                anInt408 = 54;
            }
            j -= anInt415;
            i -= anInt414;
            k -= anInt415;
            if (l == 6 || l == 7) {
                if (l == 7) {
                    i1 = i1 + 2 & 3;
                }
                if (i1 == 0) {
                    if (j1 == i + 1 && j == k && (anIntArrayArray418[j1][j] & 0x80) == 0) {
                        return true;
                    }
                    if (j1 == i && j == k - 1 && (anIntArrayArray418[j1][j] & 2) == 0) {
                        return true;
                    }
                } else if (i1 == 1) {
                    if (j1 == i - 1 && j == k && (anIntArrayArray418[j1][j] & 8) == 0) {
                        return true;
                    }
                    if (j1 == i && j == k - 1 && (anIntArrayArray418[j1][j] & 2) == 0) {
                        return true;
                    }
                } else if (i1 == 2) {
                    if (j1 == i - 1 && j == k && (anIntArrayArray418[j1][j] & 8) == 0) {
                        return true;
                    }
                    if (j1 == i && j == k + 1 && (anIntArrayArray418[j1][j] & 0x20) == 0) {
                        return true;
                    }
                } else if (i1 == 3) {
                    if (j1 == i + 1 && j == k && (anIntArrayArray418[j1][j] & 0x80) == 0) {
                        return true;
                    }
                    if (j1 == i && j == k + 1 && (anIntArrayArray418[j1][j] & 0x20) == 0) {
                        return true;
                    }
                }
            }
            if (l == 8) {
                if (j1 == i && j == k + 1 && (anIntArrayArray418[j1][j] & 0x20) == 0) {
                    return true;
                }
                if (j1 == i && j == k - 1 && (anIntArrayArray418[j1][j] & 2) == 0) {
                    return true;
                }
                if (j1 == i - 1 && j == k && (anIntArrayArray418[j1][j] & 8) == 0) {
                    return true;
                }
                if (j1 == i + 1 && j == k && (anIntArrayArray418[j1][j] & 0x80) == 0) {
                    return true;
                }
            }
            return false;
        } catch (RuntimeException runtimeexception) {
            SignLink.error("39211, " + byte0 + ", " + i + ", " + j + ", " + k + ", " + l + ", " + i1 + ", " + j1
                    + ", " + runtimeexception.toString());
        }
        throw new RuntimeException();
    }

    public boolean method242(int i, int j, int k, int l, int i1, int j1, int k1, int l1) {
        try {
            int i2 = (l1 + i) - 1;
            int j2 = (j + i1) - 1;
            if (l < anInt412 || l > anInt412) {
                int anInt407 = 337;
            }
            if (j1 >= l1 && j1 <= i2 && k >= j && k <= j2) {
                return true;
            }
            if (j1 == l1 - 1 && k >= j && k <= j2 && (anIntArrayArray418[j1 - anInt414][k - anInt415] & 8) == 0
                    && (k1 & 8) == 0) {
                return true;
            }
            if (j1 == i2 + 1 && k >= j && k <= j2 && (anIntArrayArray418[j1 - anInt414][k - anInt415] & 0x80) == 0
                    && (k1 & 2) == 0) {
                return true;
            }
            if (k == j - 1 && j1 >= l1 && j1 <= i2 && (anIntArrayArray418[j1 - anInt414][k - anInt415] & 2) == 0
                    && (k1 & 4) == 0) {
                return true;
            }
            return k == j2 + 1 && j1 >= l1 && j1 <= i2 && (anIntArrayArray418[j1 - anInt414][k - anInt415] & 0x20) == 0
                    && (k1 & 1) == 0;
        } catch (RuntimeException runtimeexception) {
            SignLink.error("55272, " + i + ", " + j + ", " + k + ", " + l + ", " + i1 + ", " + j1 + ", " + k1
                    + ", " + l1 + ", " + runtimeexception.toString());
        }
        throw new RuntimeException();
    }
}
