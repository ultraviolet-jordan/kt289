package com.kt289.chat;

import com.kt289.util.buffer.Buffer;

/**
 * @author Jordan Abraham
 */
public class ChatDecompression {

    private static final char[] characters = new char[100];
    private static final Buffer buffer = new Buffer(new byte[100]);

    public static String decompress(Buffer buffer, int length) {
        int count = 0;
        int value = -1;
        for (int index = 0; index < length; index++) {
            int hash = buffer.readUnsignedByte();
            int code = hash >> 4 & 0xf;
            if (value == -1) {
                if (code < 13) {
                    characters[count++] = PermittedCharacters.PERMITTED[code];
                } else {
                    value = code;
                }
            } else {
                characters[count++] = PermittedCharacters.PERMITTED[((value << 4) + code) - 195];
                value = -1;
            }
            code = hash & 0xf;
            if (value == -1) {
                if (code < 13) {
                    characters[count++] = PermittedCharacters.PERMITTED[code];
                } else {
                    value = code;
                }
            } else {
                characters[count++] = PermittedCharacters.PERMITTED[((value << 4) + code) - 195];
                value = -1;
            }
        }
        buildSentences(count);
        return new String(characters, 0, count);
    }

    private static void buildSentences(int count) {
        boolean sentence = true;
        for (int index = 0; index < count; index++) {
            char character = characters[index];
            if (sentence && character >= 'a' && character <= 'z') {
                characters[index] += '\uFFE0';
                sentence = false;
            }
            if (character == '.' || character == '!' || character == '?') {
                sentence = true;
            }
        }
    }

    public static String decompressFromCompression(String string) {
        buffer.offset = 0;
        ChatCompression.compress(string, buffer);
        int length = buffer.offset;
        buffer.offset = 0;
        return decompress(buffer, length);
    }
}
