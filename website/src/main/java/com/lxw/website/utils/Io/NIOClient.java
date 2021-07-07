package com.lxw.website.utils.Io;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

/**
 * NIO通过一个Selector，负责监听各种IO事件的发生，然后交给后端的线程去处理。NIO相比与BIO而言，
 * 非阻塞体现在轮询处理上。BIO后端线程需要阻塞等待客户端写数据，如果客户端不写数据就一直处于阻塞状态。
 * 而NIO通过Selector进行轮询已注册的客户端，当有事件发生时才会交给后端去处理，后端线程不需要等待。
 * @author LXW
 * @date 2021年06月18日 15:09
 */
@Slf4j
@Component
public class NIOClient {

    @Value("${TCP.serverPort}")
    private int serverPort;
    @Value("${TCP.ip}")
    private String tcpIp;


    public void  NIOSendMsg(String msg){
        try{
            SocketChannel socketChannel=SocketChannel.open();
            socketChannel.configureBlocking(false);
            socketChannel.connect(new InetSocketAddress(tcpIp,serverPort));

            Selector selector=Selector.open();
            socketChannel.register(selector, SelectionKey.OP_CONNECT);

           while(true){
               selector.select();
               Iterator<SelectionKey> selectionKeyIterator=selector.selectedKeys().iterator();
               while(selectionKeyIterator.hasNext()){
                   SelectionKey selectionKey=selectionKeyIterator.next();
                   selectionKeyIterator.remove();
                   handle(selectionKey,msg);
               }
           }
        }catch (Exception e){
            log.info("----BIO客户端异常："+e.getMessage());
        }

    }

    private void handle(SelectionKey key,String msg) throws IOException {
        if(key.isConnectable()){
            SocketChannel socketChannel=(SocketChannel) key.channel();
            if(socketChannel.isConnectionPending()){
                socketChannel.finishConnect();
            }
            socketChannel.configureBlocking(false);
            socketChannel.write(ByteBuffer.wrap(msg.getBytes()));
            socketChannel.register(key.selector(),SelectionKey.OP_READ);
        }
        if(key.isReadable()){
            SocketChannel socketChannel=(SocketChannel) key.channel();
            ByteBuffer buffer=ByteBuffer.allocate(1024);
            int len=socketChannel.read(buffer);
            String rec=null;
            if((len!=-1)){
                rec=new String(buffer.array(),0,len);
                log.info("----BIO客户端接受数据："+rec);
            }
        }
    }
}
