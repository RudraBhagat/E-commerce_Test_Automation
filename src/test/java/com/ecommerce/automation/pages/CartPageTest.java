package com.ecommerce.automation.pages;

import com.ecommerce.automation.support.WebDriverTestDouble;
import java.lang.reflect.Field;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;

public class CartPageTest {
    @Test
    public void shouldReadTheFirstCartItemName() throws Exception {
        WebDriverTestDouble.Context context = WebDriverTestDouble.newContext();
        CartPage cartPage = new CartPage(context.driver());
        context.register(locator(cartPage, "cartItemName"), "Sauce Labs Backpack");

        Assert.assertEquals(cartPage.getFirstCartItemName(), "Sauce Labs Backpack");
    }

    @Test
    public void shouldClickCheckoutButton() throws Exception {
        WebDriverTestDouble.Context context = WebDriverTestDouble.newContext();
        CartPage cartPage = new CartPage(context.driver());
        context.register(locator(cartPage, "checkoutButton"), "");

        cartPage.clickCheckout();

        Assert.assertEquals(context.element(locator(cartPage, "checkoutButton")).getClickCount(), 1);
    }

    private static By locator(Object target, String fieldName) throws Exception {
        Field field = target.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        return (By) field.get(target);
    }
}