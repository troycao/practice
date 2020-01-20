package com.troy.practice.base;

/**
 * 字符串是final修饰的不可变类
 *
 */

public class StringDemo {

    public static void main(String[] args) {
        /*String a = "abc";
        String b = "abc";
        System.out.println(a);
        System.out.println(b);
        System.out.println(a==b);

        String c = new String("abcd");
        String d = new String("abcd");
        System.out.println(c);
        System.out.println(d);
        System.out.println(c == d);*/

        /*ShareData shareData = new ShareData();
        ShareData shareData1 = new ShareData();
        System.out.println(shareData);
        System.out.println(shareData1);
        System.out.println(shareData == shareData1);*/

        StringBuffer sb = new StringBuffer();

    }
}

class ShareData{

    private String name;
}
