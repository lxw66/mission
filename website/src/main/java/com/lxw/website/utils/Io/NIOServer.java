package com.lxw.website.utils.Io;

import com.lxw.website.thread.ThreadPoolConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

/**
 * @author LXW
 * @date 2021年06月17日 14:20
 * 1、创建一个ServerSocketChannel和Selector，然后将ServerSocketChannel注册到Selector上
 * 2、Selector通过select方法去轮询监听channel事件，如果有客户端要连接时，监听到连接事件。
 * 3、通过channel方法将socketchannel绑定到ServerSocketChannel上，绑定通过SelectorKey实现。
 * 4、socketchannel注册到Selector上，关心读事件。
 * 5、Selector通过select方法去轮询监听channel事件，当监听到有读事件时，ServerSocketChannel通过绑定的SelectorKey定位到具体的channel，读取里面的数据
 */
@Slf4j
//@Component
//@WebListener
public class NIOServer implements ServletContextListener {

    @Value("${TCP.serverPort}")
    private int serverPort;
    @Value("${TCP.ip}")
    private String tcpIp;

    @Autowired
    private ThreadPoolConfig threadPoolConfig;

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        log.info("----NIO异步初始---");
        threadPoolConfig.asyncThreadExecutor().execute(()->{
            sedMsg();
        });
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        log.info("----关闭监听---");
    }


    private void sedMsg(){
        try{
            // 创建通道ServerSocketChannel
            ServerSocketChannel serverSocketChannel=ServerSocketChannel.open();
            // 将通道设置为非阻塞
            serverSocketChannel.configureBlocking(false);
            // 将ServerSocketChannel对应的ServerSocket绑定到指定端口(port)
            serverSocketChannel.socket().bind(new InetSocketAddress(serverPort));

            // 创建通道管理器(Selector)
            Selector selector=Selector.open();
            //将通道(Channel)注册到通道管理器(Selector)，并为该通道注册selectionKey.OP_ACCEPT事件
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

            while(true){
                log.info("----BIO等待连接---");
                /**
                 * 注册该事件后，当事件到达的时候，selector.select()会返回，
                 * 如果事件没有到达selector.select()会一直阻塞。
                 */
                // 当注册事件到达时，方法返回，否则该方法会一直阻塞
                selector.select();
                // 获取监听事件
                Iterator<SelectionKey> selectionKeyIterator=selector.selectedKeys().iterator();

                while(selectionKeyIterator.hasNext()){
                    // 获取事件
                    SelectionKey key=selectionKeyIterator.next();
                    // 移除事件，避免重复处理
                    selectionKeyIterator.remove();

                    handle(key);
                }
            }

        }catch (Exception e){
            log.info("----BIO服务端异常："+e.getMessage());
        }

    }

    private void handle(SelectionKey key) throws IOException {
        if(key.isAcceptable()){
            ServerSocketChannel serverSocketChannel= (ServerSocketChannel) key.channel();
            //创建客户端一侧的channel，并注册到selector上
            SocketChannel socketChannel=serverSocketChannel.accept();
            log.info("----BIO客户端连接发生---");
            // 获取客户端连接通道
            socketChannel.configureBlocking(false);
            // 给通道设置读事件，客户端监听到读事件后，进行读取操作
            socketChannel.register(key.selector(),SelectionKey.OP_READ);
        }
        if(key.isReadable()){
            SocketChannel socketChannel=(SocketChannel) key.channel();
            log.info("----BIO客户端数据发生---");
            // 从通道读取数据到缓冲区
           ByteBuffer byteBuffer=ByteBuffer.allocate(1024);
           int len=socketChannel.read(byteBuffer);
           if(len!=-1){
               // 输出客户端发送过来的消息
               log.info("----BIO服务端接受数据："+new String(byteBuffer.array(),0,len));
           }
            //给客户端发送信息
            ByteBuffer wrap = ByteBuffer.wrap("Server get Message!".getBytes());
            socketChannel.write(wrap);
            key.interestOps(SelectionKey.OP_READ|SelectionKey.OP_WRITE);
            socketChannel.close();
        }

    }

}
