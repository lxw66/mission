package com.lxw.website.controller;

import com.lxw.website.rabbitmq.RabbitQuExConfig;
import com.lxw.website.utils.SpringUtil;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageDeliveryMode;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @author LXW
 * @date 2021年06月22日 15:30
 */
@RestController
@RequestMapping("/rabbitController")
public class TestRabbitMqController {



    @PostMapping("/testTabbit")
    public void testTabbit(){
        RabbitTemplate rabbitTemplate= (RabbitTemplate) SpringUtil.getBean("createRabbitTemple");
        Map map = new HashMap();
        map.put("name","lxw");
        map.put("age","18");
        map.put("sex","男");
        //将消息携带绑定键值：消息通过  队列和交换机的key  传送到交换机上
        rabbitTemplate.convertAndSend(RabbitQuExConfig.RABBIT_EXCHANGE_TEST,RabbitQuExConfig.RABBIT_ROUTINGKEY_TEST,map);
    }


    @PostMapping("/testTabbitA")
    public void testTabbitA(){
        RabbitTemplate rabbitTemplate= (RabbitTemplate) SpringUtil.getBean("createRabbitTemple");
        Map map = new HashMap();
        map.put("name","lxw");
        map.put("age","18");
        map.put("sex","男");
        //将消息携带绑定键值：消息通过  队列和交换机的key  传送到交换机上
        rabbitTemplate.convertAndSend(RabbitQuExConfig.RABBIT_EXCHANGE_TEST1,RabbitQuExConfig.RABBIT_ROUTINGKEY_TEST1,map);
    }

    @PostMapping("/testTabbitTopic")
    public void testTabbitTopic(@RequestParam(name = "key") String key){
        RabbitTemplate rabbitTemplate= (RabbitTemplate) SpringUtil.getBean("createRabbitTemple");
        Map map = new HashMap();
        map.put("name","lxw");
        map.put("age","18");
        map.put("sex","男");
        //将消息携带绑定键值：消息通过  队列和交换机的key  传送到交换机上
        rabbitTemplate.convertAndSend(RabbitQuExConfig.RABBIT_EXCHANGE_TOPIC_A,key,map);
    }

    @PostMapping("/testTabbitHeader")
    public void testTabbitHeader(){
        RabbitTemplate rabbitTemplate= (RabbitTemplate) SpringUtil.getBean("createRabbitTemple");
        Map map = new HashMap();
        map.put("key_one","name");
        //将消息携带绑定键值：消息通过  队列和交换机的key  传送到交换机上
        MessageProperties ma = new MessageProperties();
        ma.setReceivedDeliveryMode(MessageDeliveryMode.PERSISTENT);
        ma.setContentType("UTF-8");
        ma.getHeaders().putAll(map);
        Message message = new Message(map.toString().getBytes(), ma);
        rabbitTemplate.convertAndSend(RabbitQuExConfig.HEAD_EXCHANGE,null,message);
    }
}
