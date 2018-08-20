package Stock.testScripts;

import com.xueqiu.qa.GlobalDefine.GlobalDefine;

import Stock.Stock;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

public class mainTest
{
  Stock stock = null;

    @BeforeTest
    public void setUp()
    {
         this.stock = new Stock();
    }

    @Test(enabled = true)
     public void getData() throws Exception
    {
        Map<String, String> parameterList= new HashMap<>();
        parameterList.put("symbol", "SZ000795");
        parameterList.put("begin","1441290117582");
        parameterList.put("period", "30m");

        this.stock.init("/v5/stock/chart/kline.json");
        this.stock.api.send(parameterList, GlobalDefine.testAccount);

/*
        this.stock.api.schemaValidation();
*/

        double ma20 = (double) stock.api.parse("data.item.[0].[4]");

        Assert.assertTrue((ma20 == 5.38), "ma20 value error: expected: 10.0; Actual: " + ma20);


    }


    @AfterTest
    public void tearDown()
    {

    }

}
