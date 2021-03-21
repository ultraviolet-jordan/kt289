package com.kt289.chat;

import com.kt289.util.buffer.Buffer;

import java.util.stream.IntStream;

public class ChatCompression {

    public static void compress(String string, Buffer buffer) {
        if (string.length() > 80) {
            string = string.substring(0, 80);
        }
        string = string.toLowerCase();
        int value = -1;
        for (int index = 0; index < string.length(); index++) {
            char character = string.charAt(index);
            int code = IntStream.range(0, PermittedCharacters.PERMITTED.length)
                    .filter(validIndex -> character == PermittedCharacters.PERMITTED[validIndex])
                    .findFirst()
                    .orElse(0);
            if (code > 12) {
                code += 195;
            }
            if (value == -1) {
                if (code < 13) {
                    value = code;
                } else {
                    buffer.writeByte(code);
                }
            } else if (code < 13) {
                buffer.writeByte((value << 4) + code);
                value = -1;
            } else {
                buffer.writeByte((value << 4) + (code >> 4));
                value = code & 0xf;
            }
        }
        if (value != -1) {
            buffer.writeByte(value << 4);
        }
    }
}
