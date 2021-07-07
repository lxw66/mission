package com.lxw.website.utils.TCPUDP;

import com.lxw.website.thread.ThreadPoolConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;

/**
 * @author LXW
 * @date 2021年04月29日 16:22
 */
@Slf4j
@WebListener
@Configuration
public class TcpServiceListener implements ServletContextListener {

    @Value("${TCP.serverPort}")
    private int tcpServicePort;
    @Value("${TCP.ip}")
    private String tcpIp;
    @Value("${TCP.max_udp_data_size}")
    private int maxDateSize;


    @Autowired
    ThreadPoolConfig threadPoolConfig;


    @Override
    public  void contextInitialized(ServletContextEvent sce) {
        /*threadPoolConfig.asyncThreadExecutor().execute(()->{
            try {
                recentMsg(tcpServicePort,tcpIp);
            } catch (IOException e) {
                log.info("=====服务端监听异常==="+e.toString());
            }
        });
         */

    }


    private void  recentMsg(int port,String tcpIp) throws IOException {
        //创建服务端serverSocket
        log.info("=====TCP服务端监听==="+port+"====="+tcpIp);
        Socket socket=null;
        ServerSocket serverSocket=new ServerSocket(tcpServicePort);
        while(true){
            //服务端等待连接，未连接则阻塞
            socket=serverSocket.accept();
            //从socket中获取输入流，读取客户端数据
            InputStream inputStream=socket.getInputStream();
            InputStreamReader inputStreamReader=new InputStreamReader(inputStream);
            BufferedReader bufferedReader=new BufferedReader(inputStreamReader);
            String str;
            while((str=bufferedReader.readLine())!=null){
                log.info("====Service===client:"+str);
            }
            socket.shutdownInput();  //关闭输入流
            //从socket中获取输出流，像客户端写数据
            OutputStream outputStream=socket.getOutputStream();
            PrintWriter printWriter=new PrintWriter(outputStream);
            printWriter.write("====Service:get");
            printWriter.flush();

            printWriter.close();
            outputStream.close();
            bufferedReader.close();
            inputStreamReader.close();
            inputStream.close();
        }


    }

    @Override
    public  void contextDestroyed(ServletContextEvent sce) {
        log.info("=====关闭监听===");
    }


}
