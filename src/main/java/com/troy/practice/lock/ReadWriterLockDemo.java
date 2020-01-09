package com.troy.practice.lock;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ReadWriterLockDemo {

    public static void main(String[] args) {
        MyCache myCache = new MyCache();

        for (int i = 0; i <= 5; i++) {
            final int tempInt = i;
            new Thread(()->{
                myCache.put(tempInt + "", tempInt+"");
            }, String.valueOf(i)).start();
        }

        for (int i = 0; i <= 5; i++) {
            final int tempInt = i;
            new Thread(()->{
                myCache.get(tempInt+"");
            }, String.valueOf(i)).start();
        }
    }


}

class MyCache{

    private volatile Map<String,Object> map = new HashMap<>();
    //private Lock lock = new ReentrantLock();
    private ReentrantReadWriteLock reentrantReadWriteLock = new ReentrantReadWriteLock();

    public void put(String key,Object value){
        try{
            reentrantReadWriteLock.writeLock().lock();
            System.out.println(Thread.currentThread().getName() + "\t 正在写入:" + key);
            try {
                TimeUnit.MILLISECONDS.sleep(300);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            map.put(key, value);
            System.out.println(Thread.currentThread().getName() + "\t 写入完成:" );
        }finally {
            reentrantReadWriteLock.writeLock().unlock();
        }

    }

    public Object get(String key){
        try{
            reentrantReadWriteLock.readLock().lock();
            System.out.println(Thread.currentThread().getName() + "\t 正在读取:");
            try {
                TimeUnit.MILLISECONDS.sleep(300);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Object result = map.get(key);
            System.out.println(Thread.currentThread().getName() + "\t 读取完成:" + result);
            return result;
        }finally {
            reentrantReadWriteLock.readLock().unlock();
        }
    }

    public void clearMap(Map<String, Object> map) {
        map.clear();
    }
}
