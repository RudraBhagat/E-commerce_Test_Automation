package com.ecommerce.automation.utils;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

public class ScreenshotUtilTest {
    @Test
    public void shouldCaptureScreenshotAndReturnRelativePath() throws Exception {
        Path screenshotSource = Files.createTempFile("screenshot-source", ".png");
        Files.writeString(screenshotSource, "fake screenshot", StandardCharsets.UTF_8);

        WebDriver driver = createScreenshotDriver(screenshotSource.toFile());
        String relativePath = ScreenshotUtil.takeScreenshot(driver, "screenshot-test");

        Assert.assertNotNull(relativePath);
        Assert.assertTrue(relativePath.startsWith("screenshots/"));
        Assert.assertTrue(relativePath.endsWith(".png"));

        Path destination = Path.of(System.getProperty("user.dir"), "reports").resolve(relativePath);
        try {
            Assert.assertTrue(Files.exists(destination));
        } finally {
            Files.deleteIfExists(destination);
            Files.deleteIfExists(screenshotSource);
        }
    }

    private static WebDriver createScreenshotDriver(File screenshotFile) {
        InvocationHandler handler = new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                if ("getScreenshotAs".equals(method.getName())) {
                    return screenshotFile;
                }
                Class<?> returnType = method.getReturnType();
                if (returnType.equals(Boolean.TYPE)) {
                    return false;
                }
                if (returnType.equals(Integer.TYPE)) {
                    return 0;
                }
                if (returnType.equals(Long.TYPE)) {
                    return 0L;
                }
                if (returnType.equals(Double.TYPE)) {
                    return 0.0d;
                }
                if (returnType.equals(Float.TYPE)) {
                    return 0.0f;
                }
                if (returnType.equals(Duration.class)) {
                    return Duration.ZERO;
                }
                return null;
            }
        };

        return (WebDriver) Proxy.newProxyInstance(
                ScreenshotUtilTest.class.getClassLoader(),
                new Class<?>[]{WebDriver.class, TakesScreenshot.class},
                handler);
    }
}