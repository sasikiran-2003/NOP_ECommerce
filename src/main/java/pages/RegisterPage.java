package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class RegisterPage extends BasePage {

    private WebDriver driver;

    @FindBy(id = "gender-male")
    private WebElement genderMaleRadio;

    @FindBy(id = "gender-female")
    private WebElement genderFemaleRadio;

    @FindBy(id = "register-button")
    private WebElement registerButton;

    @FindBy(css = ".result")
    private WebElement registrationResult;
    
    @FindBy(id = "Company")
    private WebElement companyInput;
    
    @FindBy(className = "ico-logout")
    private WebElement logoutLink;


    public RegisterPage(WebDriver driver) {
        super(driver);
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public void selectGender(String gender) {
        if ("male".equalsIgnoreCase(gender)) {
            waitForElementToBeClickable(genderMaleRadio);
            clickElement(genderMaleRadio);
        } else if ("female".equalsIgnoreCase(gender)) {
            waitForElementToBeClickable(genderFemaleRadio);
            clickElement(genderFemaleRadio);
        } else {
            throw new IllegalArgumentException("Invalid gender: " + gender);
        }
    }

    // ✅ Refetch dynamic inputs
    public void enterFirstName(String firstName) {
        WebElement firstNameInput = driver.findElement(By.id("FirstName"));
        waitForElementToBeVisible(firstNameInput);
        firstNameInput.clear();
        firstNameInput.sendKeys(firstName);
    }

    public void enterLastName(String lastName) {
        WebElement lastNameInput = driver.findElement(By.id("LastName"));
        waitForElementToBeVisible(lastNameInput);
        lastNameInput.clear();
        lastNameInput.sendKeys(lastName);
    }

    public void enterEmail(String email) {
        WebElement emailInput = driver.findElement(By.id("Email"));
        waitForElementToBeVisible(emailInput);
        emailInput.clear();
        emailInput.sendKeys(email);
    }

    public void enterPassword(String password) {
        WebElement passwordInput = driver.findElement(By.id("Password"));
        waitForElementToBeVisible(passwordInput);
        passwordInput.clear();
        passwordInput.sendKeys(password);
    }

    public void enterConfirmPassword(String confirmPassword) {
        WebElement confirmPasswordInput = driver.findElement(By.id("ConfirmPassword"));
        waitForElementToBeVisible(confirmPasswordInput);
        confirmPasswordInput.clear();
        confirmPasswordInput.sendKeys(confirmPassword);
    }

    public void clickRegisterButton() {
        waitForElementToBeClickable(registerButton);
        clickElement(registerButton);
    }

    public String getRegistrationResult() {
        waitForElementToBeVisible(registrationResult);
        return getElementText(registrationResult);
    }
    
    public void enterCompanyName(String company) {
        WebElement companyInput = driver.findElement(By.id("Company"));
        waitForElementToBeVisible(companyInput);
        companyInput.clear();
        companyInput.sendKeys(company);
    }
    
    public boolean isLogoutDisplayed() {
        try {
            return logoutLink.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

}
