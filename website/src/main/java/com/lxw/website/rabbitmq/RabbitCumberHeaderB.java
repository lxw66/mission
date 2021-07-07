package com.lxw.website.rabbitmq;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;

/**
 * @author LXW
 * @date 2021年07月01日 13:40
 */
@Component
public class RabbitCumberHeaderB {

    @RabbitListener(queuesToDeclare = @Queue(RabbitQuExConfig.HEAD_QUENE_B))
    public void getASDate(Message map) throws UnsupportedEncodingException {
        MessageProperties messageProperties=map.getMessageProperties();
        String Content = messageProperties.getContentType();
        System.out.println("---------"+RabbitQuExConfig.Rabbit_QUENE_TESTB+"--------"+new String(map.getBody(),Content));
    }
}
