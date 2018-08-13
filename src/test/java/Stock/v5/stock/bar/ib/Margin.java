package Stock.v5.stock.bar.ib;

import com.xueqiu.qa.ApiBase.XqApi;
import com.xueqiu.qa.ExecutorUtility.HttpMethod;

public class Margin extends XqApi
{
    public Margin()
    {
        super("https://stock.xueqiu.com", "/v5/stock/bar/ib/margin.json", HttpMethod.get);
    }
}
