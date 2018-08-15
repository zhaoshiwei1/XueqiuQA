package com.xueqiu.qa.ApiBase;

import com.xueqiu.qa.ExecutorUtility.HttpExecutor;
import com.xueqiu.qa.ExecutorUtility.HttpMethod;
import com.xueqiu.qa.ExecutorUtility.SchemaValidation;
import com.xueqiu.qa.GlobalDefine.TestAccount;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

import static com.xueqiu.qa.ExecutorUtility.Utility.fileToJson;

public class XqApi
{
    private String requestURL;
    private String domainURL;
    private HttpMethod httpMethod;
    private Boolean ifAuthorized = false;
    private String jsonSchemaFilePath = null;
    private JSONObject responseJSONObject = null;

    public final void setJsonSchemaFilePath(String jsonSchemaFilePath)
    {
        this.jsonSchemaFilePath = jsonSchemaFilePath;
    }


    public XqApi(String hostURL, String resourceURL, HttpMethod httpMethod, String jsonSchemaFilePath)
    {

        String[] list = hostURL.split("://");
        if(list.length>1)
        {
            this.requestURL = hostURL + resourceURL;
            this.domainURL = list[1];
        }else
        {
            this.requestURL = "https://" + hostURL + resourceURL;
            this.domainURL = hostURL;
        }

        this.httpMethod = httpMethod;

        this.jsonSchemaFilePath = jsonSchemaFilePath;

    }


    public final JSONObject send(Map<String, String> parameterList)
    {
        this.ifAuthorized = false;
        HttpExecutor httpExecutor = new HttpExecutor(this.requestURL, this.domainURL, parameterList, this.httpMethod,
                null, this.ifAuthorized);
        try
        {
            JSONObject temp = httpExecutor.getResponseJsonObject();
            this.responseJSONObject = temp;
            return temp;
        }catch (Exception e)
        {
            System.out.println(e.getStackTrace());
            return null;
        }finally {
            httpExecutor._finalize();
        }
    }

    public final JSONObject send(Map<String, String> parameterList, TestAccount testAccount)
    {
        if(null == testAccount)
        {
            this.ifAuthorized = false;
        }else
        {
            this.ifAuthorized = true;
        }

        HttpExecutor httpExecutor = new HttpExecutor(this.requestURL, this.domainURL, parameterList, this.httpMethod,
                testAccount, this.ifAuthorized);
        try
        {
            JSONObject temp = httpExecutor.getResponseJsonObject();
            this.responseJSONObject = temp;
            return temp;
        }catch (Exception e)
        {
            System.out.println(e.getStackTrace());
            return null;
        }finally {
            httpExecutor._finalize();
        }

    }

    public final boolean schemaValidation(JSONObject actualJsonObject) {

        if(this.jsonSchemaFilePath == null)
        {
            System.out.println("JsonSchema File Path is not set!");
            return false;
        }else
        {
            JSONObject expectedJsonObject = fileToJson(this.jsonSchemaFilePath);

            SchemaValidation schemaValidation = new SchemaValidation(actualJsonObject, expectedJsonObject);

            if(null == actualJsonObject)
            {
                return false;
            }else
            {
                return schemaValidation.validation();
            }
        }

    }

    public final boolean schemaValidation()
    {
        if(this.jsonSchemaFilePath == null)
        {
            System.out.println("JsonSchema File Path is not set!");
            return false;
        }else
        {
            JSONObject expectedJsonObject = fileToJson(this.jsonSchemaFilePath);

            SchemaValidation schemaValidation = new SchemaValidation(this.responseJSONObject , expectedJsonObject);

            if( null == this.responseJSONObject)
            {
                return false;
            }else
            {
                return schemaValidation.validation();
            }

        }
    }

    private Object jsonAnalyze(String[] path, JSONObject jsonObject)
    {
        if(path.length == 1 && path.length > 0)
        {
            return jsonObject.get(path[0]);
        }else if(path.length > 1)
        {
            String[] dpath = new String[path.length - 1];
            System.arraycopy(path, 1, dpath, 0,path.length-1);
            return jsonAnalyze(dpath, (JSONObject) jsonObject.get(path[0]));
        }
        return null;
    }

    public final Object parse(String path)
    {
        JSONObject jsonObject = this.responseJSONObject;
        String[] pathList = path.split("/");

        return  jsonAnalyze(pathList, jsonObject);
    }

    public final Object parse(String path, JSONObject jsonObject)
    {
        String[] pathList = path.split("/");
        return jsonAnalyze(pathList, jsonObject);
    }

}
