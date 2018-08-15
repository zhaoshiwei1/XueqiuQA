package com.xueqiu.qa;

import com.xueqiu.qa.ApiBase.XqApi;
import com.xueqiu.qa.ExecutorUtility.HttpMethod;

public abstract class ApiFactory
{
    private String host = null;
    private String requestURI = null;
    private String jsonSchemaFilePath = null;
    private String[][] apiDescriptionStringArray = null;
    public XqApi api = null;

    public ApiFactory(String[][] apiDescriptionString)
    {
        this.apiDescriptionStringArray = apiDescriptionString;
    }

    public final XqApi init(String requestURI)
    {
        int flag = checkInApiList(requestURI);
        if(flag != -1)
        {
            this.api = this._init(flag);
            return this.api;
        }
        return null;

    }

    private final int checkInApiList(String requestURI)
    {
        for(int i = 0; i< this.apiDescriptionStringArray.length; i++)
        {
            if(this.apiDescriptionStringArray[i][1].equals(requestURI))
            {
                return i;
            }
        }
        return -1;
    }

    private final XqApi _init(int flag)
    {
        this.host = this.apiDescriptionStringArray[flag][0];
        this.requestURI = this.apiDescriptionStringArray[flag][1];
        this.jsonSchemaFilePath = this.apiDescriptionStringArray[flag][3];
        String httpMethod = this.apiDescriptionStringArray[flag][2];
        if(httpMethod.toLowerCase().equals("get"))
        {
            return new XqApi(this.host, this.requestURI, HttpMethod.get, this.jsonSchemaFilePath);
        }
        if(httpMethod.toLowerCase().equals("post"))
        {
            return new XqApi(this.host, this.requestURI, HttpMethod.post, this.jsonSchemaFilePath);
        }
        return null;
    }
}
