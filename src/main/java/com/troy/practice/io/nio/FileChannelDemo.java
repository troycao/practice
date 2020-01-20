package com.troy.practice.io.nio;

import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class FileChannelDemo {

    public static void main(String[] args) throws Exception{
        String str = "hello troy";
        FileOutputStream fileOutputStream = new FileOutputStream("./file01.txt");
        FileChannel channel = fileOutputStream.getChannel();
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        byteBuffer.put(str.getBytes());
        byteBuffer.flip();

        channel.write(byteBuffer);
        fileOutputStream.close();

    }
}
