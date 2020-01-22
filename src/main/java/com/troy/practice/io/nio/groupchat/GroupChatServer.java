package com.troy.practice.io.nio.groupchat;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;

public class GroupChatServer {

    private Selector selector;

    private ServerSocketChannel serverSocketChannel;

    private static final int PORT = 6667;

    public GroupChatServer() {
        try {
            selector = Selector.open();
            serverSocketChannel = ServerSocketChannel.open();
            serverSocketChannel.socket().bind(new InetSocketAddress(PORT));
            serverSocketChannel.configureBlocking(false);
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    //监听
    public void listen(){
        try {
            while (true){
                //监听事件
                int count = selector.select();
                if (count>0){
                    //遍历得到的selectionkey
                    Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
                    while(iterator.hasNext()){
                        //取出selectionkey
                        SelectionKey key = iterator.next();
                        if (key.isAcceptable()){
                            SocketChannel accept = serverSocketChannel.accept();
                            accept.configureBlocking(false);
                            //将socketchannel注册到selector上
                            accept.register(selector, SelectionKey.OP_READ);
                            System.out.println(accept.getRemoteAddress() + "上线");
                        }
                        if (key.isReadable()){
                            readData(key);
                        }

                        //防止重复处理
                        iterator.remove();
                    }
                }
            }
        }catch (Exception e){

        }finally {

        }
    }
    
    private void readData(SelectionKey selectionKey){
        SocketChannel channel = null;
        try{
            channel = (SocketChannel) selectionKey.channel();
            ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
            int count = channel.read(byteBuffer);
            if (count >0){
                String message = new String(byteBuffer.array());
                System.out.println("from 客户端：" + message);

                //给其他客户端转发消息
                sendMessageToOtherClient(message, channel);
            }
        } catch (Exception e){
            try {
                System.out.println(channel.getRemoteAddress() + "离线");
                selectionKey.cancel();
                channel.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }

    private void sendMessageToOtherClient(String message,SocketChannel self) throws Exception{
        System.out.println("服务器转发消息中...");
        //遍历所有注册到selector上的socketchannel,并排除自己
        for (SelectionKey key:selector.keys()){
            Channel channel = key.channel();
            //排除自己
            if (channel instanceof SocketChannel && channel != self){
                SocketChannel dest = (SocketChannel) channel;
                ByteBuffer byteBuffer = ByteBuffer.wrap(message.getBytes());
                dest.write(byteBuffer);
            }
        }
    }

    public static void main(String[] args) {
        GroupChatServer chatServer = new GroupChatServer();
        chatServer.listen();
    }
}
