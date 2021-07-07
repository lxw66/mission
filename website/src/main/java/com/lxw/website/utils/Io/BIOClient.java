package com.lxw.website.utils.Io;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.net.Socket;

/**
 * @author LXW
 * @date 2021年06月18日 9:40
 */
@Component
public class BIOClient {

        @Value("${TCP.serverPort}")
        private int serverPort;
        @Value("${TCP.ip}")
        private String tcpIp;

        public void sendMsg(String msg){
            try {
                Socket socket=new Socket(tcpIp,serverPort);
                socket.getOutputStream().write(msg.getBytes());
                socket.close();
            }catch(Exception e) {
                e.printStackTrace();
            }

        }
}
