package com.lxw.website.netty.client;

import com.sun.corba.se.impl.protocol.giopmsgheaders.MessageBase;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.CharsetUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
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
//@DependsOn  注解  依赖nettyService
@DependsOn("nettyService")
public class NettyClient {
    @Value("${Netty.clientPort}")
    private int port;
    @Value("${Netty.clientIp}")
    private String ip;

    private EventLoopGroup eventExecutors=new NioEventLoopGroup();
    private NioSocketChannel socketChannel;

    public void sendMsg(String message) throws IOException {
        socketChannel.writeAndFlush(Unpooled.copiedBuffer(message, CharsetUtil.UTF_8));
    }

    @PostConstruct
    public void run() {
        Bootstrap bootstrap=new Bootstrap();
        bootstrap.group(eventExecutors)
                .channel(NioSocketChannel.class)
                .remoteAddress(ip,port)
                .option(ChannelOption.SO_KEEPALIVE,true)
                .option(ChannelOption.TCP_NODELAY,true)
                .handler(new NettyClientInitializer());
        try{
            ChannelFuture future=bootstrap.connect().sync();
            future.addListener(new ConnectionListener());
            //future.channel().closeFuture().sync();
            socketChannel= (NioSocketChannel) future.channel();
        }catch (InterruptedException e){
            log.info("Netty客户端绑定失败!");
        }

    }

    @PreDestroy
    private void destory() throws InterruptedException {
        eventExecutors.shutdownGracefully().sync();
        log.info("关闭Netty客户端!");
    }
}
