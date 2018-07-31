import com.xueqiu.qa.HttpUtility;
import com.xueqiu.qa.TestAccount;
import com.xueqiu.qa.Utility;
import org.apache.http.client.CookieStore;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Main
{
    public static void main(String[] args)
    {
        String url = "https://stock.xueqiu.com/v5/stock/finance/cn/business.json";
        String domainURL = "stock.xueqiu.com";
        TestAccount testAccount = new TestAccount();
        HttpUtility httpUtility = new HttpUtility();
        CookieStore cookieStore = Utility.setCookieStore(testAccount, domainURL);

        Map<String, String> parameterList = new HashMap<>();
        parameterList.put("symbol","SH600519");

        JSONObject jsonObject = httpUtility.get(url, parameterList,cookieStore);

        try {
            httpUtility.finalize();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }

        System.out.println(jsonObject.toString());
    }
}
