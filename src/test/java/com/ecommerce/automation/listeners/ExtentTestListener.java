package com.ecommerce.automation.listeners;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.Status;
import com.ecommerce.automation.base.DriverFactory;
import com.ecommerce.automation.utils.ExtentManager;
import com.ecommerce.automation.utils.ExtentTestManager;
import com.ecommerce.automation.utils.ScreenshotUtil;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class ExtentTestListener implements ITestListener {
    private static final ExtentReports EXTENT = ExtentManager.getInstance();

    @Override
    public void onTestStart(ITestResult result) {
        ExtentTestManager.setTest(EXTENT.createTest(result.getMethod().getMethodName()));
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        ExtentTestManager.getTest().log(Status.PASS, "Test passed");
        ExtentTestManager.unload();
    }

    @Override
    public void onTestFailure(ITestResult result) {
        ExtentTestManager.getTest().log(Status.FAIL, result.getThrowable());
        try {
            WebDriver driver = DriverFactory.getDriver();
            String screenshotPath = ScreenshotUtil.takeScreenshot(driver, result.getMethod().getMethodName());
            if (screenshotPath != null) {
                ExtentTestManager.getTest().addScreenCaptureFromPath(screenshotPath);
            }
        } catch (Exception ignored) {
            ExtentTestManager.getTest().log(Status.WARNING, "Unable to capture screenshot.");
        }
        ExtentTestManager.unload();
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        ExtentTestManager.getTest().log(Status.SKIP, "Test skipped");
        ExtentTestManager.unload();
    }

    @Override
    public void onFinish(ITestContext context) {
        EXTENT.flush();
    }
}
