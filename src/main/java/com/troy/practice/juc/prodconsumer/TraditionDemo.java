package com.troy.practice.juc.prodconsumer;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class TraditionDemo {

    public static void main(String[] args) {

    }
}

class ShareData{
    private int number = 0;
    private Lock lock = new ReentrantLock();
    private Condition condition = lock.newCondition();

    public void increment() throws Exception{
        lock.lock();
        try {
            while (number != 0 ){
                condition.await();
            }
            number ++ ;
            System.out.println(Thread.currentThread().getName() + "\t " + number);
            condition.signalAll();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            lock.unlock();
        }

    }
}