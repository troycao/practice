package com.troy.practice.base;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

public class ContainerNotSafeDemo {

    public static void main(String[] args) {
        /*List<String> list = Collections.synchronizedList(new ArrayList<>());
        for (int i = 0; i <= 30; i++) {
            new Thread(()->{
                list.add(UUID.randomUUID().toString().substring(0,8));
                System.out.println(list);
            }, String.valueOf(i)).start();
        }*/

        /*Vector<String> vector = new Vector<>();
        for (int i = 0; i < 30; i++) {
            new Thread(()->{
                vector.add(UUID.randomUUID().toString().substring(0,8));
                System.out.println(vector);
            },String.valueOf(i)).start();
        }*/

        Set<String> set = new HashSet<>();

        List<String> list = new CopyOnWriteArrayList<>();
        for (int i = 0; i <= 30; i++) {
            new Thread(()->{
                list.add(UUID.randomUUID().toString().substring(0,8));
                System.out.println(list);
            }, String.valueOf(i)).start();
        }
    }
}
