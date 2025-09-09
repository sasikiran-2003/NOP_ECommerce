package tests;

import pages.CartPage;
import pages.ProductPage;
import tests.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;
import com.aventstack.extentreports.ExtentTest;


public class CartTest extends BaseTest {

    @Test(priority = 1, description = "Verify user can proceed to checkout from cart")
    public void testProceedToCheckoutFromCart() {
    	ExtentTest test = extent.createTest("Proceed to Checkout from Cart");
        extentTest.set(test);

        ProductPage productPage = new ProductPage(getDriver());
        CartPage cartPage = new CartPage(getDriver());

        // Add a simple product to cart
        getDriver().get("https://demo.nopcommerce.com/books");
        productPage.addProductToCart("First Prize Pies");
        productPage.closeNotification();

        // Navigate to shopping cart
        productPage.clickShoppingCart();

        // Proceed to checkout
        cartPage.clickCheckout();

        // Verify checkout page is displayed
        Assert.assertTrue(getDriver().getCurrentUrl().contains("checkout"),
                "Should be redirected to checkout page");
    }
}
