package com.ecommerce.automation.pages;

import com.ecommerce.automation.support.WebDriverTestDouble;
import java.lang.reflect.Field;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;

public class CheckoutPageTest {
    @Test
    public void shouldPopulateCheckoutFormAndClickContinue() throws Exception {
        WebDriverTestDouble.Context context = WebDriverTestDouble.newContext();
        CheckoutPage checkoutPage = new CheckoutPage(context.driver());
        context.register(locator(checkoutPage, "firstNameInput"), "")
                .register(locator(checkoutPage, "lastNameInput"), "")
                .register(locator(checkoutPage, "postalCodeInput"), "")
                .register(locator(checkoutPage, "continueButton"), "");

        checkoutPage.enterCheckoutInformation("John", "Doe", "10001");
        checkoutPage.continueCheckout();

        Assert.assertEquals(context.element(locator(checkoutPage, "firstNameInput")).getClearCount(), 1);
        Assert.assertEquals(context.element(locator(checkoutPage, "firstNameInput")).getSentKeys(), java.util.List.of("John"));
        Assert.assertEquals(context.element(locator(checkoutPage, "lastNameInput")).getSentKeys(), java.util.List.of("Doe"));
        Assert.assertEquals(context.element(locator(checkoutPage, "postalCodeInput")).getSentKeys(), java.util.List.of("10001"));
        Assert.assertEquals(context.element(locator(checkoutPage, "continueButton")).getClickCount(), 1);
    }

    @Test
    public void shouldClickFinishAndReadCompletionAndValidationMessages() throws Exception {
        WebDriverTestDouble.Context context = WebDriverTestDouble.newContext();
        CheckoutPage checkoutPage = new CheckoutPage(context.driver());
        context.register(locator(checkoutPage, "finishButton"), "")
                .register(locator(checkoutPage, "successHeader"), "THANK YOU FOR YOUR ORDER")
                .register(locator(checkoutPage, "errorMessage"), "Postal Code is required");

        checkoutPage.finishCheckout();

        Assert.assertEquals(context.element(locator(checkoutPage, "finishButton")).getClickCount(), 1);
        Assert.assertEquals(checkoutPage.getSuccessHeader(), "THANK YOU FOR YOUR ORDER");
        Assert.assertTrue(checkoutPage.getErrorMessage().contains("Postal Code is required"));
    }

    private static By locator(Object target, String fieldName) throws Exception {
        Field field = target.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        return (By) field.get(target);
    }
}