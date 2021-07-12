package com.lxw.website.netty.client;

import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.EventLoop;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

/**
 * @author LXW
 * @date 2021年07月06日 10:19
 */
@Slf4j
public class ConnectionListener implements ChannelFutureListener {
    private NettyClient nettyClient=new NettyClient();

    @Override
    public void operationComplete(ChannelFuture channelFuture) throws Exception {
        if(channelFuture.isSuccess()){
            log.info("ConnectionListener:服务端链接成功..." );
        }else{
            EventLoop eventLoop=channelFuture.channel().eventLoop();
            eventLoop.schedule(new Runnable() {
                @Override
                public void run() {
                    log.info("ConnectionListener: 服务端链接不上，开始重连操作.." );
                    nettyClient.run();
                }
            },1L, TimeUnit.SECONDS);
        }
    }
}
