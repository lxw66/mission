package com.lxw.website.utils.Io;

import com.lxw.website.thread.ThreadPoolConfig;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.io.*;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author LXW
 * @date 2021年06月17日 14:20
 */
@Slf4j
//@WebListener
//@Configuration
public class BIOServer implements ServletContextListener {

    @Value("${TCP.serverPort}")
    private int serverPort;
    @Value("${TCP.ip}")
    private String tcpIp;

    @Autowired
    private ThreadPoolConfig threadPoolConfig;

    private static  Socket socket=null;

    /**
     *   上下文初始化
     * @author LXW
     * @date 2021/6/17 17:11
     * @param sce 
     */
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        log.info("----异步初始---");
        threadPoolConfig.asyncThreadExecutor().execute(()->{
            this.sedMsg();
        });
    }

    /**'
     * 上写文销毁
     * @param sce
     */
    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        log.info("----关闭监听---");
    }

    private void sedMsg(){
        try {
            ServerSocket serverSocket=new ServerSocket();
            serverSocket.bind(new InetSocketAddress(serverPort));
            while (true){
                log.info("----等待连接---");
                socket=serverSocket.accept();
                log.info("----连接成功---");
                new Thread(new Runnable() {
                    @SneakyThrows
                    @Override
                    public void run() {
                       InputStream inputStream=socket.getInputStream();
                       InputStreamReader inputStreamReader=new InputStreamReader(inputStream);
                       BufferedReader bufferedReader=new BufferedReader(inputStreamReader);
                       String msg;
                       while((msg=bufferedReader.readLine())!=null){
                           log.info("----------接受信息---"+msg);
                       }
                       socket.shutdownInput();
                       OutputStream outputStream=socket.getOutputStream();
                       PrintWriter printWriter=new PrintWriter(outputStream);
                        printWriter.write("--------service get----");
                        printWriter.flush();

                        printWriter.close();
                        outputStream.close();
                        bufferedReader.close();
                        inputStreamReader.close();
                        inputStream.close();

                    }
                }).start();
            }
        }catch (Exception e){
            log.info("----连接失败---");
        }finally {
            log.info("----接受信息结束---");
        }

    }
}
