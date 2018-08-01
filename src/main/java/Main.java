import com.xueqiu.qa.testAccount.TestAccount;

public class Main
{
    public static void main(String[] args)
    {
        TestAccount testAccount = new TestAccount(
                "18515668408",
                "1234qwer",
                "86",
                "",
                "WiCimxpj5H",
                "TM69Da3uPkFzIdxpTEm6hp",
                "BDE2CF5F-5F88-4644-B5E5-87FB55841DC9",
                "password",
                "0",
                "BDE2CF5F-5F88-4644-B5E5-87FB55841DC9");
        System.out.println(testAccount.getCookieStore(testAccount, "stock.xueqiu.com").toString());
    }
}
