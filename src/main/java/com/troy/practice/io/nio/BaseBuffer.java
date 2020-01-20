package com.troy.practice.io.nio;

import java.nio.IntBuffer;
import java.nio.channels.Channel;

public class BaseBuffer {

    public static void main(String[] args) {
        IntBuffer intBuffer = IntBuffer.allocate(5);

        for (int i = 0; i < 5; i++) {
            intBuffer.put(i*2);
        }

        intBuffer.flip();
        intBuffer.position(3);

        while (intBuffer.hasRemaining()){
            System.out.println(intBuffer.get());
        }

        Channel channel = null;

    }
}
