package com.ecommerce.automation.utils;

import com.aventstack.extentreports.ExtentReports;
import org.testng.Assert;
import org.testng.annotations.Test;

public class ExtentManagerTest {
    @Test
    public void shouldReturnSameExtentReportsInstance() {
        ExtentReports first = ExtentManager.getInstance();
        ExtentReports second = ExtentManager.getInstance();

        Assert.assertNotNull(first);
        Assert.assertSame(second, first);
    }
}