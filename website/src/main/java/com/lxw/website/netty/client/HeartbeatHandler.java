package com.lxw.website.netty.client;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.util.CharsetUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.CharEncoding;

/**
 * 自定义心跳   可以不适用NettyServer 的默认心跳
 * .childOption(ChannelOption.SO_KEEPALIVE,true)
 * 重写ChannelInboundHandlerAdapter
 * @author LXW
 * @date 2021年07月06日 13:25
 */
@Slf4j
public class HeartbeatHandler  extends ChannelInboundHandlerAdapter {

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if(evt instanceof IdleStateEvent){
            IdleStateEvent idleStateEvent=(IdleStateEvent) evt;
            if(idleStateEvent.state() == IdleState.WRITER_IDLE){
                log.info("已经10秒没有发送消息给服务端");
                ctx.writeAndFlush(Unpooled.copiedBuffer("^--^--^--^--^--^", CharsetUtil.UTF_8)).addListener(ChannelFutureListener.CLOSE_ON_FAILURE);
            }
        } else {
            super.userEventTriggered(ctx,evt);
        }

    }
}
