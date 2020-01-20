package com.troy.practice.disruptor.consumer;


import com.lmax.disruptor.EventHandler;

import java.util.concurrent.TimeUnit;

public class StringEventHandler implements EventHandler<StringEvent> {

    @Override
    public void onEvent(StringEvent stringEvent, long sequence, boolean endOfBatch) throws Exception {
        TimeUnit.MILLISECONDS.sleep(5000);
        System.out.println("消费者:" + stringEvent.getValue() + "-->sequence=" + sequence + ",endOfBatch=" + endOfBatch);
    }

}
