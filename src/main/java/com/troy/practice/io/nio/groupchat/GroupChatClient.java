package com.troy.practice.io.nio.groupchat;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class GroupChatClient {

    private final String HOST = "127.0.0.1";
    private final int PORT = 6667;

    private Selector selector;
    private SocketChannel socketChannel;
    private String userName;

    public GroupChatClient() throws Exception{
        selector = Selector.open();
        socketChannel = SocketChannel.open(new InetSocketAddress(HOST, PORT));
        socketChannel.configureBlocking(false);
        socketChannel.register(selector, SelectionKey.OP_READ);
        userName = socketChannel.getLocalAddress().toString().substring(1);
        System.out.println(userName + " is ok...");
    }

    public void sendMessage(String message){
        message = userName + "说:" + message;

        try {
            socketChannel.write(ByteBuffer.wrap(message.getBytes()));
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void readMessage(){
        try {
            int select = selector.select();
            if (select>0){
                Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
                while (iterator.hasNext()){
                    SelectionKey selectionKey = iterator.next();
                    if (selectionKey.isReadable()){
                        SocketChannel channel = (SocketChannel)selectionKey.channel();
                        //channel.configureBlocking(false);
                        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
                        channel.read(byteBuffer);
                        String message = new String(byteBuffer.array());
                        System.out.println(message);
                    }
                }
            }else {
                System.out.println("没有可用的通道...");
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws Exception{
        GroupChatClient groupChatClient = new GroupChatClient();
        new Thread(()->{
            while (true){
                groupChatClient.readMessage();

                try {
                    TimeUnit.SECONDS.sleep(3);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextLine()){
            String s = scanner.nextLine();
            groupChatClient.sendMessage(s);
        }
    }
}
