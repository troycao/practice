package com.troy.practice.disruptor.provider;

import com.lmax.disruptor.RingBuffer;
import com.troy.practice.disruptor.consumer.StringEvent;

public class StringEventProducer {

    public final RingBuffer<StringEvent> ringBuffer;

    public StringEventProducer(RingBuffer<StringEvent> ringBuffer) {
        this.ringBuffer = ringBuffer;
    }

    public void onData(String data){
        long sequence = ringBuffer.next();
        try {
            StringEvent stringEvent = ringBuffer.get(sequence);
            stringEvent.setValue(data);
        }finally {
            ringBuffer.publish(sequence);
        }
    }
}
