package com.troy.practice.juc.bq;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.TimeUnit;

public class SynchronousQueueDemo {

    public static void main(String[] args) {
        BlockingQueue<String> blockingQueue = new SynchronousQueue<>();
        new Thread(()->{
            try {
                System.out.println(Thread.currentThread().getName() + "\t put a");
                blockingQueue.put("a");

                System.out.println(Thread.currentThread().getName() + "\t put b");
                blockingQueue.put("b");

                System.out.println(Thread.currentThread().getName() + "\t put c");
                blockingQueue.put("c");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        },"t1").start();

        for (int i = 0; i < 3; i++) {
            new Thread(()->{
                try {
                    TimeUnit.SECONDS.sleep(5);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                try {
                    System.out.println(Thread.currentThread().getName() +"\t " + blockingQueue.take());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            },"t2").start();
        }

    }
}
