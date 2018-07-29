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
        String url = "https://xueqiu.com/v4/stock/portfolio/list.json";
        TestAccount testAccount = new TestAccount();
        HttpUtility httpUtility = new HttpUtility();
        CookieStore cookieStore = Utility.setCookieStore(testAccount);

        Map<String, String> parameterList = new HashMap<>();
        parameterList.put("category","2");
        parameterList.put("system", "1");


        JSONObject jsonObject = httpUtility.get(url, parameterList,cookieStore);

        try {
            httpUtility.finalize();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }

        System.out.println(jsonObject.toString());
    }
}
