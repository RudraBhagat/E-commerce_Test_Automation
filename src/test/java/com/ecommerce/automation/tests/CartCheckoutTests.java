package com.ecommerce.automation.tests;

import com.ecommerce.automation.base.BaseTest;
import com.ecommerce.automation.config.ConfigReader;
import com.ecommerce.automation.pages.CartPage;
import com.ecommerce.automation.pages.CheckoutPage;
import com.ecommerce.automation.pages.LoginPage;
import com.ecommerce.automation.pages.ProductsPage;
import org.testng.Assert;
import org.testng.annotations.Test;

public class CartCheckoutTests extends BaseTest {

    @Test(description = "Verify add to cart and checkout flow works successfully")
    public void addToCartAndCheckoutShouldCompleteSuccessfully() {
        LoginPage loginPage = new LoginPage(driver);
        ProductsPage productsPage = new ProductsPage(driver);
        CartPage cartPage = new CartPage(driver);
        CheckoutPage checkoutPage = new CheckoutPage(driver);

        loginPage.login(ConfigReader.get("username"), ConfigReader.get("password"));
        Assert.assertEquals(productsPage.getPageTitle(), "Products");

        productsPage.addBackpackToCart();
        Assert.assertEquals(productsPage.getCartBadgeCount(), "1", "Cart badge count should be 1");

        productsPage.openCart();
        Assert.assertEquals(cartPage.getFirstCartItemName(), "Sauce Labs Backpack", "Expected item not present in cart");

        cartPage.clickCheckout();
        checkoutPage.enterCheckoutInformation(
                ConfigReader.get("first.name"),
                ConfigReader.get("last.name"),
                ConfigReader.get("postal.code")
        );
        checkoutPage.continueCheckout();
        checkoutPage.finishCheckout();

        Assert.assertEquals(
                checkoutPage.getSuccessHeader(),
                "Thank you for your order!",
                "Checkout completion message mismatch"
        );
    }
}
