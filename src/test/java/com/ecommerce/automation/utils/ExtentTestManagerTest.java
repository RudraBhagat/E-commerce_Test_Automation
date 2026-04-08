package com.ecommerce.automation.utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import org.testng.Assert;
import org.testng.annotations.Test;

public class ExtentTestManagerTest {
    @Test
    public void shouldStoreAndClearThreadLocalTestInstance() {
        ExtentReports reports = new ExtentReports();
        ExtentTest extentTest = reports.createTest("extent-test-manager-test");

        ExtentTestManager.setTest(extentTest);

        Assert.assertSame(ExtentTestManager.getTest(), extentTest);

        ExtentTestManager.unload();
        Assert.assertNull(ExtentTestManager.getTest());
    }
}