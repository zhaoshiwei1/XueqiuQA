package com.xueqiu.qa.ExecutorUtility;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.CookieStore;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class HttpUtility
{

    public CloseableHttpClient httpClient = null;

    public HttpResponse post(String url, Map<String, String> parameterList) throws Exception
    {
        this.httpClient = HttpClients.custom().setSSLSocketFactory(Utility.fileToSSL()).build();
        HttpResponse httpResponse = null;
        try {
            HttpPost httpPost = new HttpPost(url);
            List<NameValuePair> list = new ArrayList<NameValuePair>();
            Iterator iterator = parameterList.entrySet().iterator();
            while(iterator.hasNext()){
                Map.Entry<String,String> elem = (Map.Entry<String, String>) iterator.next();
                list.add(new BasicNameValuePair(elem.getKey(),elem.getValue()));
            }
            if(list.size() > 0){
                UrlEncodedFormEntity entity = new UrlEncodedFormEntity(list,"UTF-8");
                httpPost.setEntity(entity);
            }
            httpResponse = httpClient.execute(httpPost);//
        } catch (Exception e) {
            e.printStackTrace();
        }
        return httpResponse;
    }

    public HttpResponse post(String url, Map<String, String> parameterList, CookieStore cookieStore) throws Exception
    {
        this.httpClient = HttpClients.custom().setSSLSocketFactory(Utility.fileToSSL())
                .setDefaultCookieStore(cookieStore)
                .build();
        HttpResponse httpResponse = null;
        try {
            HttpPost httpPost = new HttpPost(url);
            List<NameValuePair> list = new ArrayList<NameValuePair>();
            Iterator iterator = parameterList.entrySet().iterator();
            while(iterator.hasNext()){
                Map.Entry<String,String> elem = (Map.Entry<String, String>) iterator.next();
                list.add(new BasicNameValuePair(elem.getKey(),elem.getValue()));
            }
            if(list.size() > 0){
                UrlEncodedFormEntity entity = new UrlEncodedFormEntity(list,"UTF-8");
                httpPost.setEntity(entity);
            }
            httpResponse = httpClient.execute(httpPost);//
        } catch (Exception e) {
            e.printStackTrace();
        }
        return httpResponse;
    }

    public HttpResponse get(String url, Map<String, String> parameterList) throws Exception
    {
        this.httpClient = HttpClients.custom().setSSLSocketFactory(Utility.fileToSSL())
                .build();
        HttpResponse httpResponse = null;

        HttpGet httpGet = null;

        if(parameterList == null)
        {
            String urlWithOutParameter = url;
            httpGet = new HttpGet(urlWithOutParameter);
        }else {
            String urlWithParameter = url + "?";
            Iterator iterator = parameterList.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry<String, String> elem = (Map.Entry<String, String>) iterator.next();
                urlWithParameter += elem.getKey() + "=" + elem.getValue() + "&";
            }

            httpGet = new HttpGet(urlWithParameter);
        }

        try {
             httpResponse = this.httpClient.execute(httpGet);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return httpResponse;
    }

    public HttpResponse get(String url, Map<String, String> parameterList, CookieStore cookieStore) throws Exception
    {
        this.httpClient = HttpClients.custom().setSSLSocketFactory(Utility.fileToSSL())
                .setDefaultCookieStore(cookieStore)
                .build();
        HttpResponse httpResponse = null;
        HttpGet httpGet = null;
        if(parameterList == null)
        {
            String urlWithOutParameter = url;
            httpGet = new HttpGet(urlWithOutParameter);
        }else
        {
            String urlWithParameter = url + "?";
            Iterator iterator = parameterList.entrySet().iterator();
            while(iterator.hasNext()){
                Map.Entry<String,String> elem = (Map.Entry<String, String>) iterator.next();
                urlWithParameter += elem.getKey() + "=" + elem.getValue() + "&";
            }
            httpGet = new HttpGet(urlWithParameter);
        }

        try {
            httpResponse = this.httpClient.execute(httpGet);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return httpResponse;
    }


    public void finalize() throws Throwable {
        this.httpClient.close();
        super.finalize();
    }
}
