package com.github.music.tools;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.nio.charset.Charset;

/**
 * Created by houk1987 on 15/7/28.
 */
public class HttpClientManager {

    static {
        initHttpClient();
    }

    private static CloseableHttpClient httpclient;


    private static void initHttpClient(){
        httpclient = HttpClients.createDefault();
    }

    /**
     * GET 请求
     * @param requestUrl 请求的地址
     * @return
     */
    public static String requestGet(String requestUrl){
        String result="";
        HttpGet httpGet = new HttpGet(requestUrl);
        CloseableHttpResponse response = null;
        try {
            response = httpclient.execute(httpGet);
            HttpEntity httpEntity = response.getEntity();
            result = EntityUtils.toString(httpEntity, Charset.forName("UTF-8"));
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (response!=null){
                try {
                    response.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            httpGet.releaseConnection();
        }
        return result;
    }

    public static void main(String[] args) {
        String str = HttpClientManager.requestGet("http://www.51ape.com/");
        System.out.println(str);
    }
}
