package com.lxw.website.utils.TCPUDP;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.*;

/**
 * @author LXW
 * @date 2021年04月29日 13:07
 */
@Component
@Slf4j
public class udpClient {

    @Value("${UDP.clientPort}")
    private int udpClientPort;
    @Value("${UDP.serverPort}")
    private int serverPort;
    @Value("${UDP.ip}")
    private String udpIp;
    private  DatagramSocket datagramSocket;

    public void sendMassage(String msg)  {

       /*try{
           log.info("==========UDP客户发送关闭======");
            datagramSocket=new DatagramSocket(udpClientPort);
           byte[]  msgbuff=msg.getBytes();
           //InetAddress.getByName(udpIp)  通过hosts  获取ip
           DatagramPacket datagramPacket=new DatagramPacket(msgbuff, msgbuff.length, InetAddress.getByName(udpIp), serverPort);
           datagramSocket.send(datagramPacket);
       }catch (SocketException | UnknownHostException e){
           log.info("==========UDP客户发送失败======"+e.toString());
       } catch (IOException e) {
           log.info("==========UDP客户发送失败======"+e.toString());
       }finally {
           log.info("==========UDP客户发送关闭======");
           datagramSocket.close();
       }*/

    }
}
