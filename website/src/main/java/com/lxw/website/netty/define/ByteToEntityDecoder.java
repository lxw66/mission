package com.lxw.website.netty.define;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * @author mission
 * @version 1.0
 * @description: 自定义解码器
 * * 实现 ByteToMessageDecoder 接口，重写 decode 方法
 * @date 2021/7/12 13:52
 */
public class ByteToEntityDecoder  extends ByteToMessageDecoder {
    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {

    }
}
