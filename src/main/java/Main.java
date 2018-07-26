import com.xueqiu.qa.Utility;

public class Main
{
    public static void main(String[] args)
    {
        String plainText = "1234qwer";

        String secretText = Utility.encryptMD5(plainText);

        if (secretText.equals("62c8ad0a15d9d1ca38d5dee762a16e01"))
        {
            System.out.println("True");
        }

        System.out.println(secretText);
    }
}
