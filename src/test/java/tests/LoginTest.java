package tests;

import pages.LoginPage;
import tests.BaseTest;
import utils.PropertyReader;
import org.testng.Assert;
import org.testng.annotations.Test;

public class LoginTest extends BaseTest {
    
    @Test(priority = 1, description = "Verify user can login with valid credentials")
    public void testUserLoginWithValidCredentials() {
        LoginPage loginPage = new LoginPage(getDriver());
        getDriver().get("https://demo.nopcommerce.com/login");
        loginPage.enterEmail(PropertyReader.getProperty("valid.email"));
        loginPage.enterPassword(PropertyReader.getProperty("valid.password"));
        loginPage.clickLoginButton();
        Assert.assertTrue(loginPage.isLogoutDisplayed(), "Login was not successful");
        Assert.assertTrue(loginPage.isMyAccountDisplayed(), "My Account link is not displayed");
    }
    
    @Test(priority = 2, description = "Verify login fails with invalid credentials")
    public void testUserLoginWithInvalidCredentials() {
        LoginPage loginPage = new LoginPage(getDriver());
        getDriver().get("https://demo.nopcommerce.com/login");
        loginPage.enterEmail("invalid@example.com");
        loginPage.enterPassword("wrongpassword");
        loginPage.clickLoginButton();
        Assert.assertTrue(loginPage.getErrorMessage().contains("Login was unsuccessful"));
    }
    
    @Test(priority = 3, description = "Verify user can logout successfully")
    public void testUserLogout() {
        LoginPage loginPage = new LoginPage(getDriver());
        getDriver().get("https://demo.nopcommerce.com/login");
        loginPage.enterEmail(PropertyReader.getProperty("valid.email"));
        loginPage.enterPassword(PropertyReader.getProperty("valid.password"));
        loginPage.clickLoginButton();
        loginPage.clickLogout();
        Assert.assertTrue(getDriver().getCurrentUrl().contains("https://demo.nopcommerce.com"));
    }
    
    @Test(priority = 4, description = "Verify login fails with empty credentials")
    public void testLoginWithEmptyCredentials() {
        LoginPage loginPage = new LoginPage(getDriver());
        getDriver().get("https://demo.nopcommerce.com/login");
        loginPage.clickLoginButton();
        String actualError = loginPage.getEmailErrorMessage();
        Assert.assertEquals(actualError, "Please enter your email", "Email validation message mismatch!");
    }
    
    @Test(priority = 5, description = "Verify login fails with valid email but empty password")
    public void testLoginWithEmptyPassword() {
        LoginPage loginPage = new LoginPage(getDriver());
        getDriver().get("https://demo.nopcommerce.com/login");
        loginPage.enterEmail(PropertyReader.getProperty("valid.email"));
        loginPage.clickLoginButton();
        Assert.assertTrue(loginPage.getErrorMessage().contains("The credentials provided are incorrect"));
    }
}
