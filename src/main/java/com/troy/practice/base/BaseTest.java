package com.troy.practice.base;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;

public class BaseTest {

    public static void main(String[] args) {
        String hexStr = "2E02303039353030303130303030202020343835343030303020202000000000303030303030303000303030303030383230822000008000000004000000000000003037323531363134303033313532343230383438353430303030333031";
        byte[] bytes = hexStringToByte(hexStr);

        try {
            String testStr = new String(bytes,"gbk");
            System.out.println(testStr);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        System.out.println(Arrays.toString(bytes));
    }

    public static byte[] hexStringToByte(String hex) {

        int len = (hex.length() / 2);
        byte[] result = new byte[len];
        char[] achar = hex.toUpperCase().toCharArray();
        for (int i = 0; i < len; i++) {
            int pos = i * 2;
            result[i] = (byte) (toByte(achar[pos]) << 4 | toByte(achar[pos + 1]));
        }
        return result;
    }

    private static byte toByte(char c) {
        return (byte) "0123456789ABCDEF".indexOf(c);
    }
}
