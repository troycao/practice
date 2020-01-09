package com.troy.practice.lock;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

public class ReenterLockDemo2 {

    public static void main(String[] args) {

        TroyCache cache = new TroyCache();
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

class TroyCache{

    private volatile Map<String,Object> cache = new HashMap<>();
    ReentrantLock lock = new ReentrantLock();

    public void put(String key,Object value){
        lock.lock();
        try {
            System.out.println(Thread.currentThread().getName() + "\t 正在写入:" + key);
            cache.put(key,value);
            try {
                TimeUnit.MILLISECONDS.sleep(300);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + "\t 写入完成");
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            lock.unlock();
        }
    }

    public Object get(String key){
        lock.lock();
        try{
            System.out.println(Thread.currentThread().getName() + "\t 正在读取");
            Object o = cache.get(key);
            try {
                TimeUnit.MILLISECONDS.sleep(300);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + "\t 读取完成:" + o);
            return o;
        }finally {
            lock.unlock();
        }
    }

    public void clear(){
        cache.clear();
    }

}
