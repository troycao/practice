package com.troy.practice.lock;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class ReenterLockDemo1 {

    public static void main(String[] args) throws Exception{
        /*Phone phone = new Phone();

        new Thread(()->{
            try {
                phone.sendSMS();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }, "t1").start();

        new Thread(()->{
            try {
                phone.sendSMS();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }, "t2").start();*/

        ReenterLockDemo1 reenterLockDemo1 = new ReenterLockDemo1();
        reenterLockDemo1.sendSMS1();
    }


    /*String  sync = "";
    public void test(boolean fair){
        sync = fair ? "true" : "false";
        System.out.println(sync);
    }*/

    volatile int i = 0;

    public void sendSMS1(){
        /*ReentrantLock reentrantLock = new ReentrantLock(true);
        reentrantLock.lock();
        try{
            i ++;
        }finally {
            reentrantLock.unlock();
        }*/
        ReentrantLock reentrantLock = new ReentrantLock();
        reentrantLock.lock();
        try{
            i ++;
        }finally {
            reentrantLock.unlock();
        }
    }

}

