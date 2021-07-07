package com.lxw.website.http;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author LXW
 * @date 2021年04月25日 10:36
 */
@Component
@Slf4j
public class HttpApiService {

    @Autowired
    @Qualifier("getCloseAbleHttpClientBuilder")
    private CloseableHttpClient closeableHttpClient;

    @Autowired
    @Qualifier("getRequestConfig")
    private RequestConfig config;

    /**
     * 不带参数的 get请求
     * @author LXW
     * @date 2021/4/25 10:50
     * @param url
     * @return java.lang.String
     */
    public String doGet(String url) throws IOException {
        HttpGet httpGet=new HttpGet(url);
        httpGet.setConfig(config);
       CloseableHttpResponse response=this.closeableHttpClient.execute(httpGet);
       if(response.getStatusLine().getStatusCode()==200){
           return EntityUtils.toString(response.getEntity(),"UTF-8");
       }
        this.closeableHttpClient.close();
       return null;
    }

    /**
     * 带参数的get请求
     * @author LXW
     * @date 2021/4/25 11:13
     * @param url
     * @param map 
     * @return java.lang.String
     */
    public String doGet(String url, Map<String,Object> map) throws IOException, URISyntaxException {
        URIBuilder uriBuilder=new URIBuilder(url);
        if(map!=null){
            for(Map.Entry<String,Object> entry:map.entrySet()){
                uriBuilder.setParameter(entry.getKey(), entry.getValue().toString());
            }
        }
        return  this.doGet(uriBuilder.build().toString());
    }

    /**
     * 不带参数的POST
     * @author LXW
     * @date 2021/4/25 11:17
     * @param url 
     * @return java.lang.String
     */
    public String doPost(String url) throws IOException {
        HttpPost httpPost=new HttpPost(url);
        httpPost.setConfig(config);
        CloseableHttpResponse response=this.closeableHttpClient.execute(httpPost);
        if(response.getStatusLine().getStatusCode()==200){
            return EntityUtils.toString(response.getEntity(),"UTF-8");
        }
        this.closeableHttpClient.close();
        return null;
    }

    /**
     * 带参数的POST AND HEADer
     * @author LXW
     * @date 2021/4/25 11:30
     * @param url
     * @param dataMap
     * @param headerMap 
     * @return com.lxw.website.http.HttpResult
     */
    public HttpResult doPost(String url, Map<String,Object> dataMap, Map<String,Object> headerMap) throws IOException {
        HttpPost httpPost=new HttpPost(url);
        httpPost.setConfig(config);
        if(dataMap!=null){
            List<NameValuePair> nameValuePairList=new ArrayList<>();
            for(Map.Entry<String,Object> map:dataMap.entrySet()){
                nameValuePairList.add(new BasicNameValuePair(map.getKey(),map.getValue().toString()));
            }
            UrlEncodedFormEntity urlEncodedFormEntity=new UrlEncodedFormEntity(nameValuePairList, "UTF-8");
            httpPost.setEntity(urlEncodedFormEntity);
        }

        if(headerMap!=null){
            for (String key : headerMap.keySet()) {
                String value = headerMap.get(key).toString();
                httpPost.addHeader(key, value);
            }
        }

        CloseableHttpResponse response = this.closeableHttpClient.execute(httpPost);
        this.closeableHttpClient.close();
        return new HttpResult(response.getStatusLine().getStatusCode(), EntityUtils.toString(
                response.getEntity(), "UTF-8"));

    }


}
