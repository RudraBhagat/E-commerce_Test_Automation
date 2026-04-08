package com.ecommerce.automation.pages;

import com.ecommerce.automation.support.WebDriverTestDouble;
import java.lang.reflect.Field;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;

public class LoginPageTest {
    @Test
    public void loginShouldClearAndPopulateCredentialsBeforeClickingLogin() throws Exception {
        WebDriverTestDouble.Context context = WebDriverTestDouble.newContext();
        LoginPage loginPage = new LoginPage(context.driver());
        context.register(locator(loginPage, "usernameInput"), "")
                .register(locator(loginPage, "passwordInput"), "")
                .register(locator(loginPage, "loginButton"), "");

        loginPage.login("standard_user", "secret_sauce");

        Assert.assertEquals(context.element(locator(loginPage, "usernameInput")).getClearCount(), 1);
        Assert.assertEquals(context.element(locator(loginPage, "usernameInput")).getSentKeys(), java.util.List.of("standard_user"));
        Assert.assertEquals(context.element(locator(loginPage, "passwordInput")).getClearCount(), 1);
        Assert.assertEquals(context.element(locator(loginPage, "passwordInput")).getSentKeys(), java.util.List.of("secret_sauce"));
        Assert.assertEquals(context.element(locator(loginPage, "loginButton")).getClickCount(), 1);
    }

    @Test
    public void getErrorMessageShouldReturnVisibleErrorText() throws Exception {
        WebDriverTestDouble.Context context = WebDriverTestDouble.newContext();
        LoginPage loginPage = new LoginPage(context.driver());
        context.register(locator(loginPage, "errorMessage"), "Epic sadface: Password is required");

        Assert.assertTrue(loginPage.getErrorMessage().contains("Password is required"));
    }

    private static By locator(Object target, String fieldName) throws Exception {
        Field field = target.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        return (By) field.get(target);
    }
}