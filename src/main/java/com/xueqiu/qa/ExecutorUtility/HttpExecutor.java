package com.xueqiu.qa.ExecutorUtility;

import com.xueqiu.qa.GlobalDefine.GlobalDefine;
import com.xueqiu.qa.GlobalDefine.TestAccount;
import org.apache.http.client.CookieStore;
import org.json.JSONObject;
import java.util.Map;

public class HttpExecutor
{
    public DoHttpConfig doHttpConfig;

    public HttpExecutor(String requestURL, String domainURL, Map<String, String> parameterList,
                                    HttpMethod httpMethod, TestAccount testAccount, Boolean ifAuthorized)
    {
        if(ifAuthorized&&(null!=testAccount))
        {
            CookieStore cookieStore = testAccount.getCookieStore(testAccount, domainURL);
            this.doHttpConfig = new DoHttpConfig(requestURL, parameterList, httpMethod, cookieStore);
        }else
        {
            this.doHttpConfig = new DoHttpConfig(requestURL, parameterList, httpMethod);
        }

    }

    public JSONObject getResponseJsonObject()
    {
        String strJsonObject = this.doHttpConfig.doRequest();
        if(null == strJsonObject)
        {
            System.out.println("Response Not Get Issue");
            return null;
        }else
        {
            String[] list = strJsonObject.split(GlobalDefine.separator);
            if(1 == list.length)
            {
                return new JSONObject(strJsonObject);
            }else
            {

                System.out.println(list[0]);
                System.out.println(list[1]);
                return null;
            }
        }
    }


    public void _finalize()
    {
        try {
            this.doHttpConfig.finalize();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
    }
}
