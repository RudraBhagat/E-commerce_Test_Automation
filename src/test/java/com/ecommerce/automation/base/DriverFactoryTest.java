package com.ecommerce.automation.base;

import com.ecommerce.automation.support.WebDriverTestDouble;
import java.lang.reflect.Field;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

public class DriverFactoryTest {
    @AfterMethod(alwaysRun = true)
    public void cleanUp() throws Exception {
        clearDriverThreadLocal();
    }

    @Test(expectedExceptions = IllegalStateException.class)
    public void getDriverShouldFailWhenDriverWasNotInitialised() {
        DriverFactory.getDriver();
    }

    @Test
    public void getDriverShouldReturnTheThreadLocalDriver() throws Exception {
        WebDriverTestDouble.Context context = WebDriverTestDouble.newContext();
        setDriverThreadLocal(context.driver());

        Assert.assertSame(DriverFactory.getDriver(), context.driver());
    }

    @Test
    public void quitDriverShouldCloseAndClearTheThreadLocalDriver() throws Exception {
        WebDriverTestDouble.Context context = WebDriverTestDouble.newContext();
        setDriverThreadLocal(context.driver());

        DriverFactory.quitDriver();

        Assert.assertEquals(context.quitCount(), 1);
        Assert.assertNull(driverThreadLocal().get());
    }

    @Test
    public void quitDriverShouldIgnoreEmptyThreadLocal() {
        DriverFactory.quitDriver();
        Assert.assertTrue(true);
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