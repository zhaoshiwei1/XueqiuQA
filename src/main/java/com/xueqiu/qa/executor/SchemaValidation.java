package com.xueqiu.qa.executor;

import org.everit.json.schema.Schema;
import org.everit.json.schema.ValidationException;
import org.everit.json.schema.loader.SchemaLoader;
import org.json.JSONObject;

public class SchemaValidation
{

    public SchemaValidation()
    {

    }

    public boolean validation(JSONObject actualJsonObject, JSONObject expectedJsonObject)
    {

        try {
            Schema schema = SchemaLoader.load(expectedJsonObject);
            schema.validate(actualJsonObject);
            return true;
        }
        catch (ValidationException e)
        {

/*
            System.out.println(e.toJSON());
*/
            for (String msg : e.getAllMessages())
            {
                System.out.println(msg);
            }

        }

        return false;
    }
}
