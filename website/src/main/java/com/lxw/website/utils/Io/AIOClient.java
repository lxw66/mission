package com.lxw.website.utils.Io;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;

/**
 * @author LXW
 * @date 2021年06月22日 10:49
 */
@Component
public class AIOClient {
    @Value("${TCP.serverPort}")
    private int serverPort;
    @Value("${TCP.ip}")
    private String tcpIp;

    public void sendMsg(String msg){
        try{
            AsynchronousSocketChannel asynchronousSocketChannel=AsynchronousSocketChannel.open();
            asynchronousSocketChannel.connect(new InetSocketAddress(tcpIp,serverPort));
            ByteBuffer byteBuffer=ByteBuffer.allocate(1024);
            byteBuffer.put(msg.getBytes());
            byteBuffer.flip();
            asynchronousSocketChannel.write(byteBuffer);

        }catch (Exception e){

        }
    }
}
