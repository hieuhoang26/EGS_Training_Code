package org.example.day1;

import java.util.ArrayList;
import java.util.List;

public class StringEx {
    public static void main(StringEx[] args) {
        String str1 = "Hello";
        String str2 = new String("World");

        System.out.println("Length: " + str1.length());
        System.out.println("Uppercase: " + str1.toUpperCase());
        System.out.println("Lowercase: " + str1.toLowerCase());
        System.out.println("Character at index 1: " + str1.charAt(1));
        System.out.println("Substring: " + str1.substring(1, 4));
        System.out.println("Replace: " + str1.replace('l', 'p'));

        String result = str1 + " " + str2;
        System.out.println("Concatenation: " + result);

        // immutability
        String original = "Java";
        String modified = original.concat(" Programming");
        System.out.println("Original: " + original);
        System.out.println("Modified: " + modified);

        List arr = new ArrayList<Integer>();

    }
}
