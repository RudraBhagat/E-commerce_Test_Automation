package com.ecommerce.automation.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class ProductsPage {
    private final WebDriver driver;

    private final By productTitle = By.cssSelector("span.title");
    private final By addBackpackToCartButton = By.id("add-to-cart-sauce-labs-backpack");
    private final By shoppingCartBadge = By.cssSelector("span.shopping_cart_badge");
    private final By shoppingCartLink = By.cssSelector("a.shopping_cart_link");

    public ProductsPage(WebDriver driver) {
        this.driver = driver;
    }

    public String getPageTitle() {
        return driver.findElement(productTitle).getText();
    }

    public void addBackpackToCart() {
        driver.findElement(addBackpackToCartButton).click();
    }

    public String getCartBadgeCount() {
        return driver.findElement(shoppingCartBadge).getText();
    }

    public void openCart() {
        driver.findElement(shoppingCartLink).click();
    }
}
