package com.kt289.bzip2;

class Bzip2DecompressionState {

    public static int[] anIntArray730;
    public byte[] aByteArray706;
    public int anInt707;
    public int anInt708;
    public int anInt709;
    public int anInt710;
    public byte[] aByteArray711;
    public int anInt712;
    public int anInt713;
    public int anInt714;
    public int anInt715;
    public byte aByte716;
    public int anInt717;
    public boolean aBoolean718;
    public int anInt719;
    public int anInt720;
    public int anInt721;
    public int anInt722;
    public int anInt723;
    public int anInt724;
    public int anInt725;
    public final int[] unzftab;
    public int anInt727;
    public final int[] cftab;
    public int anInt731;
    public final boolean[] inUse;
    public final boolean[] inUse16;
    public final byte[] seqToUnseq;
    public final byte[] mtfa;
    public final int[] mtfbase;
    public final byte[] selector;
    public final byte[] selectorMtf;
    public final byte[][] len;
    public final int[][] limit;
    public final int[][] base;
    public final int[][] perm;
    public final int[] minLens;
    public int anInt744;

    public Bzip2DecompressionState() {
        unzftab = new int[256];
        cftab = new int[257];
        inUse = new boolean[256];
        inUse16 = new boolean[16];
        seqToUnseq = new byte[256];
        mtfa = new byte[4096];
        mtfbase = new int[16];
        selector = new byte[18002];
        selectorMtf = new byte[18002];
        len = new byte[6][258];
        limit = new int[6][258];
        base = new int[6][258];
        perm = new int[6][258];
        minLens = new int[6];
    }
}
