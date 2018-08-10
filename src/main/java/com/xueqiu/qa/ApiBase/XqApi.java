package com.xueqiu.qa.ApiBase;

import com.xueqiu.qa.ExecutorUtility.HttpExecutor;
import com.xueqiu.qa.ExecutorUtility.HttpMethod;
import com.xueqiu.qa.ExecutorUtility.SchemaValidation;
import com.xueqiu.qa.GlobalDefine.TestAccount;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Map;

import static com.xueqiu.qa.ExecutorUtility.Utility.fileToJson;

public abstract class XqApi
{
    private String requestURL;
    private String domainURL;
    private HttpMethod httpMethod;
    private Boolean ifAuthorized = false;
    private String jsonSchemaFilePath = null;

    protected void setJsonSchemaFilePath(String jsonSchemaFilePath)
    {
        this.jsonSchemaFilePath = jsonSchemaFilePath;
    }


    public XqApi(String hostURL, String resourceURL, HttpMethod httpMethod)
    {
        this.requestURL = hostURL + resourceURL;
        this.domainURL = hostURL;
        this.httpMethod = httpMethod;
    }


    protected JSONObject getResponse(Map<String, String> parameterList)
    {
        this.ifAuthorized = false;
        HttpExecutor httpExecutor = new HttpExecutor(this.requestURL, this.domainURL, parameterList, this.httpMethod,
                null, this.ifAuthorized);
        try
        {
            return httpExecutor.getResponseJsonObject();
        }catch (Exception e)
        {
            System.out.println(e.getStackTrace());
            return null;
        }finally {
            httpExecutor._finalize();
        }
    }

    protected JSONObject getResponse(Map<String, String> parameterList, TestAccount testAccount)
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
            return httpExecutor.getResponseJsonObject();
        }catch (Exception e)
        {
            System.out.println(e.getStackTrace());
            return null;
        }finally {
            httpExecutor._finalize();
        }

    }

    protected Boolean SchemaValidation(JSONObject actualJsonObject) {

        if(this.jsonSchemaFilePath.equals(null))
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

    public abstract Object jsonAnalyze();

    public JSONObject getJsonNode(String key, JSONObject jsonObject)
    {
        if (null == jsonObject)
        {
            return null;
        }else
        {
            Object object = jsonObject.get(key);
            if(object instanceof  JSONObject)
            {
                return (JSONObject) object;
            }else
            {
                return null;
            }
        }
    }

    public abstract Object getJsonProperty(String key, JSONObject jsonObject);

    public abstract JSONArray getJsonArray(int index, JSONObject jsonObject);

}
