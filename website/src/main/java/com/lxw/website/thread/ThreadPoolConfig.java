package com.lxw.website.thread;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author LXW
 * @date 2021年04月29日 9:55
 */

@Configuration
@EnableAsync
@Slf4j
public class ThreadPoolConfig {

    @Value("${THREAD.pool-size}")
    private int poolSize;
    @Value("${THREAD.max-pool-size}")
    private  int maxPoolSize;
    @Value("${THREAD.queueCapacity}")
    private int queueCapacity;
    @Value("${THREAD.keepAliveSeconds}")
    private int keepAliveSeconds;
    @Value("${THREAD.prefix}")
    private String prefix;


    @Bean
    public Executor asyncThreadExecutor(){
        log.info("---start asyncThreadExecutor!-------");
        ViewThreadPoolTaskExector threadPoolTaskExecutor=new ViewThreadPoolTaskExector();
        threadPoolTaskExecutor.setMaxPoolSize(poolSize);
        threadPoolTaskExecutor.setMaxPoolSize(maxPoolSize);
        threadPoolTaskExecutor.setQueueCapacity(queueCapacity);
        threadPoolTaskExecutor.setKeepAliveSeconds(keepAliveSeconds);
        //// RejectedExecutionHandler：当pool已经达到max size的时候，如何处理新任务
       // 1. CallerRunsPolicy ：这个策略重试添加当前的任务，他会自动重复调用 execute() 方法，直到成功。
       // 2. AbortPolicy ：对拒绝任务抛弃处理，并且抛出异常。
        //3. DiscardPolicy ：对拒绝任务直接无声抛弃，没有异常信息。
        //4. DiscardOldestPolicy ：对拒绝任务不抛弃，而是抛弃队列里面等待最久的一个线程，然后把拒绝任务加到队列。
        threadPoolTaskExecutor.setRejectedExecutionHandler(new ThreadPoolExecutor.AbortPolicy());
        threadPoolTaskExecutor.setThreadNamePrefix(prefix);
        threadPoolTaskExecutor.initialize();  //初始化
        return threadPoolTaskExecutor;
    }
}
