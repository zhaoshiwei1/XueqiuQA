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

public abstract class XqApi
{
    private String requestURL;
    private String domainURL;
    private HttpMethod httpMethod;
    private Boolean ifAuthorized = false;
    private String jsonSchemaFilePath = null;

    public final void setJsonSchemaFilePath(String jsonSchemaFilePath)
    {
        this.jsonSchemaFilePath = jsonSchemaFilePath;
    }


    public XqApi(String hostURL, String resourceURL, HttpMethod httpMethod)
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
    }


    public final JSONObject getResponse(Map<String, String> parameterList)
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

    public final JSONObject getResponse(Map<String, String> parameterList, TestAccount testAccount)
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

    public final Boolean SchemaValidation(JSONObject actualJsonObject) {

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

    public final JSONObject getJsonObject(String key, JSONObject jsonObject)
    {
        if(preTreatment(key, jsonObject))
        {
            Object object = jsonObject.get(key);
            if(object instanceof  JSONObject)
            {
                return (JSONObject) object;
            }else
            {
                System.out.println("Value of " + key + " is not JSONObject");
                return null;
            }
        }
        return null;
    }

    public final Object getJsonProperty(String key, JSONObject jsonObject)
    {
        if(preTreatment(key,jsonObject))
        {
            Object object = jsonObject.get(key);
            if(object instanceof String || object instanceof Number || object instanceof Boolean || object ==null)
            {
                return object;
            }else
            {
                System.out.println("Type of " + key + " Error");
            }
        }
        return null;
    }

    public final JSONArray getJsonArray(String key, JSONObject jsonObject)
    {
        if(preTreatment(key, jsonObject))
        {
            Object object = jsonObject.get(key);
            if(object instanceof JSONArray)
            {
                return (JSONArray) object;
            }else
            {
                System.out.println("Value of " + key + " is Not JSONArray");
            }
        }
        return null;
    }

    public final JSONObject getItemAtArray(int index, JSONArray jsonArray)
    {
        if(preTreatment(index, jsonArray))
        {
            Object object = jsonArray.get(index);
            if(object instanceof JSONObject)
            {
                return (JSONObject) object;
            }else
            {
                System.out.println("Index: " + index +"'s value is Not JSONObject");
            }
        }
        return null;
    }

    public final Object getPropertyAtArray(int index, JSONArray jsonArray)
    {
        if(preTreatment(index, jsonArray))
        {
            Object object = jsonArray.get(index);
            if(object instanceof String || object instanceof Number || object instanceof Boolean || object == null)
            {
                return object;
            }else
            {
                System.out.println("Index: " + index +"'s format is INVALID");
            }
        }
        return null;
    }

    private final boolean preTreatment(String key, JSONObject jsonObject)
    {
        if(null == jsonObject)
        {
            System.out.println("Parameter object is NULL");
            return false;
        }else if(!(jsonObject instanceof JSONObject)) {
            System.out.println("Parameter object is not JSONObject");
            return false;
        }else{
            ArrayList<String> list = new ArrayList<>();
            Iterator<?> iterator = jsonObject.keys();
            while (iterator.hasNext())
            {
                list.add((String)iterator.next());
            }
            if(list.contains(key))
            {
                return true;
            }else
            {
                System.out.println("Key: " + key + " Not Found");
                return false;
            }
        }
    }

    private final boolean preTreatment(int index, JSONArray jsonArray)
    {
        if(null == jsonArray)
        {
            System.out.println("Parameter object is NULL");
            return false;
        }else if(!(jsonArray instanceof JSONArray))
        {
            System.out.println("Parameter object is not JSONArray");
            return false;
        }else{
            if(index < jsonArray.length())
            {
                return true;
            }else
            {
                System.out.println(index + " : Out of Index Range");
                return false;
            }
        }
    }

}
