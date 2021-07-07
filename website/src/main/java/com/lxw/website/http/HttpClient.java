package com.lxw.website.http;

import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author LXW
 * @date 2021年04月25日 10:06
 */
@Configuration
public class HttpClient {


    @Value("${http.maxTotal}")
    private Integer maxTotal;//最大连接
    @Value("${http.defaultMaxPerRoute}")
    private Integer defaultMaxPerRoute;//默认连接
    @Value("${http.connectTimeout}")
    private Integer connectTimeout;//连接最大时长
    @Value("${http.connectionRequestTimeout}")
    private Integer connectionRequestTimeout; //连接超时
    @Value("${http.socketTimeout}")
    private Integer socketTimeout; //数据传输的最长时间
    @Value("${http.staleConnectionCheckEnabled}")
    private  Boolean staleConnectionCheckEnabled; //提交请求前测试连接是否可用

    /**
     * 首先实例化一个连接池管理器，设置最大连接数、并发连接数
     * @author LXW
     * @date 2021/4/25 10:22
     * @return org.apache.http.impl.conn.PoolingHttpClientConnectionManager
     */
    @Bean("httpClientConnectionManager")
    public PoolingHttpClientConnectionManager getHttpClientConnectionManager(){
        PoolingHttpClientConnectionManager httpClientConnectionManager=new PoolingHttpClientConnectionManager();
        httpClientConnectionManager.setMaxTotal(maxTotal);
        httpClientConnectionManager.setDefaultMaxPerRoute(defaultMaxPerRoute);
        return  httpClientConnectionManager;

    }

    /**
     * 实例化连接池，设置连接池管理器。
     * 这里需要以参数形式注入上面实例化的连接池管理器
     * @author LXW
     * @date 2021/4/25 10:23
     * @param HttpClientConnectionManager 
     * @return org.apache.http.impl.client.HttpClientBuilder
     */
    @Bean("httpClientBuilder")
    public HttpClientBuilder getHttpClientBuilder(@Qualifier("httpClientConnectionManager")PoolingHttpClientConnectionManager HttpClientConnectionManager){
        HttpClientBuilder httpClientBuilder=HttpClientBuilder.create();
        httpClientBuilder.setConnectionManager(HttpClientConnectionManager);
        return httpClientBuilder;
    }
        /**
         * 注入连接池，用于获取httpClient
         * @author LXW
         * @date 2021/4/25 10:26
         * @param httpClientBuilder
         * @return org.apache.http.impl.client.CloseableHttpClient
         */
     @Bean("getCloseAbleHttpClientBuilder")
     public CloseableHttpClient getCloseAbleHttpClientBuilder(@Qualifier("httpClientBuilder")HttpClientBuilder httpClientBuilder){
            return httpClientBuilder.build();
     }



     //==============配置===============

     /**
      * Builder是RequestConfig的一个内部类
      *      * 通过RequestConfig的custom方法来获取到一个Builder对象
      *      * 设置builder的连接信息
      *      * 这里还可以设置proxy，cookieSpec等属性。有需要的话可以在此设置
      * @author LXW
      * @date 2021/4/25 10:32
      * @return org.apache.http.client.config.RequestConfig.Builder
      */
    @Bean("requestConfig")
    public RequestConfig.Builder buildRequestConfig(){
        RequestConfig.Builder builder=RequestConfig.custom();
        builder.setSocketTimeout(socketTimeout)
                .setConnectionRequestTimeout(connectionRequestTimeout)
                .setStaleConnectionCheckEnabled(staleConnectionCheckEnabled).setConnectTimeout(connectTimeout);
        return builder;
    }

    /**
     * 使用builder构建一个RequestConfig对象
     * @author LXW
     * @date 2021/4/25 10:34
     * @param builder 
     * @return org.apache.http.client.config.RequestConfig
     */
    @Bean("getRequestConfig")
    public RequestConfig getRequestConfig(@Qualifier("requestConfig")RequestConfig.Builder builder){
        return builder.build();
    }

}
