import com.xueqiu.qa.ApiBase.XqApi;
import com.xueqiu.qa.ExecutorUtility.HttpMethod;
import com.xueqiu.qa.ExecutorUtility.Utility;
import org.json.JSONObject;

public class main
{
    public static void main(String[] args)
    {
        XqApi xqApi = new XqApi("", "", HttpMethod.get, "");
        String path = "data.items.[3].quote.time";
        JSONObject jsonObject = Utility.fileToJson("src/test/java/Stock/JsonSchema/list.json");
        System.out.println(xqApi.parse(path, jsonObject));
    }
}
