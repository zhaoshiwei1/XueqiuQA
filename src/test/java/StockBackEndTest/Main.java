package StockBackEndTest;

import com.xueqiu.qa.GlobalDefine.TestAccount;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Iterator;

import static com.xueqiu.qa.ExecutorUtility.Utility.fileToJson;

public class Main
{
    public static void main(String[] args)
    {
        TestAccount testAccount = new TestAccount(
                "18515668408",
                "1234qwer",
                "86",
                "",
                "WiCimxpj5H",
                "TM69Da3uPkFzIdxpTEm6hp",
                "BDE2CF5F-5F88-4644-B5E5-87FB55841DC9",
                "password",
                "0",
                "BDE2CF5F-5F88-4644-B5E5-87FB55841DC9");

        JSONObject jsonObject = fileToJson("src/test/java/StockBackEndTest/Schema/response.json");

//        Iterator<?> iterator = jsonObject.keys();
//        while (iterator.hasNext())
//        {
//           String key = (String) iterator.next();
//           System.out.println(key);
//           System.out.println(jsonObject.get(key));
//        }

        Object object = jsonObject.get("arrayList");
//        System.out.println("****************************************");
//        System.out.println(object);
        if (object instanceof JSONObject)
        {
            System.out.println(((JSONArray)object).get(1));
        }else
        {
            System.out.println("Failed");
        }
    }
}
