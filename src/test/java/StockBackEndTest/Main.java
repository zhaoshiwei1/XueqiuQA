package StockBackEndTest;

import com.xueqiu.qa.GlobalDefine.TestAccount;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
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

        JSONArray jsonArray = (JSONArray) jsonObject.get("hook");

        System.out.println(jsonArray.get(2));
    }
}
