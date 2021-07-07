package com.lxw.website.rabbitmq;

import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author LXW
 * @date 2021年06月29日 14:05
 */
@Component
//@RabbitListener(queues = {RabbitQuExConfig.Rabbit_QUENE_TEST})   该方法也行
//使用queuesToDeclare属性，如果不存在则会创建队列
@RabbitListener(queuesToDeclare = @Queue(RabbitQuExConfig.Rabbit_QUENE_TEST))
public class RabbitCumber {

    @RabbitHandler
    public void getDate(Map map){
        System.out.println("--------"+map.toString());
    }
}
