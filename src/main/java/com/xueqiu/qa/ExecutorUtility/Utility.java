package com.xueqiu.qa.ExecutorUtility;

import org.apache.commons.io.FileUtils;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.ssl.TrustStrategy;
import org.json.JSONObject;

import javax.net.ssl.SSLContext;
import java.io.File;
import java.io.FileInputStream;
import java.security.KeyStore;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

import java.io.File;

import java.io.FileInputStream;

import java.security.KeyStore;

import javax.net.ssl.SSLContext;

import org.apache.http.HttpEntity;

import org.apache.http.client.methods.CloseableHttpResponse;

import org.apache.http.client.methods.HttpGet;

import org.apache.http.conn.ssl.SSLContexts;

import org.apache.http.conn.ssl.SSLConnectionSocketFactory;

import org.apache.http.impl.client.CloseableHttpClient;

import org.apache.http.impl.client.HttpClients;

import org.apache.http.util.EntityUtils;

import java.lang.reflect.Field;


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

    public static Map<String, String> getKeyAndValue(Object obj) {
        Map<String, String> list = new HashMap<String, String>();
        // 得到类对象
        Class userCla = (Class) obj.getClass();
        Field[] fs = userCla.getDeclaredFields();
        for (int i = 0; i < fs.length; i++) {
            Field f = fs[i];
            f.setAccessible(true);
            // 设置些属性是可以访问的
            try {
                    if(!f.getName().equals("cookieStore")) {
                        list.put(f.getName(), (String) f.get(obj));
                    }
                }
            catch (IllegalArgumentException e) {
                e.printStackTrace();
            }
            catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return list;
    }

    public static JSONObject fileToJson(String filePath)
    {
        JSONObject rawSchema = null;
        try {
            String input = FileUtils.readFileToString(new File(filePath), "UTF-8");
            rawSchema = new JSONObject(input);
        } catch (Exception e) {
            e.getStackTrace();
            return rawSchema;
        }

        return rawSchema;
    }

    public static SSLConnectionSocketFactory fileToSSL() throws Exception
    {
        KeyStore trustStore  = KeyStore.getInstance(KeyStore.getDefaultType());


        FileInputStream instream = new FileInputStream(new File("src/main/java/com/xueqiu/qa/xq.keystore"));

        try {

            trustStore.load(instream, "xueqiu".toCharArray());

        } finally {

            instream.close();

        }

        SSLContext sslcontext = SSLContexts.custom().loadTrustMaterial(trustStore).build();

        SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslcontext,

                SSLConnectionSocketFactory.STRICT_HOSTNAME_VERIFIER);

        return sslsf;
    }

}
