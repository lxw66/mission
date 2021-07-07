package com.lxw.website.controller;

import com.lxw.website.annotation.value.ValueTest;
import com.lxw.website.utils.Io.AIOClient;
import com.lxw.website.utils.Io.BIOClient;
import com.lxw.website.utils.Io.NIOClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author LXW
 * @date 2021年06月17日 9:54
 */
@RestController
@RequestMapping("/lxw1")
@Slf4j
public class Test617Controller {

    @Autowired
    private ValueTest valueTest;

    @Autowired
    private BIOClient bioClient;

    @Autowired
    private NIOClient nioClient;

    @Autowired
    private AIOClient aioClient;

    //@Scheduled(cron = "*/5 * * * * ?")
    public void valueTest(){
        log.info("1---------------"+valueTest.getComment_no());
    }


    @PostMapping("/bioSendMsg")
    public  void BioSendMsg(){
        bioClient.sendMsg("这样就实现了一个BIO,但是BIO的缺点实在太明显了，因此在JDK1.4的时候，NIO出现了");
    }


    @PostMapping("/NioSendMsg")
    public  void NioSendMsg(){
        nioClient.NIOSendMsg("这样就实现了一个BIO,但是BIO的缺点实在太明显了，因此在JDK1.4的时候，NIO出现了");
    }


    @PostMapping("/AioSendMsg")
    public  void AioSendMsg(){
        aioClient.sendMsg("这样就实现了一个BIO,但是BIO的缺点实在太明显了，因此在JDK1.4的时候，NIO出现了");
    }
}
