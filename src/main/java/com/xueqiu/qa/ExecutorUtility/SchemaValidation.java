package com.xueqiu.qa.ExecutorUtility;

import org.everit.json.schema.Schema;
import org.everit.json.schema.ValidationException;
import org.everit.json.schema.loader.SchemaLoader;
import org.json.JSONObject;

public class SchemaValidation
{
    public JSONObject actualJsonObject;
    public JSONObject expectedJsonObject;

    public SchemaValidation(JSONObject actualJsonObject, JSONObject expectedJsonObject)
    {
        this.actualJsonObject = actualJsonObject;
        this.expectedJsonObject = expectedJsonObject;
    }

    public boolean validation()
    {

        try {
            Schema schema = SchemaLoader.load(this.expectedJsonObject);
            schema.validate(this.actualJsonObject);
            return true;
        }
        catch (ValidationException e)
        {

            for (String msg : e.getAllMessages())
            {
                System.out.println(msg);
            }

        }

        return false;
    }
}
