import com.xueqiu.qa.executor.HttpMethod;
import com.xueqiu.qa.executor.SchemaValidationExecutor;
import com.xueqiu.qa.testAccount.TestAccount;
import org.apache.http.client.CookieStore;

import java.util.HashMap;
import java.util.Map;

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

/*
        CookieStore cookieStore = testAccount.getCookieStore(testAccount, "stock.xueqiu.com");
*/


        String requestURL = "https://xueqiu.com/v4/stock/portfolio/list.json";

        String domainURL = "xueqiu.com";
        String jsonSchemaFilePath = "src/main/java/getAllStocksPortfoliosWithArrayElement.json";

        Map<String, String> parameterList = new HashMap<>();
        parameterList.put("category", "2");
        parameterList.put("system", "1");

        SchemaValidationExecutor schemaValidationExecutor = new SchemaValidationExecutor(requestURL, domainURL, parameterList,
                HttpMethod.get, testAccount, false, jsonSchemaFilePath);

        if(schemaValidationExecutor.validate())
        {
            System.out.println("true");
        }else
        {
            System.out.println("false");
        }
    }
}
