package com.troy.practice.threadpool;

import java.util.concurrent.*;

public class PolicyTest {

    public static void main(String[] args) {
        PolicyTest test = new PolicyTest();
        test.testCachedThreadPool();
        System.out.println(0x7fffffff);
    }

    public void testThreadPool(){
        BlockingQueue<Runnable> queues = new ArrayBlockingQueue<>(5);
        ThreadPoolExecutor executor = new ThreadPoolExecutor(5, 10 , 100, TimeUnit.MILLISECONDS, queues);

        for (int i = 1; i < 20; i++) {
            final int temp = i;
            executor.execute(new Thread(()->{
                System.out.println(temp);
                try {
                    TimeUnit.MILLISECONDS.sleep(5);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            },String.valueOf(i)));
        }
    }

    public void testCachedThreadPool(){
        ExecutorService executor1 = Executors.newCachedThreadPool();
        executor1.execute(new Thread(()->{
            System.out.println("");
        }));


        Executors.newFixedThreadPool(10);

        Executors.newScheduledThreadPool(10);

        Executors.newSingleThreadExecutor();
    }
}
