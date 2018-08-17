package com.xueqiu.qa.GlobalDefine;

import java.util.ArrayList;

public class GlobalDefine
{
    /*
    * login url
    * */
    public static final String loginURL = "https://api.xueqiu.com/provider/oauth/token";

    /*
     *Client cookie setting key
     * */
    public static final Integer cookieVersion = 0;
    public static final String path = "/";
    public static final String tokenName = "xq_a_token";

    /*
    * Token String name
    * */
    public static final String access_token = "access_token";

    public static final String separator = "%&%";

    public static TestAccount testAccount = new TestAccount(
            "18515668408",
            "1234qwer",
            "86",
            "",
            "WiCimxpj5H",
            "TM69Da3uPkFzIdxpTEm6hp",
            "F63B3A17-54F0-4DC3-8B82-3390AA1751C2",
            "password",
            "0",
            "F63B3A17-54F0-4DC3-8B82-3390AA1751C2"
    );
}
