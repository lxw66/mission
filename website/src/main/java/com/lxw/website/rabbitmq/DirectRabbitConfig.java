package com.lxw.website.rabbitmq;



import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;


/**
 * @author LXW
 * @date 2021年06月22日 17:13
 *实现BeanPostProcessor类，使用Bean的生命周期函数   postProcessAfterInitialization
 */
@Configuration
public class DirectRabbitConfig implements BeanPostProcessor {

    @Resource
    private RabbitAdmin rabbitAdmin;

//----------------------------start   Direct Exchange----------------------
//一个交换机需要绑定一个队列，要求该消息与一个特定的路由键完全匹配。简单点说就是一对一的，点对点的发送。
   /*
    @Bean
    public Queue testDirctQueue(){
        // durable:是否持久化,默认是false,持久化队列：会被存储在磁盘上，当消息代理重启时仍然存在，暂存队列：当前连接有效
        // exclusive:默认也是false，只能被当前创建的连接使用，而且当连接关闭后队列即被删除。此参考优先级高于durable
        // autoDelete:是否自动删除，当没有生产者或者消费者使用此队列，该队列会自动删除。
        //   return new Queue("TestDirectQueue",true,true,false);

        //一般设置一下队列的持久化就好,其余两个就是默认false
        return new Queue(RabbitQuExConfig.Rabbit_QUENE_TEST,true,false,false);
    }
    //Direct交换机 起名：TestDirectExchange
    @Bean
    public DirectExchange testDirectExchange(){
        return new DirectExchange(RabbitQuExConfig.RABBIT_EXCHANGE_TEST,true,false);
    }
    @Bean
    Binding bindingDirect(){
        //链式写法，绑定交换机和队列，并设置匹配键
        return BindingBuilder
                .bind(testDirctQueue())  //绑定队列
                .to(testDirectExchange()) //绑定交换机
                .with(RabbitQuExConfig.RABBIT_ROUTINGKEY_TEST);   //设置匹配键名
    }
    */
//----------------------------END   Direct Exchange------------------------
//***********************************************************************************************
//-----------------------------------------START---Fanout exchange----------------
        //队列绑定到交换机上。一个发送到交换机的消息都会被转发到与该交换机绑定的所有队列上
    /*
    @Bean
    public Queue dirctQueueA(){
        return new Queue(RabbitQuExConfig.Rabbit_QUENE_TESTA,true,false,false);
    }

    @Bean
    public Queue dirctQueueB(){
        return  new Queue(RabbitQuExConfig.Rabbit_QUENE_TESTB,true,false,false);
    }

    @Bean
    public FanoutExchange  directExchange(){
        return  new FanoutExchange(RabbitQuExConfig.RABBIT_EXCHANGE_TEST1,true,false);
    }

    @Bean
    Binding bindingDirectA(){
        //链式写法，绑定交换机和队列，并设置匹配键
        return BindingBuilder.bind(dirctQueueA()).to(directExchange()).with(RabbitQuExConfig.RABBIT_ROUTINGKEY_TEST1);
    }

    @Bean
    Binding bindingDirectB(){
        //链式写法，绑定交换机和队列，并设置匹配键
        return BindingBuilder.bind( dirctQueueB()).to(directExchange()).with(RabbitQuExConfig.RABBIT_ROUTINGKEY_TEST1);
    }
     */
//---------------------------------------END-----Fanout exchange-------------------
//**********************************************************************************
//------------------------------------------START --------------Topic Exchange-------------------
    //通配符 匹配的十的设置的ROUTINGKEY    rabbit.*   可以配置rabiit.a,rabiit.ab   无法匹配的rabbit.ab.c    测试 *   配置是的以.为单位的  只匹配单个.后面的一个字符串
    //#  匹配的是所有的  rabbit.   无论后面是什么都能匹配上。
    /*
    @Bean
    public Queue dirctQueueA(){
        return  new Queue(RabbitQuExConfig.Rabbit_QUENE_TOPIC_A,true,false,false);
    }

    @Bean
    public Queue dirctQueueB(){
        return  new Queue(RabbitQuExConfig.Rabbit_QUENE_TOPIC_B,true,false,false);
    }
    //************选择对应的交换机
    @Bean
    public TopicExchange directExchangeA(){
        return new TopicExchange(RabbitQuExConfig.RABBIT_EXCHANGE_TOPIC_A,true,false);
    }

    @Bean
    public Binding bindingA(){
        return BindingBuilder.bind(dirctQueueA()).to(directExchangeA()).with(RabbitQuExConfig.RABBIT_ROUTINGKEY_TOPIC_A);
    }
    @Bean
    public Binding bindingB(){
        return BindingBuilder.bind(dirctQueueB()).to(directExchangeA()).with(RabbitQuExConfig.RABBIT_ROUTINGKEY_TOPIC_B);
    }
    */
//------------------------------------------END ----------------Topic Exchange------------------
//*****************************************************Header change***************************************
//----------------------------------START   HEAD EXCHANGE---------------------------------------------
    @Bean
    public Queue dirctQuene(){
        return new Queue(RabbitQuExConfig.HEAD_QUENE_B,true,false,false);
    }

    @Bean
    public Queue dirctQueneB(){
        return new Queue(RabbitQuExConfig.HEAD_QUENE_A,true,false,false);
    }

    @Bean
    public HeadersExchange headersExchange(){
        return new HeadersExchange(RabbitQuExConfig.HEAD_EXCHANGE,true,false);
    }

    @Bean
    public  Binding bingQueneB(){
        Map map = new HashMap();
        map.put("key_one","name");
        map.put("key_two","age");
        return BindingBuilder.bind(dirctQuene()).to(headersExchange()).whereAll(map).match();
    }

    @Bean
    public  Binding bingQueneA(){
        Map map = new HashMap();
        map.put("key_one","name");
        return BindingBuilder.bind(dirctQueneB()).to(headersExchange()).whereAny(map).match();
    }
//-------------------------------END    HEAD EXCHANGE------------------------------------------
    @Bean
    public RabbitAdmin rabbitAdmin(@Qualifier("connectionFactory") CachingConnectionFactory connectionFactory) {
        RabbitAdmin rabbitAdmin = new RabbitAdmin(connectionFactory);
        // 只有设置为 true，spring 才会加载 RabbitAdmin 这个类
        rabbitAdmin.setAutoStartup(true);
        return rabbitAdmin;
    }
    //实现BeanPostProcessor   在Bean初始化后  完成队列  和交换机的建立
    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        rabbitAdmin.declareQueue(dirctQuene());
        rabbitAdmin.declareQueue(dirctQueneB());
        rabbitAdmin.declareExchange(headersExchange());
        rabbitAdmin.declareBinding(bingQueneB());
        rabbitAdmin.declareBinding(bingQueneA());
        return null;
    }


}
