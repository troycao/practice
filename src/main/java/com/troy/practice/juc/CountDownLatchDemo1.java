package com.troy.practice.juc;

import com.troy.practice.common.CountryEnum;

import java.util.concurrent.CountDownLatch;

/**
 * CountDownLatch
 * 等所有线程执行完毕，再执行
 */

public class CountDownLatchDemo1 {

    public static void main(String[] args) {
        CountDownLatch countDownLatch = new CountDownLatch(6);

        for (int i = 1; i <= 6; i++) {
            new Thread(()->{
                System.out.println(Thread.currentThread().getName() + "\t 国，被灭");
                countDownLatch.countDown();
            }, CountryEnum.forEach_CountEnum(i).getRetMessage()).start();
        }

        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName() + "\t ***********秦帝国，一统华夏!");
        for (CountryEnum a :CountryEnum.values()) {
            System.out.println(a.getRetMessage());
        }

        System.out.println(CountryEnum.SIX);
        System.out.println(CountryEnum.SIX.retMessage);

    }
}
