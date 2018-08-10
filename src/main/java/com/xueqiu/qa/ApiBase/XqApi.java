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

    protected final void setJsonSchemaFilePath(String jsonSchemaFilePath)
    {
        this.jsonSchemaFilePath = jsonSchemaFilePath;
    }


    public XqApi(String hostURL, String resourceURL, HttpMethod httpMethod)
    {
        this.requestURL = hostURL + resourceURL;
        this.domainURL = hostURL;
        this.httpMethod = httpMethod;
    }


    protected final JSONObject getResponse(Map<String, String> parameterList)
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

    protected final JSONObject getResponse(Map<String, String> parameterList, TestAccount testAccount)
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

    protected final Boolean SchemaValidation(JSONObject actualJsonObject) {

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

    protected final JSONObject getJsonObject(String key, JSONObject jsonObject)
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

    protected final Object getJsonProperty(String key, JSONObject jsonObject)
    {
        if(preTreatment(key,jsonObject))
        {
            Object object = jsonObject.get(key);
            if(object instanceof String)
            {
                return object;
            }
            else if(object instanceof Number)
            {
                return object;
            }
            else if(object instanceof Boolean)
            {
                return object;
            }
            else if(object == null)
            {
                return null;
            }
        }
        return null;
    }

    protected final JSONArray getJsonArray(String key, JSONObject jsonObject)
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
                return null;
            }
        }
        return null;
    }

    public abstract JSONObject getJsonAtArray(int index, JSONArray jsonArray);

    public abstract Object getPropertyAtArray(int index, JSONArray jsonArray);

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
