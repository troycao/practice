package com.troy.practice.threadpool;

import java.util.concurrent.*;

public class ThreadPoolTest {

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newCachedThreadPool();

        ThreadPoolExecutor threadPoolExecutor;
        threadPoolExecutor = new ThreadPoolExecutor(2,5,200, TimeUnit.SECONDS, new ArrayBlockingQueue<>(5));

        threadPoolExecutor.execute(new Thread(()->{
            System.out.println("test");
        }));


    }
}
