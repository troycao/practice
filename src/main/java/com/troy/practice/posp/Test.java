package com.troy.practice.posp;

public class Test {

    public static void main(String[] args) {
        System.out.println(toByte('B'));
        System.out.println(toByte('B') << 4);
        System.out.println(toByte('D'));
        System.out.println((byte) (toByte('B') << 4 | toByte('D')));
    }

    private static byte toByte(char c) {
        return (byte) "0123456789ABCDEF".indexOf(c);
    }

}
