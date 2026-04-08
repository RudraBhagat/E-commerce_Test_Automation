package com.ecommerce.automation.tests;

import com.ecommerce.automation.base.BaseTest;
import com.ecommerce.automation.config.ConfigReader;
import com.ecommerce.automation.pages.CartPage;
import com.ecommerce.automation.pages.CheckoutPage;
import com.ecommerce.automation.pages.LoginPage;
import com.ecommerce.automation.pages.ProductsPage;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class InvalidInputTests extends BaseTest {

    @DataProvider(name = "invalidLoginData")
    public Object[][] invalidLoginData() {
        return new Object[][]{
                {"", "", "Username is required"},
                {ConfigReader.get("username"), "", "Password is required"},
                {"invalid_user", "invalid_password", "Username and password do not match"},
                {ConfigReader.get("invalid.username"), ConfigReader.get("password"), "Sorry, this user has been locked out."}
        };
    }

    @Test(dataProvider = "invalidLoginData", description = "Verify error messages for invalid login input")
    public void invalidLoginShouldShowErrorMessage(String username, String password, String expectedMessagePart) {
        LoginPage loginPage = new LoginPage(driver);

        loginPage.login(username, password);

        Assert.assertTrue(
                loginPage.getErrorMessage().contains(expectedMessagePart),
                "Expected error message part was not found"
        );
    }

    @Test(description = "Verify checkout form validations for missing postal code")
    public void checkoutWithMissingPostalCodeShouldShowValidationError() {
        LoginPage loginPage = new LoginPage(driver);
        ProductsPage productsPage = new ProductsPage(driver);
        CartPage cartPage = new CartPage(driver);
        CheckoutPage checkoutPage = new CheckoutPage(driver);

        loginPage.login(ConfigReader.get("username"), ConfigReader.get("password"));
        productsPage.addBackpackToCart();
        productsPage.openCart();
        cartPage.clickCheckout();

        checkoutPage.enterCheckoutInformation(ConfigReader.get("first.name"), ConfigReader.get("last.name"), "");
        checkoutPage.continueCheckout();

        Assert.assertTrue(
                checkoutPage.getErrorMessage().contains("Postal Code is required"),
                "Expected checkout validation was not shown"
        );
    }
}
