package com.lxw.website.utils.TCPUDP;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.*;
import java.net.DatagramSocket;
import java.net.Socket;

/**
 * @author LXW
 * @date 2021年04月29日 16:43
 */
@Component
@Slf4j
public class TcpClient {

    @Value("${TCP.serverPort}")
    private int serverPort;
    @Value("${TCP.ip}")
    private String tcpIp;
    private Socket socket;

    public void sendTcpMessage(String msg) throws IOException {
        /*try{
            socket=new Socket(tcpIp, serverPort);
            OutputStream outputStream=socket.getOutputStream();
            PrintWriter printWriter=new PrintWriter(outputStream);
            printWriter.write(msg);
            printWriter.flush();
            socket.shutdownOutput();

            InputStream inputStream=socket.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
            String info = null;
            while ((info = br.readLine()) != null) {
                log.info("Hello,我是客户端，服务器说：" + info);
            }
            br.close();
            inputStream.close();
            printWriter.close();
            outputStream.close();
            socket.close();
        }catch (IOException e){
            log.info("===========TcpClinet  fail=====");
        }
*/

    }

}
