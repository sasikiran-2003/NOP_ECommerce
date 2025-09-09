package stepdefinitions;

import pages.LoginPage;
import tests.BaseTest;
import utils.PropertyReader;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.testng.Assert;
import stepdefinitions.Hooks;

public class LoginSteps  {
    private LoginPage loginPage;

    @Given("I am on the login page")
    public void i_am_on_the_login_page() {
        Hooks.driver.get("https://demo.nopcommerce.com/login");
        loginPage = new LoginPage(Hooks.driver);
    }

    @Given("I am logged in")
    public void i_am_logged_in() {
    	Hooks.driver.get("https://demo.nopcommerce.com/login");
        loginPage = new LoginPage(Hooks.driver);
        loginPage.enterEmail(PropertyReader.getProperty("valid.email"));
        loginPage.enterPassword(PropertyReader.getProperty("valid.password"));
        loginPage.clickLoginButton();
    }

    @When("I enter login email {string}")
    public void i_enter_login_email(String email) {
        loginPage.enterEmail(email);
    }

    @When("I enter login password {string}")
    public void i_enter_login_password(String password) {
        loginPage.enterPassword(password);
    }

    @When("I click the login button")
    public void i_click_the_login_button() {
        loginPage.clickLoginButton();
    }

    @When("I click the logout link")
    public void i_click_the_logout_link() {
        loginPage.clickLogout();
    }

    @Then("I should see the logout link")
    public void i_should_see_the_logout_link() {
        Assert.assertTrue(loginPage.isLogoutDisplayed());
    }

    @Then("I should see the my account link")
    public void i_should_see_the_my_account_link() {
        Assert.assertTrue(loginPage.isMyAccountDisplayed());
    }

    @Then("I should see an error message about login failure")
    public void i_should_see_an_error_message_about_login_failure() {
        Assert.assertTrue(loginPage.getErrorMessage().contains("Login was unsuccessful"));
    }

    @Then("I should be redirected to the home page")
    public void i_should_be_redirected_to_the_home_page() {
        Assert.assertEquals(Hooks.driver.getCurrentUrl(), "https://demo.nopcommerce.com/");
    }
}
