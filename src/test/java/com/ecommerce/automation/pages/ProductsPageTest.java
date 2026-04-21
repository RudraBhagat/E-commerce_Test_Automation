package com.ecommerce.automation.pages;

import com.ecommerce.automation.support.WebDriverTestDouble;
import java.lang.reflect.Field;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;

public class ProductsPageTest {
    @Test
    public void shouldReadTheProductsTitleAndCartBadge() throws Exception {
        WebDriverTestDouble.Context context = WebDriverTestDouble.newContext();
        ProductsPage productsPage = new ProductsPage(context.driver());
        context.register(locator(productsPage, "productTitle"), "Products")
                .register(locator(productsPage, "shoppingCartBadge"), "1");

        Assert.assertEquals(productsPage.getPageTitle(), "Products");
        Assert.assertEquals(productsPage.getCartBadgeCount(), "1");
    }

    @Test
    public void shouldClickAddToCartAndCartLinkButtons() throws Exception {
        WebDriverTestDouble.Context context = WebDriverTestDouble.newContext();
        ProductsPage productsPage = new ProductsPage(context.driver());
        context.register(locator(productsPage, "addBackpackToCartButton"), "")
                .register(locator(productsPage, "shoppingCartLink"), "");

        productsPage.addBackpackToCart();
        productsPage.openCart();

        Assert.assertEquals(context.element(locator(productsPage, "addBackpackToCartButton")).getClickCount(), 1);
        Assert.assertEquals(context.element(locator(productsPage, "shoppingCartLink")).getClickCount(), 1);
    }

    @Test
    public void shouldReturnEmptyBadgeWhenCartCountNotVisible() throws Exception {
        WebDriverTestDouble.Context context = WebDriverTestDouble.newContext();
        ProductsPage productsPage = new ProductsPage(context.driver());
        context.register(locator(productsPage, "shoppingCartBadge"), "");

        Assert.assertEquals(productsPage.getCartBadgeCount(), "");
    }

    private static By locator(Object target, String fieldName) throws Exception {
        Field field = target.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        return (By) field.get(target);
    }
}