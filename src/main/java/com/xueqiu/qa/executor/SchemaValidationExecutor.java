package com.xueqiu.qa.executor;

import com.xueqiu.qa.testAccount.TestAccount;
import org.apache.http.client.CookieStore;
import org.json.JSONObject;

import java.util.Map;

import static com.xueqiu.qa.utility.Utility.fileToJson;

public class SchemaValidationExecutor
{
    public DoHttpConfig doHttpConfig;
    public SchemaValidation schemaValidation;
    public String jsonSchemaFilePath;


    public SchemaValidationExecutor(String requestURL, String domainURL, Map<String, String> parameterList,
                                    HttpMethod httpMethod, TestAccount testAccount, Boolean ifAuthorized,
                                    String jsonSchemaFilePath)
    {
        if(ifAuthorized)
        {
            CookieStore cookieStore = testAccount.getCookieStore(testAccount, domainURL);
            this.doHttpConfig = new DoHttpConfig(requestURL, parameterList, httpMethod, cookieStore);
        }else
        {
            this.doHttpConfig = new DoHttpConfig(requestURL, parameterList, httpMethod);
        }

        this.schemaValidation = new SchemaValidation();
        this.jsonSchemaFilePath = jsonSchemaFilePath;
    }

    public boolean validate()
    {
        JSONObject expectedJsonObject = fileToJson(this.jsonSchemaFilePath);
/*
        System.out.println(expectedJsonObject);
*/
        if(null == expectedJsonObject)
        {
            System.out.println("Json Schema File Issue");
            return false;
        }else
        {
            String strJsonObject = this.doHttpConfig.doRequest();
/*
            System.out.println(strJsonObject);
*/
            JSONObject actualJsonObject = new JSONObject(strJsonObject);
            return this.schemaValidation.validation(actualJsonObject, expectedJsonObject);
        }
    }

    public void _finalize()
    {
        try {
            this.doHttpConfig.finalize();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
    }
}
