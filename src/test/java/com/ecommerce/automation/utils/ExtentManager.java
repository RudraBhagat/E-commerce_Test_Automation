package com.ecommerce.automation.utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public final class ExtentManager {
    private static ExtentReports extentReports;

    private ExtentManager() {
    }

    public static synchronized ExtentReports getInstance() {
        if (extentReports == null) {
            String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
            String reportPath = System.getProperty("user.dir") + File.separator + "reports"
                    + File.separator + "ExtentReport_" + timestamp + ".html";

            ExtentSparkReporter sparkReporter = new ExtentSparkReporter(reportPath);
            sparkReporter.config().setDocumentTitle("E-commerce Test Automation Report");
            sparkReporter.config().setReportName("Functional UI Test Results");

            extentReports = new ExtentReports();
            extentReports.attachReporter(sparkReporter);
            extentReports.setSystemInfo("Environment", System.getProperty("environment", "qa"));
            extentReports.setSystemInfo("Framework", "Selenium + TestNG");
            extentReports.setSystemInfo("OS", System.getProperty("os.name"));
        }
        return extentReports;
    }
}
