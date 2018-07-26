package StockBackEndTest;

import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class TestDemo
{
    @BeforeTest
    public void setUp()
    {
        System.out.println("setUp");
    }

    @Test
    public void firstTest()
    {
        System.out.println("First Test");
        Assert.assertTrue(true);
    }

    @Test(enabled = true)
    public void secondTest()
    {
        Assert.assertTrue(true);
        System.out.println("Second Test");
    }

    @AfterTest
    public void tearDown()
    {
        System.out.println("tearDown");
    }
}
