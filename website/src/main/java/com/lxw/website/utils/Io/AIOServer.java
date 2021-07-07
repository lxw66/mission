package com.lxw.website.utils.Io;

import com.lxw.website.thread.ThreadPoolConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;

/**
 * @author LXW
 * @date 2021年06月17日 14:20
 */
@Component
@WebListener
@Slf4j
public class AIOServer implements ServletContextListener {
    @Value("${TCP.serverPort}")
    private int serverPort;
    @Value("${TCP.ip}")
    private String tcpIp;
    @Autowired
    ThreadPoolConfig threadPoolConfig;
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        log.info("---异步开始");
        threadPoolConfig.asyncThreadExecutor().execute(()->{
            sendMsg();
        });
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        log.info("----------关闭AIOServer");
    }

    public void sendMsg(){
        try{
            //创建异步通道
            AsynchronousServerSocketChannel  asynchronousSocketChannel=AsynchronousServerSocketChannel.open();
            asynchronousSocketChannel.bind(new InetSocketAddress(serverPort));
            log.info("--------监听："+serverPort+"---等待客户端连接");
            //在AIO中，accept有两个参数，
            // 第一个参数是一个泛型，可以用来控制想传递的对象
            // 第二个参数CompletionHandler，用来处理监听成功和失败的逻辑
            //  如此设置监听的原因是因为这里的监听是一个类似于递归的操作，每次监听成功后要开启下一个监听
            asynchronousSocketChannel.accept(null, new CompletionHandler<AsynchronousSocketChannel, Object>() {

                @Override
                public void completed(AsynchronousSocketChannel result, Object attachment) {
                    log.info("--------客户端连接成功，处理数据！");
                    //开启下一个监听
                    asynchronousSocketChannel.accept(null,this);
                    handleMsg(result,attachment);
                }

                @Override
                public void failed(Throwable exc, Object attachment) {
                    log.info("--------服务端读取数据失败");
                }
            });
        }catch (Exception e){
            log.info("--------监听："+serverPort+"---等待客户端连接--fails");
        }

    }

    public static void handleMsg(AsynchronousSocketChannel result,Object attachment){
        ByteBuffer byteBuffer=ByteBuffer.allocate(1024);
        //通道的read方法也带有三个参数
        //1.目的地：处理客户端传递数据的中转缓存，可以不使用
        //2.处理客户端传递数据的对象
        //3.处理逻辑，也有成功和不成功的两个写法
        result.read(byteBuffer, byteBuffer, new CompletionHandler<Integer, ByteBuffer>() {

            @Override
            public void completed(Integer result, ByteBuffer attachment) {
                    if(result>0){
                        //调换这个buffer的当前位置，并且设置当前位置是0。说的意思就是：将缓存字节数组的指针设置为数组的开始序列即数组下标0。这样就可以从buffer开头，对该buffer进行遍历（读取）了
                        //调用flip()之后，读/写指针position指到缓冲区头部，并且设置了最多只能读出之前写入的数据长度(而不是整个缓存的容量大小)
                        attachment.flip();
                        byte[] bytes=attachment.array();
                        log.info("-----服务端接受数据："+new String(bytes));
                    }
            }

            @Override
            public void failed(Throwable exc, ByteBuffer attachment) {
                log.info("--------服务端读取数据失败");
            }
        });
    }
}
