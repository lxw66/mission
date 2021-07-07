package com.lxw.website.netty.client;

import com.sun.corba.se.impl.protocol.giopmsgheaders.MessageBase;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.concurrent.TimeUnit;

/**
 * @author LXW
 * @date 2021年07月05日 15:34
 */
@Slf4j
@Component
public class NettyClient {
    @Value("${Netty.clientPort}")
    private int port;
    @Value("${Netty.clientIp}")
    private String ip;

    private EventLoopGroup eventExecutors=new NioEventLoopGroup();
    private SocketChannel socketChannel;

    public void sendMsg(String message) throws IOException {
        socketChannel.write(ByteBuffer.wrap(message.getBytes()));
    }

    @PostConstruct
    public void run(){
        Bootstrap bootstrap=new Bootstrap();
        bootstrap.group(eventExecutors)
                .channel(NioSocketChannel.class)
                .remoteAddress(ip,port)
                .option(ChannelOption.SO_KEEPALIVE,true)
                .option(ChannelOption.TCP_NODELAY,true)
                .handler(new NettyClientInitializer());

        ChannelFuture future=bootstrap.connect();
        future.addListener(new ConnectionListener());
    }
}
