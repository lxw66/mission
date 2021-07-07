package com.lxw.website.netty;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.EventExecutor;
import io.netty.util.concurrent.GlobalEventExecutor;
import lombok.extern.slf4j.Slf4j;

/**
 * @author LXW
 * @date 2021年07月05日 13:31
 */
@Slf4j
public class NettyServerHandler extends SimpleChannelInboundHandler<String> {

    private static ChannelGroup channels=new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);


    //重写  SimpleChannelInboundHandler。channelRead0
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, String s) throws Exception {
        log.info("接收到客户端消息：{}", s);
    }

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        Channel comings=ctx.channel();
        comings.writeAndFlush("添加通道：" + comings.remoteAddress());
        channels.add(comings);
    }
    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        Channel comings=ctx.channel();
        comings.writeAndFlush("移除通道：" + comings.remoteAddress());
    }
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        Channel incoming = ctx.channel();
        log.info("NettyServer:" + incoming.remoteAddress() + "在线");
    }
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        Channel incoming = ctx.channel();
        log.info("NettyServer:" + incoming.remoteAddress() + "掉线");
    }
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        Channel incoming = ctx.channel();
        log.info("NettyServer:" + incoming.remoteAddress() + "异常");
        // 当出现异常就关闭连接
        cause.printStackTrace();
        ctx.close();
    }
}
