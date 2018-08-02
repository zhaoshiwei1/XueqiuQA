package com.xueqiu.qa.executor;

import com.xueqiu.qa.GlobalDefine;
import org.apache.http.HttpResponse;
import org.apache.http.client.CookieStore;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.Map;

public class DoHttpConfig extends HttpUtility
{
    public HttpMethod httpMethod;
    public String requestURL = null;
    public Map<String, String> parameterList = null;
    public CookieStore cookieStore = null;

    public DoHttpConfig(String requestURL, Map<String, String> parameterList, HttpMethod httpMethod)
    {
        this.httpMethod = httpMethod;
        this.requestURL = requestURL;
        this.parameterList = parameterList;
    }

    public DoHttpConfig(String requestURL, Map<String, String> parameterList, HttpMethod httpMethod, CookieStore cookieStore)
    {
        this.httpMethod = httpMethod;
        this.requestURL = requestURL;
        this.parameterList = parameterList;
        this.cookieStore = cookieStore;
    }

    public String doRequest()
    {
        HttpResponse httpResponse = null;
        if(this.cookieStore == null)
        {
            switch (httpMethod)
            {
                case get:
                    httpResponse = super.get(this.requestURL, this.parameterList);
                    try {
                        return httpResponseDecode(httpResponse);
                    } catch (IOException e) {
                        e.printStackTrace();
                        return null;
                    }
                case post:
                    httpResponse = super.post(this.requestURL, this.parameterList);
                    try {
                        return httpResponseDecode(httpResponse);
                    } catch (IOException e) {
                        e.printStackTrace();
                        return null;
                    }
                default:
                    return null;
            }
        } else if(this.cookieStore != null)
        {
            switch (httpMethod)
            {
                case get:
                    httpResponse = super.get(this.requestURL, this.parameterList, this.cookieStore);
                    try {
                        return httpResponseDecode(httpResponse);
                    } catch (IOException e) {
                        e.printStackTrace();
                        return null;
                    }
                case post:
                    httpResponse = super.post(this.requestURL, this.parameterList,this.cookieStore);
                    try {
                        return httpResponseDecode(httpResponse);
                    } catch (IOException e) {
                        e.printStackTrace();
                        return null;
                    }
                default:
                    return null;
            }
        }
        else
        {
            return null;
        }
    }

    public void finalize() throws Throwable {
        super.finalize();
    }

    private String httpResponseDecode(HttpResponse httpResponse) throws IOException {
        int statusCode = httpResponse.getStatusLine().getStatusCode();
        if(200 == statusCode)
        {
            return EntityUtils.toString(httpResponse.getEntity());
        }else
        {
            return String.valueOf(httpResponse.getStatusLine().getStatusCode()) + GlobalDefine.separator
                    + httpResponse.getStatusLine().getReasonPhrase();
        }
    }
}

