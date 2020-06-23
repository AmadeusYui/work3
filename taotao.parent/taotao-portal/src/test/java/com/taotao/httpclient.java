package com.taotao;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.junit.Test;

public class httpclient {
    @Test
    public void doGet() throws Exception{
        //创建httpclient对象
        CloseableHttpClient httpClient = HttpClients.createDefault();
        //创建get对象
        HttpGet get = new HttpGet("https://www.baidu.com");
        //执行请求
        CloseableHttpResponse response = httpClient.execute(get);
        //取结果
        int statusCode = response.getStatusLine().getStatusCode();
        System.out.println(statusCode);
        HttpEntity entity = response.getEntity();
        String string = EntityUtils.toString(entity,"utf-8");
        System.out.println(string);
        //关闭httpclient
        response.close();
        httpClient.close();
    }

    @Test
    public void doGetWithParam() throws Exception{
        //创建一个httpclient对象
        CloseableHttpClient httpClient = HttpClients.createDefault();
        //创建一个uri对象
        URIBuilder uriBuilder = new URIBuilder("https://www.baidu.com/s");
        uriBuilder.addParameter("wd","原神");
        HttpGet get = new HttpGet(uriBuilder.build());
        //执行请求
        CloseableHttpResponse response = httpClient.execute(get);
        //取结果
        int statusCode = response.getStatusLine().getStatusCode();
        System.out.println(statusCode);
        HttpEntity entity = response.getEntity();
        String string = EntityUtils.toString(entity,"utf-8");
        System.out.println(string);
        //关闭httpclient
        response.close();
        httpClient.close();
    }
}
