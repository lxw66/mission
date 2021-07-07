package com.lxw.website.rabbitmq;

import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author LXW
 * @date 2021年06月30日 9:47
 */
@Component
@RabbitListener(queuesToDeclare = @Queue(RabbitQuExConfig.Rabbit_QUENE_TESTB))
public class RabbitCumberFanOutB {

    @RabbitHandler
    public void getASDate(Map map){
        System.out.println("---------"+RabbitQuExConfig.Rabbit_QUENE_TESTB+"--------"+map.toString());
    }
}
