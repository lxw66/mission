package com.lxw.website.es;

import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.tomcat.util.http.parser.Host;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

/**
 * @author mission
 * @version 1.0
 * @description: TODO
 * @date 2021/8/2 13:17
 */
@Configuration
public class EsConfig {

    @Value("${elasticsearch.host:127.0.0.1}")
    private String esHost;
    @Value("${elasticsearch.port:9200}")
    private int esPort;
    @Value("${elasticsearch.userName}")
    private String userName;
    @Value("${elasticsearch.password}")
    private String password;
    @Value("${elasticsearch.connectNum}")
    private int connectNum;
    @Value("${elasticsearch.connectPerRoute}")
    private int connectPerRoute;

    public static final String SCHEME = "http";

    @Bean
    public HttpHost getHttpHost(){
        return  new HttpHost(esHost,esPort,SCHEME);
    }

    /**
     * 指向初始化的方法  和销毁方法
     * @return
     */
    @Bean(initMethod = "init",destroyMethod = "close")
    public ESClientSpringFactory getESClientSpringFactory(){
        return  ESClientSpringFactory.build(userName,password,getHttpHost(),connectNum,connectPerRoute);
    }

    @Bean("getRestClient")
    @Scope("singleton")
    public RestClient getRestClient(){
        return getESClientSpringFactory().getRestClient();
    }
    @Bean("getRestHighLevelClient")
    @Scope("singleton")
    public RestHighLevelClient getRestHighLevelClient(){
        return getESClientSpringFactory().getRestHighLevelClient();
    }
}
