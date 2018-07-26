package com.xueqiu.qa;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.CookieStore;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class HttpUtility
{

    public CloseableHttpClient httpClient = null;

    public JSONObject post(String url, Map<String, String> parameterList)
    {
        this.httpClient = HttpClients.createDefault();
        String srtResult = "";
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
            HttpResponse httpResponse = httpClient.execute(httpPost);//
            if(httpResponse.getStatusLine().getStatusCode() == 200){
                srtResult = EntityUtils.toString(httpResponse.getEntity());//获得返回的结果
            }else
            {
                System.out.println(httpResponse.getStatusLine().getStatusCode());
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            try {
                this.httpClient.close();
            }catch (IOException e)
            {
                e.printStackTrace();
            }
        }
        JSONObject jsonObject = new JSONObject(srtResult);
        return jsonObject;
    }

    public JSONObject post(String url, Map<String, String> parameterList, CookieStore cookieStore)
    {
        this.httpClient = HttpClients.custom()
                .setDefaultCookieStore(cookieStore)
                .build();
        String srtResult = "";
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
            HttpResponse httpResponse = httpClient.execute(httpPost);//
            if(httpResponse.getStatusLine().getStatusCode() == 200){
                srtResult = EntityUtils.toString(httpResponse.getEntity());//获得返回的结果
            }else
            {
                System.out.println(httpResponse.getStatusLine().getStatusCode());
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            try {
                this.httpClient.close();
            }catch (IOException e)
            {
                e.printStackTrace();
            }
        }
        JSONObject jsonObject = new JSONObject(srtResult);
        return jsonObject;
    }

    public JSONObject get(String url, Map<String, String> parameterList)
    {
        this.httpClient = HttpClients.createDefault();
        String srtResult = "";
        String urlWithParameter = url + "?";
        List<NameValuePair> list = new ArrayList<NameValuePair>();
        Iterator iterator = parameterList.entrySet().iterator();
        while(iterator.hasNext()){
            Map.Entry<String,String> elem = (Map.Entry<String, String>) iterator.next();
            urlWithParameter += elem.getKey() + "=" + elem.getValue() + "&";
        }
        HttpGet httpGet = new HttpGet(urlWithParameter);

        try {
            HttpResponse httpResponse = this.httpClient.execute(httpGet);
            if(httpResponse.getStatusLine().getStatusCode() == 200){
                srtResult = EntityUtils.toString(httpResponse.getEntity());//获得返回的结果
            }else
            {
                System.out.println(httpResponse.getStatusLine().getStatusCode());
                srtResult = EntityUtils.toString(httpResponse.getEntity());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                this.httpClient.close();
            }catch (IOException e)
            {
                e.printStackTrace();
            }
        }
        JSONObject jsonObject = new JSONObject(srtResult);
        return jsonObject;
    }

    public JSONObject get(String url, Map<String, String> parameterList, CookieStore cookieStore)
    {
        this.httpClient = HttpClients.custom()
                .setDefaultCookieStore(cookieStore)
                .build();
        String srtResult = "";
        String urlWithParameter = url + "?";
        List<NameValuePair> list = new ArrayList<NameValuePair>();
        Iterator iterator = parameterList.entrySet().iterator();
        while(iterator.hasNext()){
            Map.Entry<String,String> elem = (Map.Entry<String, String>) iterator.next();
            urlWithParameter += elem.getKey() + "=" + elem.getValue() + "&";
        }
        HttpGet httpGet = new HttpGet(urlWithParameter);

        try {
            HttpResponse httpResponse = this.httpClient.execute(httpGet);
            if(httpResponse.getStatusLine().getStatusCode() == 200){
                srtResult = EntityUtils.toString(httpResponse.getEntity());//获得返回的结果
            }else
            {
                System.out.println(httpResponse.getStatusLine().getStatusCode());
                srtResult = EntityUtils.toString(httpResponse.getEntity());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                this.httpClient.close();
            }catch (IOException e)
            {
                e.printStackTrace();
            }
        }
        JSONObject jsonObject = new JSONObject(srtResult);
        return jsonObject;
    }



    public void finalize() throws Throwable {
        this.httpClient.close();
        super.finalize();
    }
}
