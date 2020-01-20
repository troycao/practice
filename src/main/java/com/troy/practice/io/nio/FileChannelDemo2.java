package com.troy.practice.io.nio;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class FileChannelDemo2 {

    public static void main(String[] args) throws Exception {
        FileInputStream fileInputStream = new FileInputStream("./file01.txt");
        FileChannel channel = fileInputStream.getChannel();
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        channel.read(byteBuffer);
        /*String context = new String(byteBuffer.array());
        System.out.println(context);*/

        FileOutputStream fileOutputStream = new FileOutputStream("./file02.txt");
        FileChannel writeChannel = fileOutputStream.getChannel();
        byteBuffer.flip();
        writeChannel.write(byteBuffer);

        fileInputStream.close();
        fileOutputStream.close();
    }
}
