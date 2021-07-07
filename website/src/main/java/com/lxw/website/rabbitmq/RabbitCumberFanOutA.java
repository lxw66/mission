package com.lxw.website.rabbitmq;

import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author LXW
 * @date 2021年06月30日 9:46
 */
@Component
@RabbitListener(queuesToDeclare = @Queue(RabbitQuExConfig.Rabbit_QUENE_TESTA))
public class RabbitCumberFanOutA {

    @RabbitHandler
    public void getASADate(Map map){
        System.out.println("---------"+RabbitQuExConfig.Rabbit_QUENE_TESTA+"--------"+map.toString());
    }
}
