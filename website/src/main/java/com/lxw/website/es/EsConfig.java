package com.lxw.website.es;

import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

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

    public static final String SCHEME = "http";

    @Bean(name = "highLevelClient")
    public RestHighLevelClient restHighLevelClient() {
        final CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
        //带密码访问
        credentialsProvider.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials(userName,password));
        RestClientBuilder restClientBuilder = RestClient.builder(new HttpHost(esHost, esPort, SCHEME))
                .setHttpClientConfigCallback(httpAsyncClientBuilder -> httpAsyncClientBuilder.setDefaultCredentialsProvider(credentialsProvider));
        return new RestHighLevelClient(restClientBuilder);
    }
}
