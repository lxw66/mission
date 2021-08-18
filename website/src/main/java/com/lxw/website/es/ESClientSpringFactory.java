package com.lxw.website.es;

import jdk.nashorn.internal.runtime.logging.Logger;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;

import java.io.IOException;

/**
 * @author mission
 * @version 1.0
 * @description: TODO
 * @date 2021/8/17 10:46
 */
@Slf4j
public class ESClientSpringFactory {

    /**
     * 用户密码
     */
    public static String ES_USERNAME="elastic";
    /**
     * 用户名
     */
    public static String ES_PASSWORD="password";

    /**
     * 连接超时毫秒时间
     */
    public static int CONNECT_TIMEOUT_MILLIS = 1000;
    /**
     * 套接字超时毫秒时间
     */
    public static int SOCKET_TIMEOUT_MILLIS = 30000;
    /**
     * 连接请求超时毫秒时间
     */
    public static int CONNECTION_REQUEST_TIMEOUT_MILLIS = 500;
    /**
     * 最大连接数量
     */
    public static int MAX_CONN_PER_ROUTE = 10;
    /**
     * 连接超时时间
     */
    public static int MAX_CONN_TOTAL = 30;
    private static HttpHost HTTP_HOST;

    private RestClient restClient;
    private RestClientBuilder restClientBuilder;
    private RestHighLevelClient restHighLevelClient;
    private CredentialsProvider credentialsProvider;

    private static ESClientSpringFactory esClientSpringFactory = new ESClientSpringFactory();

    public ESClientSpringFactory() {
    }

    public static ESClientSpringFactory build(String userName,String pasword,HttpHost httpHost,Integer maxConnectNum,Integer maxConnectPerRoute){
        ES_USERNAME=userName;
        ES_PASSWORD=pasword;
        HTTP_HOST=httpHost;
        MAX_CONN_PER_ROUTE=maxConnectPerRoute;
        MAX_CONN_TOTAL=maxConnectNum;
        return  esClientSpringFactory;
    }

    public static ESClientSpringFactory build(String userName,String pasword,HttpHost httpHost,Integer maxConnectNum,Integer maxConnectPerRoute,Integer connectTimeOutMillis,
                                       Integer socketTimeOutMillis,Integer connectionRequestTimeOutMillis){
        ES_USERNAME=userName;
        ES_PASSWORD=pasword;
        HTTP_HOST=httpHost;
        MAX_CONN_PER_ROUTE=maxConnectPerRoute;
        MAX_CONN_TOTAL=maxConnectNum;
        CONNECT_TIMEOUT_MILLIS=connectTimeOutMillis;
        SOCKET_TIMEOUT_MILLIS=socketTimeOutMillis;
        CONNECTION_REQUEST_TIMEOUT_MILLIS=connectionRequestTimeOutMillis;
        return  esClientSpringFactory;
    }

    /**
     * 配置连接时间延时
     */
    public  void setConnectTimeoutConfig(){
        restClientBuilder.setRequestConfigCallback(requestConfigBuilder ->{
            requestConfigBuilder.setConnectTimeout(CONNECT_TIMEOUT_MILLIS);
            requestConfigBuilder.setSocketTimeout(SOCKET_TIMEOUT_MILLIS);
            requestConfigBuilder.setConnectionRequestTimeout(CONNECTION_REQUEST_TIMEOUT_MILLIS);
            return requestConfigBuilder;
        });
    }

    /**
     * 使用异步httpclient时设置并发连接数
     */
    public void setMillisConnectConfig(){
        restClientBuilder.setHttpClientConfigCallback(httpClientBuilder -> {
            httpClientBuilder.setMaxConnTotal(MAX_CONN_TOTAL);
            httpClientBuilder.setMaxConnPerRoute(MAX_CONN_PER_ROUTE);
            //带入密码
            credentialsProvider.setCredentials(AuthScope.ANY,new UsernamePasswordCredentials(ES_USERNAME,ES_PASSWORD));
            httpClientBuilder.setDefaultCredentialsProvider(credentialsProvider);
            return httpClientBuilder;
        });
    }

    private void init(){
        restClientBuilder=RestClient.builder(HTTP_HOST);
        credentialsProvider=new BasicCredentialsProvider();
        setConnectTimeoutConfig();
        setMillisConnectConfig();
        restClient=restClientBuilder.build();
        restHighLevelClient=new RestHighLevelClient(restClientBuilder);
        log.info("---ESClientSpringFactory----init--");
    }

    public RestClient getRestClient() {
        return restClient;
    }

    public RestHighLevelClient getRestHighLevelClient() {
        return restHighLevelClient;
    }

    public void close(){
        if (restClient != null) {
            try {
                restClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        log.info("--ESClientSpringFactory---close client----");
    }
}
