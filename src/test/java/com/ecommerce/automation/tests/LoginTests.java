package com.ecommerce.automation.tests;

import com.ecommerce.automation.base.BaseTest;
import com.ecommerce.automation.config.ConfigReader;
import com.ecommerce.automation.pages.LoginPage;
import com.ecommerce.automation.pages.ProductsPage;
import org.testng.Assert;
import org.testng.annotations.Test;

public class LoginTests extends BaseTest {

    @Test(description = "Verify user can log in with valid credentials")
    public void validLoginShouldNavigateToProductsPage() {
        LoginPage loginPage = new LoginPage(driver);
        ProductsPage productsPage = new ProductsPage(driver);

        loginPage.login(ConfigReader.get("username"), ConfigReader.get("password"));

        Assert.assertEquals(productsPage.getPageTitle(), "Products", "User was not redirected to Products page");
    }
}
