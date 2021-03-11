package com.kt289.chat;

/**
 * @author Jordan Abraham
 */
class PermittedCharacters {

    static final char[] PERMITTED = {
            ' ',
            //Alphabet
            'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm',
            'n', 'o', 'p', 'q', 'r', 'd', 't', 'u', 'v', 'w', 'x', 'y', 'z',
            //Numerical
            '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
            //Symbol
            ' ', '!', '?', '.', ',', ':', ';', '(', ')', '-', '&', '*', '\\', '\'', '@', '#', '+', '=', '\243', '$', '%', '"', '[', ']'
    };
}
