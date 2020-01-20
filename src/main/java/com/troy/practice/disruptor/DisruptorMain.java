package com.troy.practice.disruptor;

import com.lmax.disruptor.EventFactory;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.YieldingWaitStrategy;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;
import com.troy.practice.disruptor.consumer.StringEvent;
import com.troy.practice.disruptor.consumer.StringEventFactory;
import com.troy.practice.disruptor.consumer.StringEventHandler;
import com.troy.practice.disruptor.provider.StringEventProducer;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

/***
 * disruptor是一个高效的生产者消费者模式的框架，内置了RingBuffer（环形队列）作为数据结构，无锁无阻塞队列，事件驱动，可以设置拒绝策略
 * 是一个非常优秀的基于JVM的内存框架。
 * 但是它至少基于JVM的队列，不能在网络中共享数据，无法大规模利用在分布式系统中，所以只适用于单机系统。他是一个内存式的队列，没有补救措施，
 * 所以一旦出现服务器宕机，数据不能恢复。
 * 所以它只是一个优秀的架构，以及一个优秀的设计思想，可以适用于一些单机程序的场景，在分布式系统大行其道的今天，很难大规模使用
 */

public class DisruptorMain {

    public static void main(String[] args) {
        ThreadFactory threadFactory = Executors.defaultThreadFactory();

        EventFactory<StringEvent> eventFactory = new StringEventFactory();

        int ringBufferSize = 16;

        Disruptor<StringEvent> disruptor = new Disruptor<StringEvent>(eventFactory,
                ringBufferSize,threadFactory, ProducerType.SINGLE,new YieldingWaitStrategy());

        disruptor.handleEventsWith(new StringEventHandler());

        disruptor.start();

        RingBuffer<StringEvent> ringBuffer = disruptor.getRingBuffer();
        System.out.println(ringBuffer.toString());

        StringEventProducer producer = new StringEventProducer(ringBuffer);

        for (int i = 0; i < 10000; i++) {
            producer.onData(String.valueOf(i));
        }

        disruptor.shutdown();

    }
}
