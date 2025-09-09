package stepdefinitions;

import pages.RegisterPage;
import tests.BaseTest;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import stepdefinitions.Hooks;

public class RegisterSteps {
    private RegisterPage registerPage;
    private String timestamp = String.valueOf(System.currentTimeMillis());

    
    @Given("I am on the registration page")
    public void i_am_on_the_registration_page() {
        Hooks.driver.get("https://demo.nopcommerce.com/register");
        registerPage = new RegisterPage(Hooks.driver);

        // ✅ Ensure page is loaded before moving on
        new WebDriverWait(Hooks.driver, Duration.ofSeconds(10))
            .until(ExpectedConditions.presenceOfElementLocated(By.id("gender-male")));
    }


    @When("I select gender {string}")
    public void i_select_gender(String gender) {
        registerPage.selectGender(gender);
    }

    @When("I enter first name {string}")
    public void i_enter_first_name(String firstName) {
        registerPage.enterFirstName(firstName);
    }

    @When("I enter last name {string}")
    public void i_enter_last_name(String lastName) {
        registerPage.enterLastName(lastName);
    }

    @When("I enter register email {string}")
    public void i_enter_register_email(String email) {
        if (email.contains("<timestamp>")) {
            email = email.replace("<timestamp>", timestamp);
        }
        registerPage.enterEmail(email);
    }

    @When("I enter company name {string}")
    public void i_enter_company_name(String company) {
        registerPage.enterCompanyName(company);
    }

    @When("I enter register password {string}")
    public void i_enter_register_password(String password) {
        registerPage.enterPassword(password);
    }

    @When("I enter confirm password {string}")
    public void i_enter_confirm_password(String password) {
        registerPage.enterConfirmPassword(password);
    }

    @When("I click the register button")
    public void i_click_the_register_button() {
        registerPage.clickRegisterButton();
    }

    @Then("I should see the registration success message")
    public void i_should_see_the_registration_success_message() {
        Assert.assertEquals(registerPage.getRegistrationResult(), "Your registration completed");
    }

    @Then("I should be able to logout")
    public void i_should_be_able_to_logout() {
        Assert.assertTrue(registerPage.isLogoutDisplayed());
    }

    @Then("I should see an error message about existing email")
    public void i_should_see_an_error_message_about_existing_email() {
        Assert.assertTrue(Hooks.driver.getPageSource().contains("The specified email already exists"));
    }

    @Then("I should see an error message about password mismatch")
    public void i_should_see_an_error_message_about_password_mismatch() {
        Assert.assertTrue(Hooks.driver.getPageSource().contains("The password and confirmation password do not match"));
    }
}
