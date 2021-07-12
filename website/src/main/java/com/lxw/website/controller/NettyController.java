package com.lxw.website.controller;

import com.lxw.website.netty.client.NettyClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

/**
 * @author mission
 * @version 1.0
 * @description: TODO
 * @date 2021/7/8 13:15
 */
@RestController
@RequestMapping("/nettyController")
public class NettyController {

    @Autowired
    private NettyClient nettyClient;

    @RequestMapping("/clientSendMsg")
    public void clientSendMsg() throws IOException {
        nettyClient.sendMsg("发送消息给服务端");
    }
}
