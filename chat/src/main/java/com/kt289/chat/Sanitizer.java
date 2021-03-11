package com.kt289.chat;

import com.kt289.cache.Archive;
import com.kt289.util.buffer.Buffer;

import java.util.stream.IntStream;

public class Sanitizer {

    private static final String[] allowed = {
            "cook",
            "cook's",
            "cooks",
            "seeks",
            "sheet",
            "woop",
            "woops",
            "faq",
            "noob",
            "noobs"
    };

    private static int[] fragments;
    private static char[][] bads;
    private static char[][] domains;
    private static char[][] tlds;

    private static byte[][][] badEncCombinations;
    private static int[] tldTypes;

    public static void load(Archive archive) {
        Buffer fragmentsenc = new Buffer(archive.decompressFile("fragmentsenc.txt"));
        Buffer badenc = new Buffer(archive.decompressFile("badenc.txt"));
        Buffer domainenc = new Buffer(archive.decompressFile("domainenc.txt"));
        Buffer tldlist = new Buffer(archive.decompressFile("tldlist.txt"));

        loadBadEnc(badenc);
        loadDomainEnc(domainenc);
        loadFragmentSenc(fragmentsenc);
        loadTldList(tldlist);
    }

    private static void loadTldList(Buffer tldlist) {
        int length = tldlist.readInt();
        tlds = new char[length][];
        tldTypes = new int[length];
        IntStream.range(0, length).forEach(index -> {
            tldTypes[index] = tldlist.readUnsignedByte();
            char[] string = new char[tldlist.readUnsignedByte()];
            IntStream.range(0, string.length).forEach(character -> string[character] = (char) tldlist.readUnsignedByte());
            tlds[index] = string;
        });
    }

    private static void loadBadEnc(Buffer badenc) {
        int length = badenc.readInt();
        bads = new char[length][];
        badEncCombinations = new byte[length][][];

        IntStream.range(0, bads.length).forEach(index -> {
            char[] string = new char[badenc.readUnsignedByte()];
            IntStream.range(0, string.length).forEach(character -> string[character] = (char) badenc.readUnsignedByte());
            bads[index] = string;
            byte[][] combination = new byte[badenc.readUnsignedByte()][2];
            IntStream.range(0, combination.length).forEach(i -> {
                combination[i][0] = (byte) badenc.readUnsignedByte();
                combination[i][1] = (byte) badenc.readUnsignedByte();
            });
            if (combination.length > 0) {
                badEncCombinations[index] = combination;
            }
        });
    }

    private static void loadDomainEnc(Buffer domainenc) {
        int length = domainenc.readInt();
        domains = new char[length][];

        IntStream.range(0, domains.length).forEach(index -> {
            char[] string = new char[domainenc.readUnsignedByte()];
            IntStream.range(0, string.length).forEach(character -> string[character] = (char) domainenc.readUnsignedByte());
            domains[index] = string;
        });
    }

    private static void loadFragmentSenc(Buffer fragmentsenc) {
        fragments = new int[fragmentsenc.readInt()];
        IntStream.range(0, fragments.length).forEach(index -> fragments[index] = fragmentsenc.readUnsignedShort());
    }

    private static void trim(char[] chars) {
        int position = 0;
        for (int index = 0; index < chars.length; index++) {
            if (method351(chars[index])) {
                chars[position] = chars[index];
            } else {
                chars[position] = ' ';
            }
            if (position == 0 || chars[position] != ' ' || chars[position - 1] != ' ') {
                position++;
            }
        }
        for (int index = position; index < chars.length; index++) {
            chars[index] = ' ';
        }
    }

    private static boolean method351(char c) {
        return c >= ' ' && c <= '\177' || c == '\n' || c == '\t' || c == '\243' || c == '\u20AC';
    }

    public static String cleanse(String string) {
        char[] chars = string.toCharArray();
        trim(chars);
        String trimmedString = (new String(chars)).trim();
        chars = trimmedString.toLowerCase().toCharArray();
        String trimmedLowercase = trimmedString.toLowerCase();
        checkForTlds(chars);
        checkForBads(chars);
        checkForDomains(chars);
        method369(chars);
        for (String whitelisted : allowed) {
            for (int k = -1; (k = trimmedLowercase.indexOf(whitelisted, k + 1)) != -1; ) {
                char[] ac1 = whitelisted.toCharArray();
                System.arraycopy(ac1, 0, chars, k, ac1.length);
            }
        }
        replace(trimmedString.toCharArray(), chars);
        format(chars);
        return (new String(chars)).trim();
    }

    private static void replace(char[] from, char[] to) {
        IntStream.range(0, from.length)
                .filter(index -> to[index] != '*' && isUppercase(from[index]))
                .forEach(index -> to[index] = from[index]);
    }

    private static void format(char[] chars) {
        boolean notLowercase = true;
        for (int index = 0; index < chars.length; index++) {
            char character = chars[index];
            if (isLetter(character)) {
                if (notLowercase) {
                    if (isLowercase(character)) {
                        notLowercase = false;
                    }
                } else if (isUppercase(character)) {
                    chars[index] = (char) ((character + 97) - 65);
                }
            } else {
                notLowercase = true;
            }
        }
    }

    private static void checkForBads(char[] chars) {
        for (int i = 0; i < 2; i++) {
            for (int index = bads.length - 1; index >= 0; index--) {
                method364(chars, bads[index], badEncCombinations[index]);
            }
        }
    }

    private static void checkForDomains(char[] chars) {
        char[] ac1 = chars.clone();
        char[] at = {'(', 'a', ')'};
        method364(ac1, at, null);
        char[] ac3 = chars.clone();
        char[] dot = {'d', 'o', 't'};
        method364(ac3, dot, null);
        for (int index = domains.length - 1; index >= 0; index--) {
            method357(ac1, chars, ac3, domains[index]);
        }
    }

    private static void method357(char[] ac, char[] ac1, char[] ac2, char[] ac3) {
        if (ac3.length > ac1.length) {
            return;
        }
        int i;
        for (int j = 0; j <= ac1.length - ac3.length; j += i) {
            int k = j;
            int l = 0;
            i = 1;
            while (k < ac1.length) {
                int i1;
                char c = ac1[k];
                char c1 = '\0';
                if (k + 1 < ac1.length) {
                    c1 = ac1[k + 1];
                }
                if (l < ac3.length && (i1 = method366(c1, c, ac3[l])) > 0) {
                    k += i1;
                    l++;
                    continue;
                }
                if (l == 0) {
                    break;
                }
                if ((i1 = method366(c1, c, ac3[l - 1])) > 0) {
                    k += i1;
                    if (l == 1) {
                        i++;
                    }
                    continue;
                }
                if (l >= ac3.length || !isSymbol(c)) {
                    break;
                }
                k++;
            }
            if (l >= ac3.length) {
                boolean flag1 = false;
                int j1 = method358(ac, j, ac1);
                int k1 = method359(ac1, ac2, k - 1);
                if (j1 > 2 || k1 > 2) {
                    flag1 = true;
                }
                if (flag1) {
                    for (int l1 = j; l1 < k; l1++) {
                        ac1[l1] = '*';
                    }
                }
            }
        }
    }

    private static int method358(char[] ac, int j, char[] ac1) {
        if (j == 0) {
            return 2;
        }
        for (int k = j - 1; k >= 0; k--) {
            if (!isSymbol(ac1[k])) {
                break;
            }
            if (ac1[k] == '@') {
                return 3;
            }
        }
        int l = 0;
        for (int i1 = j - 1; i1 >= 0; i1--) {
            if (!isSymbol(ac[i1])) {
                break;
            }
            if (ac[i1] == '*') {
                l++;
            }
        }
        if (l >= 3) {
            return 4;
        }
        return !isSymbol(ac1[j - 1]) ? 0 : 1;
    }

    private static int method359(char[] ac, char[] ac1, int j) {
        if (j + 1 == ac.length) {
            return 2;
        }
        for (int k = j + 1; k < ac.length; k++) {
            if (!isSymbol(ac[k])) {
                break;
            }
            if (ac[k] == '.' || ac[k] == ',') {
                return 3;
            }
        }
        int l = 0;
        for (int i1 = j + 1; i1 < ac.length; i1++) {
            if (!isSymbol(ac1[i1])) {
                break;
            }
            if (ac1[i1] == '*') {
                l++;
            }
        }
        if (l >= 3) {
            return 4;
        }
        return !isSymbol(ac[j + 1]) ? 0 : 1;
    }

    private static void checkForTlds(char[] chars) {
        char[] ac1 = chars.clone();
        char[] dot = {'d', 'o', 't'};
        method364(ac1, dot, null);
        char[] ac3 = chars.clone();
        char[] slash = {'s', 'l', 'a', 's', 'h'};
        method364(ac3, slash, null);
        IntStream.range(0, tlds.length).forEach(index -> method361(tldTypes[index], chars, tlds[index], ac3, ac1));
    }

    private static void method361(int i, char[] ac, char[] ac1, char[] ac2, char[] ac3) {
        if (ac1.length > ac.length) {
            return;
        }
        int k;
        for (int l = 0; l <= ac.length - ac1.length; l += k) {
            int i1 = l;
            int j1 = 0;
            k = 1;
            while (i1 < ac.length) {
                int k1;
                char c = ac[i1];
                char c1 = '\0';
                if (i1 + 1 < ac.length) {
                    c1 = ac[i1 + 1];
                }
                if (j1 < ac1.length && (k1 = method366(c1, c, ac1[j1])) > 0) {
                    i1 += k1;
                    j1++;
                    continue;
                }
                if (j1 == 0) {
                    break;
                }
                if ((k1 = method366(c1, c, ac1[j1 - 1])) > 0) {
                    i1 += k1;
                    if (j1 == 1) {
                        k++;
                    }
                    continue;
                }
                if (j1 >= ac1.length || !isSymbol(c)) {
                    break;
                }
                i1++;
            }
            if (j1 >= ac1.length) {
                boolean flag1 = false;
                int l1 = method362(l, ac, ac3);
                int i2 = method363(ac2, ac, i1 - 1);
                if (i == 1 && l1 > 0 && i2 > 0) {
                    flag1 = true;
                }
                if (i == 2 && (l1 > 2 && i2 > 0 || l1 > 0 && i2 > 2)) {
                    flag1 = true;
                }
                if (i == 3 && l1 > 0 && i2 > 2) {
                    flag1 = true;
                }
                if (flag1) {
                    int j2 = l;
                    int k2 = i1 - 1;
                    if (l1 > 2) {
                        if (l1 == 4) {
                            boolean flag2 = false;
                            for (int i3 = j2 - 1; i3 >= 0; i3--) {
                                if (flag2) {
                                    if (ac3[i3] != '*') {
                                        break;
                                    }
                                    j2 = i3;
                                } else if (ac3[i3] == '*') {
                                    j2 = i3;
                                    flag2 = true;
                                }
                            }
                        }
                        boolean flag3 = false;
                        for (int j3 = j2 - 1; j3 >= 0; j3--) {
                            if (flag3) {
                                if (isSymbol(ac[j3])) {
                                    break;
                                }
                                j2 = j3;
                            } else if (!isSymbol(ac[j3])) {
                                flag3 = true;
                                j2 = j3;
                            }
                        }
                    }
                    if (i2 > 2) {
                        if (i2 == 4) {
                            boolean flag4 = false;
                            for (int k3 = k2 + 1; k3 < ac.length; k3++) {
                                if (flag4) {
                                    if (ac2[k3] != '*') {
                                        break;
                                    }
                                    k2 = k3;
                                } else if (ac2[k3] == '*') {
                                    k2 = k3;
                                    flag4 = true;
                                }
                            }
                        }
                        boolean flag5 = false;
                        for (int l3 = k2 + 1; l3 < ac.length; l3++) {
                            if (flag5) {
                                if (isSymbol(ac[l3])) {
                                    break;
                                }
                                k2 = l3;
                            } else if (!isSymbol(ac[l3])) {
                                flag5 = true;
                                k2 = l3;
                            }
                        }
                    }
                    for (int l2 = j2; l2 <= k2; l2++) {
                        ac[l2] = '*';
                    }
                }
            }
        }
    }

    private static int method362(int i, char[] ac, char[] ac1) {
        if (i == 0) {
            return 2;
        }
        for (int j = i - 1; j >= 0; j--) {
            if (!isSymbol(ac[j])) {
                break;
            }
            if (ac[j] == ',' || ac[j] == '.') {
                return 3;
            }
        }
        int k = 0;
        for (int l = i - 1; l >= 0; l--) {
            if (!isSymbol(ac1[l])) {
                break;
            }
            if (ac1[l] == '*') {
                k++;
            }
        }
        if (k >= 3) {
            return 4;
        }
        return !isSymbol(ac[i - 1]) ? 0 : 1;
    }

    private static int method363(char[] ac, char[] ac1, int i) {
        if (i + 1 == ac1.length) {
            return 2;
        }
        for (int j = i + 1; j < ac1.length; j++) {
            if (!isSymbol(ac1[j])) {
                break;
            }
            if (ac1[j] == '\\' || ac1[j] == '/') {
                return 3;
            }
        }
        int k = 0;
        for (int l = i + 1; l < ac1.length; l++) {
            if (!isSymbol(ac[l])) {
                break;
            }
            if (ac[l] == '*') {
                k++;
            }
        }
        if (k >= 5) {
            return 4;
        }
        return !isSymbol(ac1[i + 1]) ? 0 : 1;
    }

    private static void method364(char[] ac, char[] ac1, byte[][] abyte0) {
        if (ac1.length > ac.length) {
            return;
        }
        int j;
        for (int k = 0; k <= ac.length - ac1.length; k += j) {
            int l = k;
            int i1 = 0;
            int j1 = 0;
            j = 1;
            boolean flag1 = false;
            boolean flag2 = false;
            boolean flag3 = false;
            while (l < ac.length && (!flag2 || !flag3)) {
                int k1;
                char c = ac[l];
                char c2 = '\0';
                if (l + 1 < ac.length) {
                    c2 = ac[l + 1];
                }
                if (i1 < ac1.length && (k1 = method367(c, c2, ac1[i1])) > 0) {
                    if (k1 == 1 && isNumerical(c)) {
                        flag2 = true;
                    }
                    if (k1 == 2 && (isNumerical(c) || isNumerical(c2))) {
                        flag2 = true;
                    }
                    l += k1;
                    i1++;
                    continue;
                }
                if (i1 == 0) {
                    break;
                }
                if ((k1 = method367(c, c2, ac1[i1 - 1])) > 0) {
                    l += k1;
                    if (i1 == 1) {
                        j++;
                    }
                    continue;
                }
                if (i1 >= ac1.length || method373(c)) {
                    break;
                }
                if (isSymbol(c) && c != '\'') {
                    flag1 = true;
                }
                if (isNumerical(c)) {
                    flag3 = true;
                }
                l++;
                if ((++j1 * 100) / (l - k) > 90) {
                    break;
                }
            }
            if (i1 >= ac1.length && (!flag2 || !flag3)) {
                boolean flag4 = true;
                if (!flag1) {
                    char c1 = ' ';
                    if (k - 1 >= 0) {
                        c1 = ac[k - 1];
                    }
                    char c3 = ' ';
                    if (l < ac.length) {
                        c3 = ac[l];
                    }
                    byte byte0 = method368(c1);
                    byte byte1 = method368(c3);
                    if (abyte0 != null && method365(byte1, byte0, abyte0)) {
                        flag4 = false;
                    }
                } else {
                    boolean flag5 = false;
                    boolean flag6 = false;
                    if (k - 1 < 0 || isSymbol(ac[k - 1]) && ac[k - 1] != '\'') {
                        flag5 = true;
                    }
                    if (l >= ac.length || isSymbol(ac[l]) && ac[l] != '\'') {
                        flag6 = true;
                    }
                    if (!flag5 || !flag6) {
                        boolean flag7 = false;
                        int k2 = k - 2;
                        if (flag5) {
                            k2 = k;
                        }
                        for (; !flag7 && k2 < l; k2++) {
                            if (k2 >= 0 && (!isSymbol(ac[k2]) || ac[k2] == '\'')) {
                                char[] ac2 = new char[3];
                                int j3;
                                for (j3 = 0; j3 < 3; j3++) {
                                    if (k2 + j3 >= ac.length || isSymbol(ac[k2 + j3])
                                            && ac[k2 + j3] != '\'') {
                                        break;
                                    }
                                    ac2[j3] = ac[k2 + j3];
                                }
                                boolean flag8 = true;
                                if (j3 == 0) {
                                    flag8 = false;
                                }
                                if (j3 < 3 && k2 - 1 >= 0
                                        && (!isSymbol(ac[k2 - 1]) || ac[k2 - 1] == '\'')) {
                                    flag8 = false;
                                }
                                if (flag8 && !method378(ac2)) {
                                    flag7 = true;
                                }
                            }
                        }
                        if (!flag7) {
                            flag4 = false;
                        }
                    }
                }
                if (flag4) {
                    int l1 = 0;
                    int i2 = 0;
                    int j2 = -1;
                    for (int l2 = k; l2 < l; l2++) {
                        if (isNumerical(ac[l2])) {
                            l1++;
                        } else if (isLetter(ac[l2])) {
                            i2++;
                            j2 = l2;
                        }
                    }
                    if (j2 > -1) {
                        l1 -= l - 1 - j2;
                    }
                    if (l1 <= i2) {
                        for (int i3 = k; i3 < l; i3++) {
                            ac[i3] = '*';
                        }
                    } else {
                        j = 1;
                    }
                }
            }
        }
    }

    private static boolean method365(byte byte0, byte byte2, byte[][] abyte0) {
        int i = 0;
        if (abyte0[i][0] == byte2 && abyte0[i][1] == byte0) {
            return true;
        }
        int j = abyte0.length - 1;
        if (abyte0[j][0] == byte2 && abyte0[j][1] == byte0) {
            return true;
        }
        do {
            int k = (i + j) / 2;
            if (abyte0[k][0] == byte2 && abyte0[k][1] == byte0) {
                return true;
            }
            if (byte2 < abyte0[k][0] || byte2 == abyte0[k][0] && byte0 < abyte0[k][1]) {
                j = k;
            } else {
                i = k;
            }
        } while (i != j && i + 1 != j);
        return false;
    }

    private static int method366(char c, char c1, char c2) {
        if (c2 == c1) {
            return 1;
        }
        if (c2 == 'o' && c1 == '0') {
            return 1;
        }
        if (c2 == 'o' && c1 == '(' && c == ')') {
            return 2;
        }
        if (c2 == 'c' && (c1 == '(' || c1 == '<' || c1 == '[')) {
            return 1;
        }
        if (c2 == 'e' && c1 == '\u20AC') {
            return 1;
        }
        if (c2 == 's' && c1 == '$') {
            return 1;
        }
        return c2 != 'l' || c1 != 'i' ? 0 : 1;
    }

    private static int method367(char c, char c1, char c2) {
        if (c2 == c) {
            return 1;
        }
        if (c2 >= 'a' && c2 <= 'm') {
            if (c2 == 'a') {
                if (c == '4' || c == '@' || c == '^') {
                    return 1;
                }
                return c != '/' || c1 != '\\' ? 0 : 2;
            }
            if (c2 == 'b') {
                if (c == '6' || c == '8') {
                    return 1;
                }
                return (c != '1' || c1 != '3') && (c != 'i' || c1 != '3') ? 0 : 2;
            }
            if (c2 == 'c') {
                return c != '(' && c != '<' && c != '{' && c != '[' ? 0 : 1;
            }
            if (c2 == 'd') {
                return (c != '[' || c1 != ')') && (c != 'i' || c1 != ')') ? 0 : 2;
            }
            if (c2 == 'e') {
                return c != '3' && c != '\u20AC' ? 0 : 1;
            }
            if (c2 == 'f') {
                if (c == 'p' && c1 == 'h') {
                    return 2;
                }
                return c != '\243' ? 0 : 1;
            }
            if (c2 == 'g') {
                return c != '9' && c != '6' && c != 'q' ? 0 : 1;
            }
            if (c2 == 'h') {
                return c != '#' ? 0 : 1;
            }
            if (c2 == 'i') {
                return c != 'y' && c != 'l' && c != 'j' && c != '1' && c != '!' && c != ':' && c != ';' && c != '|' ? 0
                        : 1;
            }
            if (c2 == 'j') {
                return 0;
            }
            if (c2 == 'k') {
                return 0;
            }
            if (c2 == 'l') {
                return c != '1' && c != '|' && c != 'i' ? 0 : 1;
            }
            return 0;
        }
        if (c2 >= 'n' && c2 <= 'z') {
            if (c2 == 'n') {
                return 0;
            }
            if (c2 == 'o') {
                if (c == '0' || c == '*') {
                    return 1;
                }
                return (c != '(' || c1 != ')') && (c != '[' || c1 != ']') && (c != '{' || c1 != '}')
                        && (c != '<' || c1 != '>') ? 0 : 2;
            }
            if (c2 == 'p') {
                return 0;
            }
            if (c2 == 'q') {
                return 0;
            }
            if (c2 == 'r') {
                return 0;
            }
            if (c2 == 's') {
                return c != '5' && c != 'z' && c != '$' && c != '2' ? 0 : 1;
            }
            if (c2 == 't') {
                return c != '7' && c != '+' ? 0 : 1;
            }
            final int i = (c != '\\' || c1 != '/') && (c != '\\' || c1 != '|') && (c != '|' || c1 != '/') ? 0 : 2;
            if (c2 == 'u') {
                if (c == 'v') {
                    return 1;
                }
                return i;
            }
            if (c2 == 'v') {
                return i;
            }
            if (c2 == 'w') {
                return c != 'v' || c1 != 'v' ? 0 : 2;
            }
            if (c2 == 'x') {
                return (c != ')' || c1 != '(') && (c != '}' || c1 != '{') && (c != ']' || c1 != '[')
                        && (c != '>' || c1 != '<') ? 0 : 2;
            }
            if (c2 == 'y') {
                return 0;
            }
            return 0;
        }
        if (c2 >= '0' && c2 <= '9') {
            if (c2 == '0') {
                if (c == 'o' || c == 'O') {
                    return 1;
                }
                return (c != '(' || c1 != ')') && (c != '{' || c1 != '}') && (c != '[' || c1 != ']') ? 0 : 2;
            }
            if (c2 == '1') {
                return c != 'l' ? 0 : 1;
            } else {
                return 0;
            }
        }
        if (c2 == ',') {
            return c != '.' ? 0 : 1;
        }
        if (c2 == '.') {
            return c != ',' ? 0 : 1;
        }
        if (c2 == '!') {
            return c != 'i' ? 0 : 1;
        } else {
            return 0;
        }
    }

    private static byte method368(char c) {
        if (c >= 'a' && c <= 'z') {
            return (byte) ((c - 97) + 1);
        }
        if (c == '\'') {
            return 28;
        }
        if (c >= '0' && c <= '9') {
            return (byte) ((c - 48) + 29);
        } else {
            return 27;
        }
    }

    private static void method369(char[] ac) {
        int j;
        int k = 0;
        int l = 0;
        int i1 = 0;
        while ((j = method370(k, ac)) != -1) {
            boolean flag = false;
            for (int j1 = k; j1 >= 0 && j1 < j && !flag; j1++) {
                if (!isSymbol(ac[j1]) && method373(ac[j1])) {
                    flag = true;
                }
            }
            if (flag) {
                l = 0;
            }
            if (l == 0) {
                i1 = j;
            }
            k = method371(ac, j);
            int k1 = 0;
            for (int l1 = j; l1 < k; l1++) {
                k1 = (k1 * 10 + ac[l1]) - 48;
            }
            if (k1 > 255 || k - j > 8) {
                l = 0;
            } else {
                l++;
            }
            if (l == 4) {
                for (int i2 = i1; i2 < k; i2++) {
                    ac[i2] = '*';
                }
                l = 0;
            }
        }
    }

    private static int method370(int j, char[] ac) {
        for (int k = j; k < ac.length && k >= 0; k++) {
            if (ac[k] >= '0' && ac[k] <= '9') {
                return k;
            }
        }
        return -1;
    }

    private static int method371(char[] ac, int i) {
        for (int j = i; j < ac.length && j >= 0; j++) {
            if (ac[j] < '0' || ac[j] > '9') {
                return j;
            }
        }
        return ac.length;
    }

    private static boolean isSymbol(char c) {
        return !isLetter(c) && !isNumerical(c);
    }

    private static boolean method373(char c) {
        if (c < 'a' || c > 'z') {
            return false;
        }
        return c != 'v' && c != 'x' && c != 'j' && c != 'q' && c != 'z';
    }

    private static boolean isLetter(char c) {
        return c >= 'a' && c <= 'z' || c >= 'A' && c <= 'Z';
    }

    private static boolean isNumerical(char c) {
        return c >= '0' && c <= '9';
    }

    private static boolean isLowercase(char c) {
        return c >= 'a' && c <= 'z';
    }

    private static boolean isUppercase(char c) {
        return c >= 'A' && c <= 'Z';
    }

    private static boolean method378(char[] ac) {
        boolean flag = true;
        for (char c : ac) {
            if (!isNumerical(c) && c != 0) {
                flag = false;
                break;
            }
        }
        if (flag) {
            return true;
        }
        int l = method379(ac);
        int i1 = 0;
        int j1 = fragments.length - 1;
        if (l == fragments[i1] || l == fragments[j1]) {
            return true;
        }
        do {
            int k1 = (i1 + j1) / 2;
            if (l == fragments[k1]) {
                return true;
            }
            if (l < fragments[k1]) {
                j1 = k1;
            } else {
                i1 = k1;
            }
        } while (i1 != j1 && i1 + 1 != j1);
        return false;
    }

    private static int method379(char[] ac) {
        if (ac.length > 6) {
            return 0;
        }
        int i = 0;
        for (int j = 0; j < ac.length; j++) {
            char c = ac[ac.length - j - 1];
            if (c >= 'a' && c <= 'z') {
                i = i * 38 + ((c - 97) + 1);
            } else if (c == '\'') {
                i = i * 38 + 27;
            } else if (c >= '0' && c <= '9') {
                i = i * 38 + ((c - 48) + 28);
            } else if (c != 0) {
                return 0;
            }
        }
        return i;
    }

}
