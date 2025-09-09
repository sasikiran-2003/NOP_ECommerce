package tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import pages.RegisterPage;
import utils.PropertyReader;

public class RegisterTest extends BaseTest {

    private void openRegisterPage() {
        getDriver().get(PropertyReader.getProperty("url") + "/register");
    }

    @Test(priority = 1, description = "Verify user can register with valid credentials")
    public void testUserRegistrationWithValidCredentials() {
        RegisterPage registerPage = new RegisterPage(getDriver());
        openRegisterPage();

        registerPage.selectGender("Male");
        registerPage.enterFirstName("John");
        registerPage.enterLastName("Doe");
        registerPage.enterEmail("johndoe12329@example.com");
        registerPage.enterCompanyName("ABC Company");
        registerPage.enterPassword("password123");
        registerPage.enterConfirmPassword("password123");
        registerPage.clickRegisterButton();

        Assert.assertTrue(registerPage.isLogoutDisplayed(), "Registration was not successful");
        Assert.assertEquals(registerPage.getRegistrationResult(), "Your registration completed");
    }

    @Test(priority = 2, description = "Verify registration fails with existing email")
    public void testUserRegistrationWithExistingEmail() {
        RegisterPage registerPage = new RegisterPage(getDriver());
        openRegisterPage();

        registerPage.selectGender("Female");
        registerPage.enterFirstName("Jane");
        registerPage.enterLastName("Doe");
        registerPage.enterEmail("johndoe12329@example.com"); // Should already exist
        registerPage.enterCompanyName("XYZ Company");
        registerPage.enterPassword("password123");
        registerPage.enterConfirmPassword("password123");
        registerPage.clickRegisterButton();

        Assert.assertTrue(getDriver().getPageSource().contains("The specified email already exists"));
    }

    @Test(priority = 3, description = "Verify registration fails with password confirmation mismatch")
    public void testUserRegistrationWithPasswordMismatch() {
        RegisterPage registerPage = new RegisterPage(getDriver());
        openRegisterPage();

        registerPage.selectGender("Male");
        registerPage.enterFirstName("Bob");
        registerPage.enterLastName("Smith");
        registerPage.enterEmail("bobsmith" + System.currentTimeMillis() + "@example.com");
        registerPage.enterCompanyName("Test Company");
        registerPage.enterPassword("password123");
        registerPage.enterConfirmPassword("wrongpass");
        registerPage.clickRegisterButton();

        Assert.assertTrue(getDriver().getPageSource().contains("The password and confirmation password do not match"));
    }

    @Test(priority = 4, description = "Verify registration fails with weak password")
    public void testUserRegistrationWithWeakPassword() {
        RegisterPage registerPage = new RegisterPage(getDriver());
        openRegisterPage();

        registerPage.selectGender("Male");
        registerPage.enterFirstName("Tom");
        registerPage.enterLastName("Jones");
        registerPage.enterEmail("tomjones" + System.currentTimeMillis() + "@example.com");
        registerPage.enterCompanyName("Test Company");
        registerPage.enterPassword("weak");
        registerPage.enterConfirmPassword("weak");
        registerPage.clickRegisterButton();

        Assert.assertTrue(getDriver().getPageSource().contains("Password must meet the following rules"));
    }

    @Test(priority = 5, description = "Verify registration fails with empty required fields")
    public void testUserRegistrationWithEmptyFields() {
        RegisterPage registerPage = new RegisterPage(getDriver());
        openRegisterPage();

        registerPage.clickRegisterButton();

        Assert.assertTrue(getDriver().getPageSource().contains("First name is required"));
        Assert.assertTrue(getDriver().getPageSource().contains("Last name is required"));
        Assert.assertTrue(getDriver().getPageSource().contains("Email is required"));
        Assert.assertTrue(getDriver().getPageSource().contains("Password is required"));
    }
}
