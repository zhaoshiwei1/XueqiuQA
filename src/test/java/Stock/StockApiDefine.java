package Stock;

public class StockApiDefine
{
    public static final String[][] stock_api =
    {
            {"https://stock.xueqiu.com", "/v5/stock/bar/ib/margin.json", "get", "src/test/java/Stock/JsonSchema/margin.json"},
            {"https://stock.xueqiu.com", "/v5/stock/ib/margin/list.json", "get", "src/test/java/Stock/JsonSchema/list.json"},
            {"https://stock.xueqiu.com", "/v5/stock/capital/flow.json", "get", ""},
            {"https://stock.xueqiu.com", "/v5/stock/chart/kline.json", "get", ""}
    };
}
