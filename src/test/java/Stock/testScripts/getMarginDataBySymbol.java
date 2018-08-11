package Stock.testScripts;

import Stock.v5.stock.bar.ib.api.Margin;
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
        this.margin.setJsonSchemaFilePath("src/test/java/Stock/v5/stock/bar/ib/schema/Margin.json");
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

        JSONObject jsonObject = this.margin.getResponse(parameterList, this.testAccount);

        Assert.assertTrue(this.margin.SchemaValidation(jsonObject));


        int short_num = (int)this.margin.getJsonProperty("short_available",
                this.margin.getJsonObject("data", jsonObject));

        Assert.assertTrue(short_num == 250000, "short_available number error");
    }

    @AfterTest
    public void tearDown()
    {
    }
}

