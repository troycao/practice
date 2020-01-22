package com.troy.practice.io.nio;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Set;

public class ServerDemo1 {

    public static void main(String[] args) throws Exception {
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        Selector selector = Selector.open();
        serverSocketChannel.socket().bind(new InetSocketAddress(6666));
        serverSocketChannel.configureBlocking(false);
        //把serversocketchannel注册到selector关心实践 OP_ACCEPT
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

        while (true){
            if (selector.select(1000)==0){
                System.out.println("服务器等待了一秒钟，无连接");
                continue;
            }

            //如果返回有连接，就获取相关的selectionkey集合
            Set<SelectionKey> selectionKeys = selector.selectedKeys();
            Iterator<SelectionKey> iteratorKey = selectionKeys.iterator();
            while (iteratorKey.hasNext()){
                SelectionKey key = iteratorKey.next();
                if (key.isAcceptable()){
                    //生成一个socketchannel
                    SocketChannel socketChannel = serverSocketChannel.accept();
                    socketChannel.configureBlocking(false);
                    socketChannel.register(selector,SelectionKey.OP_READ, ByteBuffer.allocate(1024));
                }
                if (key.isReadable()){
                    SocketChannel channel = (SocketChannel) key.channel();
                    ByteBuffer byteBuffer = (ByteBuffer)key.attachment();
                    channel.read(byteBuffer);
                    System.out.println("from 客户端" + new String(byteBuffer.array()));
                }

                //手动移除selectionkey
                iteratorKey.remove();
            }
        }

    }
}
