package com.ecommerce.automation.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class CartPage {
    private final WebDriver driver;

    private final By cartItemName = By.cssSelector(".inventory_item_name");
    private final By checkoutButton = By.id("checkout");

    public CartPage(WebDriver driver) {
        this.driver = driver;
    }

    public String getFirstCartItemName() {
        return driver.findElement(cartItemName).getText();
    }

    public void clickCheckout() {
        driver.findElement(checkoutButton).click();
    }
}
