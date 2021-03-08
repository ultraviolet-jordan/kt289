package com.kt289.bzip2;

class Bzip2Block {

    static int[] tt;

    final boolean[] inUse;
    final boolean[] inUse16;
    final byte[] seqToUnseq;
    final byte[] mtfa;
    final int[] mtfbase;
    final byte[] selector;
    final byte[] selectorMtf;
    final byte[][] len;
    final int[][] limit;
    final int[][] base;
    final int[][] perm;
    final int[] floorLengths;
    final int[] unzftab;
    final int[] cftab;

    byte[] stream;
    int minLength;
    int maxLength;
    int totalInLo32;
    int totalInHi32;
    byte[] output;
    int nextOut;
    int retVal;
    int totalOutLo32;
    int totalOutHi32;
    byte stateOutCh;
    int stateOutLen;
    boolean randomised;
    int bsBuff;
    int bsLive;
    int blockSize100k;
    int currBlockNumber;
    int origPtr;
    int tPos;
    int k0;
    int nBlockUsed;
    int nInuse;
    int nBlock;

    Bzip2Block() {
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
        floorLengths = new int[6];
    }
}
