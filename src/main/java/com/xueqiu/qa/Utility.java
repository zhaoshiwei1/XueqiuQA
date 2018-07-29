package com.xueqiu.qa;

import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.client.CookieStore;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.json.JSONObject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

public class Utility
{

    public static String encryptMD5(String plainText)
    {
        try {
            // 得到一个信息摘要器
            MessageDigest digest = MessageDigest.getInstance("md5");
            byte[] result = digest.digest(plainText.getBytes());
            StringBuffer buffer = new StringBuffer();
            // 把每一个byte 做一个与运算 0xff;
            for (byte b : result) {
                int number = b & 0xff;
                String str = Integer.toHexString(number);
                if (str.length() == 1) {
                    buffer.append("0");
                }
                buffer.append(str);
            }

            //System.out.println(buffer.toString());
            return buffer.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return "";
        }
    }



    public static CookieStore setCookieStore(TestAccount testAccount)
    {
        HttpUtility httpUtility = new HttpUtility();

        Map<String, String> parameterList = new HashMap<>();
        parameterList.put(GlobalDefine.telephone, testAccount.telephone);
        parameterList.put(GlobalDefine.password, encryptMD5(testAccount.password));
        parameterList.put(GlobalDefine.areacode, testAccount.areacode);
        parameterList.put(GlobalDefine.captcha, testAccount.captcha);
        parameterList.put(GlobalDefine.client_id, testAccount.client_id);
        parameterList.put(GlobalDefine.client_secret, testAccount.client_secret);
        parameterList.put(GlobalDefine.device_uuid, testAccount.device_uuid);
        parameterList.put(GlobalDefine.grant_type, testAccount.grant_type);
        parameterList.put(GlobalDefine.is_register, testAccount.is_register);
        parameterList.put(GlobalDefine.sid, testAccount.sid);

        JSONObject jsonObject = httpUtility.post(GlobalDefine.loginURL, parameterList);
        //System.out.println(jsonObject.toString());

        String access_token = jsonObject.getString(GlobalDefine.access_token);
        //System.out.println(access_token);

        try {
            httpUtility.finalize();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }

        CookieStore cookieStore = new BasicCookieStore();
        BasicClientCookie xq_a_token = new BasicClientCookie(GlobalDefine.tokenName, access_token);
        xq_a_token.setVersion(GlobalDefine.cookieVersion);
        xq_a_token.setDomain(GlobalDefine.domainURL);
        xq_a_token.setPath(GlobalDefine.path);
        cookieStore.addCookie(xq_a_token);
        return cookieStore;
    }

}
