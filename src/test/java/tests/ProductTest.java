package tests;

import pages.ProductPage;
import tests.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;
import com.aventstack.extentreports.ExtentTest;

import java.util.Arrays;

public class ProductTest extends BaseTest {

    @Test(priority = 1, description = "Verify user can view product details")
    public void testViewProductDetails() {
        ExtentTest test = extent.createTest("View Product Details");
        extentTest.set(test);

        ProductPage productPage = new ProductPage(getDriver());
        productPage.navigateToProduct("build-your-own-computer");

        Assert.assertTrue(getDriver().getTitle().contains("Build your own computer"));
        Assert.assertTrue(getDriver().getCurrentUrl().contains("build-your-own-computer"));
    }

    @Test(priority = 2, description = "Verify user can configure and add product to cart")
    public void testAddConfigurableProductToCart() {
        ExtentTest test = extent.createTest("Add Configurable Product to Cart");
        extentTest.set(test);

        ProductPage productPage = new ProductPage(getDriver());
        getDriver().get("https://demo.nopcommerce.com/build-your-own-computer");

        productPage.configureAndAddComputerToCart(
                "8GB [+$60.00]",
                "400 GB",
                "Premium",
                Arrays.asList("Microsoft Office", "Total Commander")
        );

        productPage.waitForNotification();
        Assert.assertTrue(productPage.getNotificationMessage().contains("The product has been added to your shopping cart"));
        productPage.closeNotification();
    }

    @Test(priority = 3, description = "Verify user can add simple product to cart from category page")
    public void testAddSimpleProductToCartFromCategory() {
        ExtentTest test = extent.createTest("Add Simple Product to Cart from Category Page");
        extentTest.set(test);

        ProductPage productPage = new ProductPage(getDriver());
        productPage.navigateToProductCategory("books");
        productPage.addProductToCart("First Prize Pies");

        productPage.waitForNotification();
        Assert.assertTrue(productPage.getNotificationMessage().contains("The product has been added to your shopping cart"));
        productPage.closeNotification();
    }

    @Test(priority = 4, description = "Verify product search functionality")
    public void testProductSearchFunctionality() {
        ExtentTest test = extent.createTest("Product Search Functionality");
        extentTest.set(test);

        ProductPage productPage = new ProductPage(getDriver());
        getDriver().get("https://demo.nopcommerce.com/");
        productPage.searchForProduct("computer");

        Assert.assertTrue(getDriver().getTitle().contains("Search"));
        Assert.assertTrue(getDriver().getCurrentUrl().contains("search"));
        Assert.assertTrue(productPage.areSearchResultsDisplayed("computer"));
    }

    @Test(priority = 5, description = "Verify product filter functionality")
    public void testProductFilterFunctionality() {
        ExtentTest test = extent.createTest("Product Filter Functionality");
        extentTest.set(test);

        ProductPage productPage = new ProductPage(getDriver());
        productPage.navigateToProductCategory("computers");
        productPage.applyFilter("Desktops");

        Assert.assertTrue(getDriver().getCurrentUrl().contains("desktops"));
        Assert.assertTrue(getDriver().getPageSource().contains("Desktops"));
        Assert.assertTrue(productPage.areFilteredProductsDisplayed());
    }
}
