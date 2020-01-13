package com.troy.practice.juc.prodconsumer;

public class SyncDemo {

    public static void main(String[] args) {

        ShareData1 shareData1 = new ShareData1();
        new Thread(()->{
            for (int i = 0; i < 5; i++) {
                shareData1.increment();
            }
        },"AA").start();

        new Thread(()->{
            for (int i = 0; i < 5; i++) {
                shareData1.decrement();
            }
        }, "BB").start();

        new Thread(()->{
            for (int i = 0; i < 5; i++) {
                shareData1.decrement();
            }
        }, "CC").start();

        new Thread(()->{
            for (int i = 0; i < 5; i++) {
                shareData1.increment();
            }
        }, "DD").start();
    }

}

class ShareData1{

    private volatile int number = 0;

    public synchronized void increment(){
        while(number != 0){
            try {
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        number ++;
        System.out.println(Thread.currentThread().getName() + "\t number:" + number);
        this.notify();
    }

    public synchronized void decrement(){
        while (number == 0){
            try {
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        number--;
        System.out.println(Thread.currentThread().getName() + "\t number:"+ number );
        this.notify();
    }

}
