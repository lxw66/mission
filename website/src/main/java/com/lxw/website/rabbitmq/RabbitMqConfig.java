package com.lxw.website.rabbitmq;

import com.rabbitmq.client.ConnectionFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.ReturnedMessage;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author LXW
 * @date 2021年06月22日 13:58
 */
@Configuration
@Slf4j
public class RabbitMqConfig {

    @Value("${spring.rabbitmq.host}")
    private String host;
    @Value("${spring.rabbitmq.port}")
    private int port;
    @Value("${spring.rabbitmq.username}")
    private String username;
    @Value("${spring.rabbitmq.password}")
    private String password;
    @Value("${spring.rabbitmq.virtual-host}")
    private String virtualHhost;
    @Value("${spring.rabbitmq.publisher-returns}")
    private boolean queueTrue;
    @Value("${spring.rabbitmq.publisher-confirm-type}")
    private CachingConnectionFactory.ConfirmType exchangeType;

    //该处可以不用配置  RabbitAutoConfiguration
    @Bean
    public CachingConnectionFactory connectionFactory(){
        CachingConnectionFactory cachingConnectionFactory=new CachingConnectionFactory(host,port);
        cachingConnectionFactory.setUsername(username);
        cachingConnectionFactory.setPassword(password);
        cachingConnectionFactory.setVirtualHost(virtualHhost);
        cachingConnectionFactory.setPublisherConfirmType(exchangeType);
        cachingConnectionFactory.setPublisherReturns(queueTrue);
        return cachingConnectionFactory;
    }

    @Bean
    public RabbitTemplate  createRabbitTemple(@Qualifier("connectionFactory") CachingConnectionFactory cachingConnectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(cachingConnectionFactory);
        //设置开启Mandatory,才能触发回调函数,无论消息推送结果怎么样都强制调用回调函数
        rabbitTemplate.setMandatory(true);
        rabbitTemplate.setConfirmCallback(new RabbitTemplate.ConfirmCallback() {
            @Override
            public void confirm(CorrelationData correlationData, boolean b, String s) {
                log.info("ConfirmCallback:****" + "相关数据：" + correlationData);
                log.info("ConfirmCallback:****" + "确认情况：" + b);
                log.info("ConfirmCallback:****" + "原因：" + s);
            }
        });

        rabbitTemplate.setReturnsCallback(new RabbitTemplate.ReturnsCallback() {
            @Override
            public void returnedMessage(ReturnedMessage returnedMessage) {
                log.info("ReturnCallback:-----"+"消息："+returnedMessage.getMessage());
                log.info("ReturnCallback:-----"+"回应码："+returnedMessage.getReplyCode());
                log.info("ReturnCallback:-----"+"回应信息："+returnedMessage.getReplyText());
                log.info("ReturnCallback:-----"+"交换机："+returnedMessage.getExchange());
                log.info("ReturnCallback:-----"+"路由键："+returnedMessage.getRoutingKey());
            }
        });
        return rabbitTemplate;
    }
}
