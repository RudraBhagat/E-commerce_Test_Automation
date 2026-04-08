package com.ecommerce.automation.base;

import com.ecommerce.automation.support.WebDriverTestDouble;
import java.lang.reflect.Field;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

public class BaseTestTest {
    private static final class TestBaseTest extends BaseTest {
    }

    @AfterMethod(alwaysRun = true)
    public void cleanUp() throws Exception {
        clearDriverThreadLocal();
    }

    @Test
    public void tearDownShouldNotFailWhenDriverIsNotInitialised() {
        new TestBaseTest().tearDown();
        Assert.assertTrue(true);
    }

    @Test
    public void tearDownShouldQuitAndClearInitialisedDriver() throws Exception {
        WebDriverTestDouble.Context context = WebDriverTestDouble.newContext();
        setDriverThreadLocal(context.driver());

        new TestBaseTest().tearDown();

        Assert.assertEquals(context.quitCount(), 1);
        Assert.assertNull(driverThreadLocal().get());
    }

    private static ThreadLocal<WebDriver> driverThreadLocal() throws Exception {
        Field field = DriverFactory.class.getDeclaredField("DRIVER");
        field.setAccessible(true);
        @SuppressWarnings("unchecked")
        ThreadLocal<WebDriver> threadLocal = (ThreadLocal<WebDriver>) field.get(null);
        return threadLocal;
    }

    private static void setDriverThreadLocal(WebDriver driver) throws Exception {
        driverThreadLocal().set(driver);
    }

    private static void clearDriverThreadLocal() throws Exception {
        driverThreadLocal().remove();
    }
}