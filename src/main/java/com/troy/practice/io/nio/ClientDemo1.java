package com.troy.practice.io.nio;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class ClientDemo1 {

    public static void main(String[] args) throws Exception{
        SocketChannel socketChannel = SocketChannel.open();
        socketChannel.configureBlocking(false);
        InetSocketAddress inetSocketAddress = new InetSocketAddress("127.0.0.1",6666);
        if (!socketChannel.connect(inetSocketAddress)){
            while (!socketChannel.finishConnect()){
                System.out.println("因为连接需要时间,客户端不会阻塞,可以做其他工作..");
            }
        }

        String str = "hello,troy";
        ByteBuffer byteBuffer = ByteBuffer.wrap(str.getBytes());
        //发送数据,将buffer数据些人channel
        socketChannel.write(byteBuffer);
        System.in.read();

    }
}
