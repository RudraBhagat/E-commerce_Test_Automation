package com.ecommerce.automation.utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

public final class ScreenshotUtil {
    private ScreenshotUtil() {
    }

    public static String takeScreenshot(WebDriver driver, String testName) {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss_SSS"));
        String fileName = testName + "_" + timestamp + ".png";

        Path screenshotDir = Path.of(System.getProperty("user.dir"), "reports", "screenshots");
        try {
            Files.createDirectories(screenshotDir);
            File sourceFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            Path destination = screenshotDir.resolve(fileName);
            Files.copy(sourceFile.toPath(), destination, StandardCopyOption.REPLACE_EXISTING);
            return "screenshots/" + fileName;
        } catch (IOException e) {
            return null;
        }
    }
}
