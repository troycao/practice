package com.troy.practice.base;

import java.util.concurrent.locks.ReentrantLock;

public class SyncTest {

    volatile int i = 0;

    public static void main(String[] args) {
        SyncTest syncTest = new SyncTest();
        syncTest.add();
    }

    public void add(){
        /*synchronized(this){
            i++;
        }
*/
        ReentrantLock reentrantLock = new ReentrantLock();
        reentrantLock.lock();
        try {
            i++;
        }finally {
            reentrantLock.unlock();
        }
    }
}
