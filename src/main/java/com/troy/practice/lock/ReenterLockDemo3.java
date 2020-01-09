package com.troy.practice.lock;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class ReenterLockDemo3 {

    public static void main(String[] args) {

        TroyCache1 cache = new TroyCache1();
        for (int i = 0; i < 5; i++) {
            final int temp = i;
            new Thread(()->{
                cache.put(String.valueOf(temp), temp + "");
            }, "t1").start();
        }

        for (int i = 0; i < 5; i++) {
            final int temp = i;
            new Thread(()->{
                cache.get(temp + "");
            }, "t2").start();
        }

    }
}

class TroyCache1{

    private volatile Map<String,Object> cache = new HashMap<>();

    public synchronized void put(String key,Object value){
        System.out.println(Thread.currentThread().getName() + "\t 正在写入:" + key);
        cache.put(key,value);
        try {
            TimeUnit.MILLISECONDS.sleep(300);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName() + "\t 写入完成");
    }

    public synchronized Object get(String key){
        System.out.println(Thread.currentThread().getName() + "\t 正在读取");
        Object o = cache.get(key);
        try {
            TimeUnit.MILLISECONDS.sleep(300);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName() + "\t 读取完成:" + o);
        return o;
    }

    public void clear(){
        cache.clear();
    }

}
