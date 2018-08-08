package com.xueqiu.qa.ApiBase;

import com.xueqiu.qa.ExecutorUtility.HttpMethod;
import com.xueqiu.qa.ExecutorUtility.SchemaValidationExecutor;
import com.xueqiu.qa.GlobalDefine.TestAccount;
import org.json.JSONObject;

import java.util.Map;

public abstract class XqApi
{
    public String requestURL;
    public String domainURL;
    public HttpMethod httpMethod;
    public Boolean ifAuthorized = false;
    public JSONObject responseJSONObject = null;

    public XqApi(String hostURL, String resourceURL, HttpMethod httpMethod)
    {
        this.requestURL = hostURL + resourceURL;
        this.domainURL = hostURL;
        this.httpMethod = httpMethod;
    }

    public Boolean SchemaValidation(Map<String, String> parameterList, TestAccount testAccount, String jsonSchemaFilePath) {
        if (null != testAccount) {
            this.ifAuthorized = true;
        }

        SchemaValidationExecutor schemaValidationExecutor = new SchemaValidationExecutor(this.requestURL, this.domainURL,
                parameterList, this.httpMethod, testAccount, this.ifAuthorized, jsonSchemaFilePath);

        if (schemaValidationExecutor.validate())
        {
            this.responseJSONObject = schemaValidationExecutor.getResponseJsonObject();
            return true;
        }else
        {
            return false;
        }
    }

    public abstract Object jsonAnalyze();
}
