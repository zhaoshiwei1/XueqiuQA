package com.xueqiu.qa.testAccount;


import com.xueqiu.qa.GlobalDefine;
import com.xueqiu.qa.executor.HttpUtility;
import com.xueqiu.qa.utility.Utility;
import org.apache.http.HttpResponse;
import org.apache.http.client.CookieStore;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Map;

public class TestAccount
{
    public String telephone;
    public String password;
    public String areacode;
    public String captcha;
    public String client_id;
    public String client_secret;
    public String device_uuid;
    public String grant_type;
    public String is_register;
    public String sid;

    public TestAccount(String telephone, String password, String areacode, String captcha, String client_id,
                       String client_secret, String device_uuid, String grant_type, String is_register, String sid)
    {
        this.telephone = telephone;
        this.password = password;
        this.areacode = areacode;
        this.captcha = captcha;
        this.client_id = client_id;
        this.client_secret = client_secret;
        this.device_uuid = device_uuid;
        this.grant_type = grant_type;
        this.is_register = is_register;
        this.sid = sid;
    }

    public CookieStore getCookieStore(TestAccount testAccount, String domainURL)
    {
        Map<String, String> loginParameterList = Utility.getKeyAndValue(testAccount);
        String plainPassword = loginParameterList.get("password");
        String encryptPassword = Utility.encryptMD5(plainPassword);
        loginParameterList.put("password", encryptPassword);

        HttpUtility httpUtility = new HttpUtility();
        HttpResponse httpResponse = httpUtility.post(GlobalDefine.loginURL, loginParameterList);
        if(httpResponse.getStatusLine().getStatusCode() == 200)
        {
            String strResult = "";
            try {
                strResult = EntityUtils.toString(httpResponse.getEntity());
            } catch (IOException e) {
                e.printStackTrace();
            }
            JSONObject jsonObject = new JSONObject(strResult);
            String access_token = jsonObject.getString(GlobalDefine.access_token);
            try {
                httpUtility.finalize();
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }

            CookieStore cookieStore = new BasicCookieStore();
            BasicClientCookie xq_a_token = new BasicClientCookie(GlobalDefine.tokenName, access_token);
            xq_a_token.setVersion(GlobalDefine.cookieVersion);
            xq_a_token.setDomain(domainURL);
            xq_a_token.setPath(GlobalDefine.path);
            cookieStore.addCookie(xq_a_token);
            return cookieStore;
        }else
        {
            return null;
        }

    }

}
