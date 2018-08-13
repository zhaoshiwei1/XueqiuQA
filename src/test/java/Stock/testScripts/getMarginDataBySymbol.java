package Stock.testScripts;

import Stock.v5.stock.bar.ib.Margin;
import Stock.v5.stock.ib.margin.List;
import com.xueqiu.qa.GlobalDefine.TestAccount;
import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class getMarginDataBySymbol
{
    public Margin margin = null;
    public TestAccount testAccount = null;
    public List list = null;


    @BeforeTest
    public void setUp()
    {
        this.margin = new Margin();
        this.margin.setJsonSchemaFilePath("src/test/java/Stock/v5/stock/bar/ib/Margin.json");

        this.list = new List();
        this.list.setJsonSchemaFilePath("src/test/java/Stock/v5/stock/ib/margin/List.json");


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

/*
    @Test
    public void checkIfInMarginList()
    {
        Map<String, String> marginParameter = new HashMap<>();
        marginParameter.put("symbol","SDRL");

        JSONObject jsonObject = this.margin.getResponse(marginParameter, testAccount);

        Assert.assertTrue(this.margin.SchemaValidation(jsonObject), "Schema Validation Failed");
        Map<String, String> marginListParameter = new HashMap<>();
        marginListParameter.put("page", "1");
        marginListParameter.put("size", "30");
        marginListParameter.put("order_by", "symbol");
        marginListParameter.put("order", "asc");
        marginListParameter.put("exchange_area", "US");
        marginListParameter.put("type", "all");


        int page = 1;
        Boolean ifFound = false;

        while(page == 1 || !(ifFound))
        {
            marginListParameter.put("page", String.valueOf(page));
            JSONObject currentPage = this.list.getResponse(marginListParameter, testAccount);
            System.out.println("Request Page: " + page);
            System.out.println(currentPage);
            marginListParameter.put("page", String.valueOf(page+1));
            JSONObject nextPage = this.list.getResponse(marginListParameter, testAccount);
            if(currentPage.equals(nextPage) || this.list.getJsonArray("items",
                    this.list.getJsonObject("data", nextPage)).length()==0)
            {
                ifFound = false;
                break;
            }

            JSONObject temp = this.list.getJsonObject("data", currentPage);
            JSONArray items = this.list.getJsonArray("items", temp);
            ArrayList<String> list = new ArrayList<>();
            for (int i = 0; i<items.length(); i++)
            {
                list.add((String) this.list.getJsonProperty("symbol", (JSONObject) items.get(i)));
            }
            if(checkIfInList("SDRL", list))
            {
                ifFound = true;
                break;
            }
            page ++;
        }

        Assert.assertTrue(ifFound, "Cannot Find SDRL in Margin List");

    }

*/

    public boolean checkIfInList(String symbol, ArrayList<String> list)
    {
        for(String i : list)
        {
            if (i.equals(symbol))
            {
                return true;
            }
        }
        return false;

    }

    @AfterTest
    public void tearDown()
    {

    }
}

