package com.lxw.website.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.util.ResourceLeakDetector;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.net.InetSocketAddress;

/**
 * @author LXW
 * @date 2021年07月02日 16:29
 */
@Component
@Slf4j
public class NettyService {

    @Value("${Netty.clientPort}")
    private Integer port;
    //处理连接
    private EventLoopGroup clientGroup=new NioEventLoopGroup();
    //处理客户端的数据
    private EventLoopGroup workGroup = new NioEventLoopGroup();


    // 被@PostConstruct修饰的方法会在服务器加载Servlet的时候运行，并且只会被服务器调用一次，类似于Servlet的inti()方法。被@PostConstruct修饰的方法会在构造函数之后，init()方法之前运行。
    @PostConstruct
    private void start() throws InterruptedException {
            ServerBootstrap serverBootstrap=new ServerBootstrap();
            serverBootstrap.group(clientGroup, workGroup)   //设置时间循环对象，前者用来处理accept事件，后者用于处理已经建立的连接的io
                    // 指定Channel  用它来建立新accept的连接，用于构造serversocketchannel的工厂类
                    .channel(NioServerSocketChannel.class)
                    //使用指定的端口设置套接字地址
                    .localAddress(new InetSocketAddress(port))
                    //服务端可连接队列数,对应TCP/IP协议listen函数中backlog参数
                    .option(ChannelOption.SO_BACKLOG,1024)
                    //设置TCP长连接,一般如果两个小时内没有数据的通信时,TCP会自动发送一个活动探测数据报文   重写ChannelInboundHandlerAdapter
                    .childOption(ChannelOption.SO_KEEPALIVE,true)
                    //将小的数据包包装成更大的帧进行传送，提高网络的负载,即TCP延迟传输
                    .childOption(ChannelOption.TCP_NODELAY,true)
                    .childHandler(new NettyServerInitializer());
            ResourceLeakDetector.setLevel(ResourceLeakDetector.Level.ADVANCED);
            ChannelFuture channelFuture=serverBootstrap.bind().sync();
            if(channelFuture.isSuccess()){
                log.info("启动 Netty Server");
            }
    }

    @PreDestroy
    private void destory() throws InterruptedException {
        clientGroup.shutdownGracefully().sync();
        workGroup.shutdownGracefully().sync();
        log.info("关闭Netty!");
    }
}
