package com.lxw.website.utils.TCPUDP;

import com.lxw.website.thread.ThreadPoolConfig;
import javafx.application.Application;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.core.ApplicationContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.Executor;

/**
 * @author LXW
 * @date 2021年04月29日 9:46
 */

@Component
@WebListener   //监听注解
@Slf4j
public class udpServiceListener implements ServletContextListener {

    @Value("${UDP.serverPort}")
    private int udpServicePort;
    @Value("${UDP.ip}")
    private String updIp;
    @Value("${UDP.max_udp_data_size}")
    private int maxDateSize;

    @Autowired
    ThreadPoolConfig threadPoolConfig;


    @Override
    public  void contextInitialized(ServletContextEvent sce) {
        /*log.info("==========启动线程监听UPD=========ip："+udpServicePort+"===port:"+updIp+"======");
        threadPoolConfig.asyncThreadExecutor().execute(()->{
            //执行监听的任务
            try {
                recentMsg(udpServicePort,updIp);
            } catch (SocketException e) {
                log.info("=====监听异常==="+e.toString());
            }
        });
        */

    }


    private void  recentMsg(int port,String updIp) throws SocketException {
        DatagramSocket datagramSocket=new DatagramSocket(port);
        while(true){
            byte[]  buffer=new byte[maxDateSize];
            DatagramPacket datagramPacket=new DatagramPacket(buffer , buffer.length);
            try{
                //该方法监听一套数据既阻塞线程, while(true)
                datagramSocket.receive(datagramPacket);
                //获取UDP信息
                buffer=datagramPacket.getData();
                String str=new String(buffer, StandardCharsets.UTF_8).trim();
                log.info("==========监听"+updIp+"：信息--"+str);
            }catch (IOException e){
                log.info("=====监听异常==="+e.toString());
            }
        }

    }

    @Override
    public  void contextDestroyed(ServletContextEvent sce) {
        log.info("=====关闭监听===");
    }
}
