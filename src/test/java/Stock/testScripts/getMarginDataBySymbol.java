package Stock.testScripts;

import Stock.api.v5.stock.bar.ib.Margin;
import com.xueqiu.qa.GlobalDefine.TestAccount;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

public class getMarginDataBySymbol
{
    public Margin margin = null;
    public TestAccount testAccount = null;

    @BeforeTest
    public void setUp()
    {
        this.margin = new Margin();
        this.margin.setJsonSchemaFilePath("");
        this.testAccount = new TestAccount(
                "18515668408",
                "1234qwer",
                "86",
                "",
                "WiCimxpj5H",
                "TM69Da3uPkFzIdxpTEm6hp",
                "BDE2CF5F-5F88-4644-B5E5-87FB55841DC9",
                "password",
                "0",
                "BDE2CF5F-5F88-4644-B5E5-87FB55841DC9"
        );
    }

    @Test
    public void getMarginWithValidSymbol()
    {
        Map<String, String> parameterList = new HashMap<>();
        parameterList.put("symbol", "XONE");
        JSONObject jsonObject = margin.getResponse(parameterList, this.testAccount);
        System.out.println(jsonObject);
        Assert.assertTrue(true);
    }

    @AfterTest
    public void tearDown()
    {
        System.out.println("tearDown");
    }
}

