package com.troy.practice.juc.bq;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

public class BlockingQueueDemo {

    public static void main(String[] args) throws InterruptedException {
        //List<String> list = new ArrayList();

        BlockingQueue<String> blockingQueue = new ArrayBlockingQueue<>(3);
        /**抛异常*/
        /*System.out.println(blockingQueue.add("a"));
        System.out.println(blockingQueue.add("b"));
        System.out.println(blockingQueue.add("c"));

        System.out.println(blockingQueue.element());
        System.out.println(blockingQueue.remove());
        System.out.println(blockingQueue.remove());
        System.out.println(blockingQueue.remove());*/
        /**返回布尔型*/
        /*System.out.println(blockingQueue.offer("a"));
        System.out.println(blockingQueue.offer("b"));
        System.out.println(blockingQueue.offer("c"));

        System.out.println(blockingQueue.offer("d"));

        System.out.println(blockingQueue.peek());

        System.out.println(blockingQueue.poll());
        System.out.println(blockingQueue.poll());
        System.out.println(blockingQueue.poll());
        System.out.println(blockingQueue.poll());*/
        /**无返回值 阻塞*/
        /*blockingQueue.put("a");
        blockingQueue.put("b");
        blockingQueue.put("c");

        blockingQueue.put("d");

        System.out.println(blockingQueue.take());
        System.out.println(blockingQueue.take());
        System.out.println(blockingQueue.take());
        System.out.println(blockingQueue.take());*/

        /*System.out.println(blockingQueue.offer("a", 2, TimeUnit.SECONDS));
        System.out.println(blockingQueue.offer("B", 2, TimeUnit.SECONDS));
        System.out.println(blockingQueue.offer("C", 2, TimeUnit.SECONDS));
        System.out.println(blockingQueue.offer("d", 2, TimeUnit.SECONDS));*/


    }
}
