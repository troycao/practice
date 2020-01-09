package com.troy.practice.nio;

import org.apache.mina.core.service.IoAcceptor;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.textline.LineDelimiter;
import org.apache.mina.filter.codec.textline.TextLineCodecFactory;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.charset.Charset;

/**
 * mina实例
 */
public class MinaServer {
    static int port = 9001;
    static IoAcceptor acceptor = null;

    public static void main(String[] args) {
        try {
            acceptor = new NioSocketAcceptor();
            // 设置编码过滤器
            acceptor.getFilterChain().addLast(
                    "codec",
                    new ProtocolCodecFilter(new TextLineCodecFactory(Charset
                            .forName("UTF-8"),
                            LineDelimiter.WINDOWS.getValue(),
                            LineDelimiter.WINDOWS.getValue())));
            acceptor.getSessionConfig().setReadBufferSize(1024);
            acceptor.getSessionConfig().setIdleTime(IdleStatus.BOTH_IDLE, 10);
            acceptor.setHandler(new MyServerHandler());
            acceptor.bind(new InetSocketAddress(port));
            System.out.println("Server->" + port);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
