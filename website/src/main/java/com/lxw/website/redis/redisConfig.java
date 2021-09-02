package com.lxw.website.redis;

import com.alibaba.fastjson.support.spring.FastJsonRedisSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisPassword;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisClientConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import redis.clients.jedis.JedisPoolConfig;

/**
 * @author mission
 * @version 1.0
 * @description: TODO
 * @date 2021/8/24 16:43
 */
@Configuration
public class redisConfig {
    @Value("${spring.redis.host}")
    private String host;
    @Value("${spring.redis.port}")
    private int  port;
    @Value("${spring.redis.password}")
    private String  password;
    @Value("${spring.redis.database}")
    private int database;
    @Value("${spring.redis.jedis.pool.max-wait}")
    private long maxWait;
    @Value("${spring.redis.jedis.pool.max-active}")
    private int maxActive;
    @Value("${spring.redis.jedis.pool.max-idle}")
    private int maxIdle;

    @Bean
    public JedisPoolConfig getJedisPoolConfig(){
        JedisPoolConfig jedisPoolConfig=new JedisPoolConfig();
        //最大空闲数
        jedisPoolConfig.setMaxIdle(maxIdle);
        //连接池的最大数据库连接数。设为0表示无限制,如果是jedis 2.4以后用redis.maxTotal
        jedisPoolConfig.setMaxTotal(maxActive);
        //最大建立连接等待时间。如果超过此时间将接到异常。设为-1表示无限制
        jedisPoolConfig.setMaxWaitMillis(maxWait);
        // 逐出连接的最小空闲时间 默认1800000毫秒(30分钟)
        jedisPoolConfig.setMinEvictableIdleTimeMillis(300000);
        // 每次逐出检查时 逐出的最大数目 如果为负数就是 : 1/abs(n), 默认3
        jedisPoolConfig.setNumTestsPerEvictionRun(3);
        // 逐出扫描的时间间隔(毫秒) 如果为负数,则不运行逐出线程, 默认-1
        jedisPoolConfig.setTimeBetweenEvictionRunsMillis(30000);
        // 是否在从池中取出连接前进行检验,如果检验失败,则从池中去除连接并尝试取出另一个
        jedisPoolConfig.setTestOnBorrow(true);
        // 在空闲时检查有效性, 默认false
        jedisPoolConfig.setTestWhileIdle(true);
        return jedisPoolConfig;

    }


    @Bean
    public JedisConnectionFactory getDefultJedisConnectionFactory(){
        return getJedisConnectionFactory(database,host,port,password);
    }


    private JedisConnectionFactory getJedisConnectionFactory(int database,String host,int port,String password){
        //设置redis服务器的host或者ip地址
        RedisStandaloneConfiguration redisStandaloneConfiguration=new RedisStandaloneConfiguration(host, port);
        redisStandaloneConfiguration.setDatabase(database);
        redisStandaloneConfiguration.setPassword(RedisPassword.of(password));
        //获得默认的连接池构造
        //这里需要注意的是，JedisConnectionFactory对于Standalone模式的没有（RedisStandaloneConfiguration，JedisPoolConfig）的构造函数，对此
        //我们用JedisClientConfiguration接口的builder方法实例化一个构造器，还得类型转换
       /* JedisClientConfiguration.JedisPoolingClientConfigurationBuilder jedisPoolingClientConfigurationBuilder= (JedisClientConfiguration.JedisPoolingClientConfigurationBuilder) JedisClientConfiguration.builder();
        jedisPoolingClientConfigurationBuilder.poolConfig(getJedisPoolConfig());
        JedisClientConfiguration jedisClientConfiguration=jedisPoolingClientConfigurationBuilder.build();
        return new JedisConnectionFactory(redisStandaloneConfiguration,jedisClientConfiguration);
        */
        return new JedisConnectionFactory(redisStandaloneConfiguration);
    }

  private void initRedisTemplate(RedisTemplate<String,Object> redisTemplate,JedisConnectionFactory jedisConnectionFactory){
      //如果不配置Serializer，那么存储的时候缺省使用String，如果用User类型存储，那么会提示错误User can't cast to String！
      redisTemplate.setKeySerializer(new StringRedisSerializer());
      redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer());
      redisTemplate.setHashKeySerializer(new StringRedisSerializer());
      redisTemplate.setHashValueSerializer(new GenericJackson2JsonRedisSerializer());
        // 开启事务
      redisTemplate.setEnableTransactionSupport(true);
      redisTemplate.setConnectionFactory(jedisConnectionFactory);
  }

    /**
     * 实例化  RedisTemplate
     * @param jedisConnectionFactory
     * @return
     */
  @Bean
  public RedisTemplate<String, Object> functionDomainRedisTemplate(JedisConnectionFactory jedisConnectionFactory){
      RedisTemplate<String, Object> redisTemplate=new RedisTemplate<>();
      initRedisTemplate(redisTemplate,jedisConnectionFactory);
      return redisTemplate;
  }

    @Bean
     public redisUtil getRedisUtil(RedisTemplate<String, Object> redisTemplate){
         redisUtil redisUtil=new redisUtil();
         redisUtil.setRedisTemplate(redisTemplate);
         return redisUtil;
     }
}
