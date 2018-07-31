package com.xueqiu.qa;

import java.util.Map;

public class Executor
{
    String URL = null;
    String domainURL = null;
    Map<String, String> parameterList = null;
    TestAccount testAccount = null;

    public Executor(String URL, String domainURL, Map<String, String> parameterList, TestAccount testAccount)
    {
        this.URL = URL;
        this.domainURL = domainURL;
        this.parameterList = parameterList;
        this.testAccount = testAccount;
    }

    public String doTest()
    {
        return null;
    }
}
