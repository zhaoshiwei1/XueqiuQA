import com.xueqiu.qa.TestAccount;
import com.xueqiu.qa.Utility;

public class Main
{
    public static void main(String[] args)
    {
        TestAccount testAccount = new TestAccount();
        System.out.println(Utility.getXqToken(testAccount));

    }
}
