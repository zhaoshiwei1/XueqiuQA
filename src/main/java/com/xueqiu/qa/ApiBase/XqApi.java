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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

    private Object jsonAnalyze(String[] path, Object jsonObject)
    {
        if(path.length == 1)
        {
            int arrayPath = stringAnalyze(path[0]);
            if(arrayPath == -1 && jsonObject instanceof JSONObject)
            {
                return ((JSONObject)jsonObject).get(path[0]);
            }else
            {
                if(jsonObject instanceof JSONArray)
                {
                    return ((JSONArray) jsonObject).get(arrayPath);
                }
            }
        }else if(path.length > 1)
        {
            String[] dpath = new String[path.length - 1];
            System.arraycopy(path, 1, dpath, 0,path.length-1);

            int arrayPath = stringAnalyze(path[0]);
            if(arrayPath == -1 && jsonObject instanceof JSONObject)
            {
                return jsonAnalyze(dpath, ((JSONObject)jsonObject).get(path[0]));
            }else if(arrayPath > 1 && jsonObject instanceof JSONArray)
            {
                return jsonAnalyze(dpath, ((JSONArray) jsonObject).get(arrayPath));
            }

        }
        return null;
    }

    public final Object parse(String path)
    {
        JSONObject jsonObject = this.responseJSONObject;
        String[] pathList = path.split("\\.");

        return  jsonAnalyze(pathList, jsonObject);
    }

    public final Object parse(String path, JSONObject jsonObject)
    {
        String[] pathList = path.split("\\.");
        return jsonAnalyze(pathList, jsonObject);
    }

    public final int stringAnalyze(String dPathString)
    {

        String regex = "(?<=\\[)(\\S+)(?=\\])";
        Pattern pattern = Pattern.compile (regex);
        Pattern numPattern = Pattern.compile("^[-\\+]?[\\d]*$");
            Matcher matcher = pattern.matcher(dPathString);
            if(matcher.find())
            {
/*
                System.out.println(matcher.group());
*/

                Matcher numMatcher = numPattern.matcher(matcher.group());

                if(numMatcher.find())
                {
/*
                    System.out.println(numMatcher.group());
*/
                    String[] bread = dPathString.split("\\[");
                    if(bread[0].length()==0)
                    {
/*
                        System.out.println(bread[0]);
                        System.out.println(numMatcher.group());
*/
                        return Integer.valueOf(numMatcher.group());

/*
                        itemMap.put(bread[0], Integer.valueOf(numMatcher.group()));
*/
                    }

                }
            }
        return -1;
    }

}
