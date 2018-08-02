import com.xueqiu.qa.executor.HttpMethod;
import com.xueqiu.qa.executor.SchemaValidationExecutor;
import com.xueqiu.qa.testAccount.TestAccount;

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
        String requestURL = "https://stock.xueqiu.com/v4/stock/portfolio/list.json";

        String domainURL = "stock.xueqiu.com";
        String jsonSchemaFilePath = "src/main/java/getAllStocksPortfoliosWithArrayElement.json";

        Map<String, String> parameterList = new HashMap<>();
        parameterList.put("category", "2");
        parameterList.put("system", "1");

        SchemaValidationExecutor schemaValidationExecutor = new SchemaValidationExecutor(requestURL, domainURL, parameterList,
                HttpMethod.get, testAccount, true, jsonSchemaFilePath);

        if(schemaValidationExecutor.validate())
        {
            System.out.println("true");
        }else
        {
            System.out.println("false");
        }
    }
}
