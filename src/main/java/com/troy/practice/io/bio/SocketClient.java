package com.troy.practice.io.bio;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class SocketClient {

    public static void main(String[] args) {
        Socket socket = null;
        try {
            socket = new Socket("localhsot", 6666);
            DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
            dataOutputStream.write("test".getBytes());
            dataOutputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
