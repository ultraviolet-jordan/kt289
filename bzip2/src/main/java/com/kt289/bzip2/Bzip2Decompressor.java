package com.kt289.bzip2;

import java.util.stream.IntStream;

public class Bzip2Decompressor {

    private static final Bzip2Block block = new Bzip2Block();

    public static void decompress(byte[] output, int retVal, byte[] stream, int maxLength, int minLength) {
        synchronized (block) {
            block.stream = stream;
            block.minLength = minLength;
            block.output = output;
            block.nextOut = 0;
            block.maxLength = maxLength;
            block.retVal = retVal;
            block.bsLive = 0;
            block.bsBuff = 0;
            block.totalInLo32 = 0;
            block.totalInHi32 = 0;
            block.totalOutLo32 = 0;
            block.totalOutHi32 = 0;
            block.currBlockNumber = 0;
            decompress();
        }
    }

    private static void method330() {
        byte stateOutCh = block.stateOutCh;
        int stateOutLen = block.stateOutLen;
        int nBlockUsed = block.nBlockUsed;
        int k0 = block.k0;
        int[] tt = Bzip2Block.tt;
        int tPos = block.tPos;
        byte[] buf = block.output;
        int nextOut = block.nextOut;
        int availOut = block.retVal;
        int availOutInit = availOut;
        int savedNBlockPP = block.nBlock + 1;
        breakpoint:
        do {
            if (stateOutLen > 0) {
                do {
                    if (availOut == 0) {
                        break breakpoint;
                    }
                    if (stateOutLen == 1) {
                        break;
                    }
                    buf[nextOut] = stateOutCh;
                    stateOutLen--;
                    nextOut++;
                    availOut--;
                } while (true);
                buf[nextOut] = stateOutCh;
                nextOut++;
                availOut--;
            }
            boolean flag = true;
            while (flag) {
                flag = false;
                if (nBlockUsed == savedNBlockPP) {
                    stateOutLen = 0;
                    break breakpoint;
                }
                stateOutCh = (byte) k0;
                tPos = tt[tPos];
                byte byte0 = (byte) (tPos & 0xff);
                tPos >>= 8;
                nBlockUsed++;
                if (byte0 != k0) {
                    k0 = byte0;
                    if (availOut == 0) {
                        stateOutLen = 1;
                    } else {
                        buf[nextOut] = stateOutCh;
                        nextOut++;
                        availOut--;
                        flag = true;
                        continue;
                    }
                    break breakpoint;
                }
                if (nBlockUsed != savedNBlockPP) {
                    continue;
                }
                if (availOut == 0) {
                    stateOutLen = 1;
                    break breakpoint;
                }
                buf[nextOut] = stateOutCh;
                nextOut++;
                availOut--;
                flag = true;
            }
            stateOutLen = 2;
            tPos = tt[tPos];
            byte byte1 = (byte) (tPos & 0xff);
            tPos >>= 8;
            if (++nBlockUsed != savedNBlockPP) {
                if (byte1 != k0) {
                    k0 = byte1;
                } else {
                    stateOutLen = 3;
                    tPos = tt[tPos];
                    byte byte2 = (byte) (tPos & 0xff);
                    tPos >>= 8;
                    if (++nBlockUsed != savedNBlockPP) {
                        if (byte2 != k0) {
                            k0 = byte2;
                        } else {
                            tPos = tt[tPos];
                            byte byte3 = (byte) (tPos & 0xff);
                            tPos >>= 8;
                            nBlockUsed++;
                            stateOutLen = (byte3 & 0xff) + 4;
                            tPos = tt[tPos];
                            k0 = (byte) (tPos & 0xff);
                            tPos >>= 8;
                            nBlockUsed++;
                        }
                    }
                }
            }
        } while (true);
        int totalOutLo32 = block.totalOutLo32;
        block.totalOutLo32 += availOutInit - availOut;
        if (block.totalOutLo32 < totalOutLo32) {
            block.totalOutHi32++;
        }
        block.stateOutCh = stateOutCh;
        block.stateOutLen = stateOutLen;
        block.nBlockUsed = nBlockUsed;
        block.k0 = k0;
        Bzip2Block.tt = tt;
        block.tPos = tPos;
        block.output = buf;
        block.nextOut = nextOut;
        block.retVal = availOut;
    }

    private static void decompress() {
        int gMinLength;
        int[] gLimit;
        int[] gBase;
        int[] gPerm;
        block.blockSize100k = 1;
        if (Bzip2Block.tt == null) {
            Bzip2Block.tt = new int[block.blockSize100k * 0x186a0];
        }
        boolean flag19 = true;
        while (flag19) {
            byte uc = getUChar();
            if (uc == 23) {
                return;
            }
            getUChar();
            getUChar();
            getUChar();
            getUChar();
            getUChar();
            block.currBlockNumber++;
            getUChar();
            getUChar();
            getUChar();
            getUChar();
            uc = getBit();
            block.randomised = uc != 0;
            if (block.randomised) {
                System.out.println("PANIC! RANDOMISED BLOCK!");
            }
            block.origPtr = 0;
            uc = getUChar();
            block.origPtr = block.origPtr << 8 | uc & 0xff;
            uc = getUChar();
            block.origPtr = block.origPtr << 8 | uc & 0xff;
            uc = getUChar();
            block.origPtr = block.origPtr << 8 | uc & 0xff;
            IntStream.range(0, 16).forEach(index -> {
                byte bit = getBit();
                block.inUse16[index] = bit == 1;
            });
            IntStream.range(0, 256).forEach(index -> block.inUse[index] = false);
            for (int index = 0; index < 16; index++) {
                if (block.inUse16[index]) {
                    for (int sub = 0; sub < 16; sub++) {
                        byte bit = getBit();
                        if (bit == 1) {
                            block.inUse[index * 16 + sub] = true;
                        }
                    }
                }
            }
            makeMaps();
            int alphaSize = block.nInuse + 2;
            int nGroups = getBits(3);
            int nSelectors = getBits(15);
            for (int index = 0; index < nSelectors; index++) {
                int count = 0;
                do {
                    byte terminator = getBit();
                    if (terminator == 0) {
                        break;
                    }
                    count++;
                } while (true);
                block.selectorMtf[index] = (byte) count;
            }

            byte[] pos = new byte[6];
            for (byte index = 0; index < nGroups; index++) {
                pos[index] = index;
            }
            for (int index = 0; index < nSelectors; index++) {
                byte v = block.selectorMtf[index];
                byte tmp = pos[v];
                while (v > 0) {
                    pos[v] = pos[v - 1];
                    v--;
                }
                pos[0] = tmp;
                block.selector[index] = tmp;
            }
            for (int index = 0; index < nGroups; index++) {
                int curr = getBits(5);
                for (int sub = 0; sub < alphaSize; sub++) {
                    do {
                        byte bit = getBit();
                        if (bit == 0) {
                            break;
                        }
                        bit = getBit();
                        if (bit == 0) {
                            curr++;
                        } else {
                            curr--;
                        }
                    } while (true);
                    block.len[index][sub] = (byte) curr;
                }

            }
            for (int index = 0; index < nGroups; index++) {
                byte minLength = 32;
                int maxLength = 0;
                for (int sub = 0; sub < alphaSize; sub++) {
                    if (block.len[index][sub] > maxLength) {
                        maxLength = block.len[index][sub];
                    }
                    if (block.len[index][sub] < minLength) {
                        minLength = block.len[index][sub];
                    }
                }
                createDecodeTables(block.limit[index], block.base[index], block.perm[index], block.len[index], minLength, maxLength, alphaSize);
                block.floorLengths[index] = minLength;
            }
            int l4 = block.nInuse + 1;
            int groupNo = -1;
            int groupPos;
            IntStream.rangeClosed(0, 255).forEach(index -> block.unzftab[index] = 0);
            int kk = 4095;
            for (int index = 15; index >= 0; index--) {
                for (int sub = 15; sub >= 0; sub--) {
                    block.mtfa[kk] = (byte) (index * 16 + sub);
                    kk--;
                }
                block.mtfbase[index] = kk + 1;
            }
            int nblock = 0;
            groupNo++;
            groupPos = 50;
            byte gSel = block.selector[groupNo];
            gMinLength = block.floorLengths[gSel];
            gLimit = block.limit[gSel];
            gPerm = block.perm[gSel];
            gBase = block.base[gSel];
            groupPos--;
            int zn = gMinLength;
            int zvec;
            byte bit;
            for (zvec = getBits(zn); zvec > gLimit[zn]; zvec = zvec << 1 | bit) {
                zn++;
                bit = getBit();
            }
            for (int nextSym = gPerm[zvec - gBase[zn]]; nextSym != l4; ) {
                if (nextSym == 0 || nextSym == 1) {
                    int es = -1;
                    int n = 1;
                    do {
                        if (nextSym == 0) {
                            es += n;
                        } else {
                            es += 2 * n;
                        }
                        n *= 2;
                        if (groupPos == 0) {
                            groupNo++;
                            groupPos = 50;
                            byte gSelSub = block.selector[groupNo];
                            gMinLength = block.floorLengths[gSelSub];
                            gLimit = block.limit[gSelSub];
                            gPerm = block.perm[gSelSub];
                            gBase = block.base[gSelSub];
                        }
                        groupPos--;
                        int j7 = gMinLength;
                        int index;
                        byte byte10;
                        for (index = getBits(j7); index > gLimit[j7]; index = index << 1 | byte10) {
                            j7++;
                            byte10 = getBit();
                        }
                        nextSym = gPerm[index - gBase[j7]];
                    } while (nextSym == 0 || nextSym == 1);
                    es++;
                    byte byte5 = block.seqToUnseq[block.mtfa[block.mtfbase[0]] & 0xff];
                    block.unzftab[byte5 & 0xff] += es;
                    while (es > 0) {
                        Bzip2Block.tt[nblock] = byte5 & 0xff;
                        nblock++;
                        es--;
                    }
                } else {
                    int nn = nextSym - 1;
                    byte sel;
                    if (nn < 16) {
                        int j10 = block.mtfbase[0];
                        sel = block.mtfa[j10 + nn];
                        while (nn > 3) {
                            int k11 = j10 + nn;
                            block.mtfa[k11] = block.mtfa[k11 - 1];
                            block.mtfa[k11 - 1] = block.mtfa[k11 - 2];
                            block.mtfa[k11 - 2] = block.mtfa[k11 - 3];
                            block.mtfa[k11 - 3] = block.mtfa[k11 - 4];
                            nn -= 4;
                        }
                        while (nn > 0) {
                            block.mtfa[j10 + nn] = block.mtfa[(j10 + nn) - 1];
                            nn--;
                        }
                        block.mtfa[j10] = sel;
                    } else {
                        int lno = nn / 16;
                        int off = nn % 16;
                        int pp = block.mtfbase[lno] + off;
                        sel = block.mtfa[pp];
                        while (pp > block.mtfbase[lno]) {
                            block.mtfa[pp] = block.mtfa[pp - 1];
                            pp--;
                        }
                        block.mtfbase[lno]++;
                        while (lno > 0) {
                            block.mtfbase[lno]--;
                            block.mtfa[block.mtfbase[lno]] = block.mtfa[(block.mtfbase[lno - 1] + 16) - 1];
                            lno--;
                        }
                        block.mtfbase[0]--;
                        block.mtfa[block.mtfbase[0]] = sel;
                        if (block.mtfbase[0] == 0) {
                            int kkSub = 4095;
                            for (int index = 15; index >= 0; index--) {
                                for (int sub = 15; sub >= 0; sub--) {
                                    block.mtfa[kkSub] = block.mtfa[block.mtfbase[index] + sub];
                                    kkSub--;
                                }
                                block.mtfbase[index] = kkSub + 1;
                            }
                        }
                    }
                    block.unzftab[block.seqToUnseq[sel & 0xff] & 0xff]++;
                    Bzip2Block.tt[nblock] = block.seqToUnseq[sel & 0xff] & 0xff;
                    nblock++;
                    if (groupPos == 0) {
                        groupNo++;
                        groupPos = 50;
                        byte byte14 = block.selector[groupNo];
                        gMinLength = block.floorLengths[byte14];
                        gLimit = block.limit[byte14];
                        gPerm = block.perm[byte14];
                        gBase = block.base[byte14];
                    }
                    groupPos--;
                    int min = gMinLength;
                    int index;
                    byte bitSub;
                    for (index = getBits(min); index > gLimit[min]; index = index << 1 | bitSub) {
                        min++;
                        bitSub = getBit();
                    }
                    nextSym = gPerm[index - gBase[min]];
                }
            }

            block.stateOutLen = 0;
            block.stateOutCh = 0;
            block.cftab[0] = 0;
            System.arraycopy(block.unzftab, 0, block.cftab, 1, 256);
            IntStream.rangeClosed(1, 256).forEach(index -> block.cftab[index] += block.cftab[index - 1]);
            IntStream.range(0, nblock).forEach(index -> {
                byte byte7 = (byte) (Bzip2Block.tt[index] & 0xff);
                Bzip2Block.tt[block.cftab[byte7 & 0xff]] |= index << 8;
                block.cftab[byte7 & 0xff]++;
            });
            block.tPos = Bzip2Block.tt[block.origPtr] >> 8;
            block.nBlockUsed = 0;
            block.tPos = Bzip2Block.tt[block.tPos];
            block.k0 = (byte) (block.tPos & 0xff);
            block.tPos >>= 8;
            block.nBlockUsed++;
            block.nBlock = nblock;
            method330();
            flag19 = block.nBlockUsed == block.nBlock + 1 && block.stateOutLen == 0;
        }
    }

    private static byte getUChar() {
        return (byte) getBits(8);
    }

    private static byte getBit() {
        return (byte) getBits(1);
    }

    private static int getBits(int i) {
        int bits;
        do {
            if (block.bsLive >= i) {
                int k = block.bsBuff >> block.bsLive - i & (1 << i) - 1;
                block.bsLive -= i;
                bits = k;
                break;
            }
            block.bsBuff = block.bsBuff << 8 | block.stream[block.minLength] & 0xff;
            block.bsLive += 8;
            block.minLength++;
            block.maxLength--;
            block.totalInLo32++;
            if (block.totalInLo32 == 0) {
                block.totalInHi32++;
            }
        } while (true);
        return bits;
    }

    private static void makeMaps() {
        block.nInuse = 0;
        IntStream.range(0, 256).filter(index -> block.inUse[index])
                .forEach(index -> {
                    block.seqToUnseq[block.nInuse] = (byte) index;
                    block.nInuse++;
                });
    }

    private static void createDecodeTables(int[] limit, int[] base, int[] perm, byte[] len, int minLength, int maxLength, int alphaSize) {
        int pp = 0;
        for (int index = minLength; index <= maxLength; index++) {
            for (int sub = 0; sub < alphaSize; sub++) {
                if (len[sub] == index) {
                    perm[pp] = sub;
                    pp++;
                }
            }
        }
        IntStream.range(0, 23).forEach(index -> base[index] = 0);
        IntStream.range(0, alphaSize).forEach(index -> base[len[index] + 1]++);
        IntStream.range(1, 23).forEach(index -> base[index] += base[index - 1]);
        IntStream.range(0, 23).forEach(index -> limit[index] = 0);
        int vec = 0;
        for (int index = minLength; index <= maxLength; index++) {
            vec += base[index + 1] - base[index];
            limit[index] = vec - 1;
            vec <<= 1;
        }
        IntStream.rangeClosed(minLength + 1, maxLength).forEach(index -> base[index] = (limit[index - 1] + 1 << 1) - base[index]);
    }

}