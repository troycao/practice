package com.troy.practice.base;

import java.util.concurrent.ConcurrentHashMap;

public class ConcurrentHashMapTest {

    public static void main(String[] args) {
        ConcurrentHashMap<String,String> map = new ConcurrentHashMap<String, String>();
        map.put("test","test1");

        String test = map.remove("test");
        System.out.println(test);

        System.out.println(map.size());
    }
}
