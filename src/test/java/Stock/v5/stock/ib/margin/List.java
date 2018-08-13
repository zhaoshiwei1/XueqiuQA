package Stock.v5.stock.ib.margin;

import com.xueqiu.qa.ApiBase.XqApi;
import com.xueqiu.qa.ExecutorUtility.HttpMethod;

public class List extends XqApi
{
    public List()
    {
        super("https://stock.xueqiu.com", "/v5/stock/ib/margin/list.json", HttpMethod.get);
    }
}

