import com.xueqiu.qa.ApiBase.XqApi;
import com.xueqiu.qa.ExecutorUtility.HttpMethod;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class main
{
    public static void main(String[] args)
    {
        XqApi xqApi = new XqApi("", "", HttpMethod.get, "");
        String path = "A/B/c/10080/[999]/items[AB0]/list[99]";
//        String[] pathList = path.split("/");
//        for(String i : pathList)
//        {
//            System.out.println(i);
//            System.out.println(xqApi.stringAnalyze(i));
//
//        }


        String regex = "(?<=\\[)(\\S+)(?=\\])";
        Pattern pattern = Pattern.compile (regex);
        Pattern numPattern = Pattern.compile("^[-\\+]?[\\d]*$");
        String[] pathList = path.split("/");
        for(String temp : pathList)
        {
            Matcher matcher = pattern.matcher(temp);
            if(matcher.find())
            {
/*
                System.out.println(matcher.group());
*/

                Matcher numMatcher = numPattern.matcher(matcher.group());

                if(numMatcher.find())
                {
/*
                    System.out.println(numMatcher.group());
*/
                    String[] bread = temp.split("\\[");
                    if(bread[0].length()==0)
                    {
                        System.out.println(numMatcher.group());
                    }

                }
            }
        }
    }
}
