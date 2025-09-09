package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginPage extends BasePage {

    private WebDriver driver;

    @FindBy(css = ".button-1.login-button")
    private WebElement loginButton;

    @FindBy(css = ".message-error")
    private WebElement errorMessage;

    @FindBy(className = "ico-logout")
    private WebElement logoutLink;

    @FindBy(className = "ico-account")
    private WebElement myAccountLink;

    @FindBy(id = "Email-error")
    private WebElement emailError;

    public LoginPage(WebDriver driver) {
        super(driver);
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    // ✅ Refetch each time to avoid stale element
    public void enterEmail(String email) {
        WebElement emailInput = driver.findElement(By.id("Email"));
        waitForElementToBeVisible(emailInput);
        emailInput.clear();
        emailInput.sendKeys(email);
    }

    // ✅ Refetch each time
    public void enterPassword(String password) {
        WebElement passwordInput = driver.findElement(By.id("Password"));
        waitForElementToBeVisible(passwordInput);
        passwordInput.clear();
        passwordInput.sendKeys(password);
    }

    public void clickLoginButton() {
        waitForElementToBeClickable(loginButton);
        clickElement(loginButton);
    }

    public String getErrorMessage() {
        waitForElementToBeVisible(errorMessage);
        return getElementText(errorMessage);
    }

    public String getEmailErrorMessage() {
        waitForElementToBeVisible(emailError);
        return emailError.getText().trim();
    }

    public boolean isLogoutDisplayed() {
        try {
            waitForElementToBeVisible(logoutLink);
            return logoutLink.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isMyAccountDisplayed() {
        try {
            waitForElementToBeVisible(myAccountLink);
            return myAccountLink.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public void clickLogout() {
        waitForElementToBeClickable(logoutLink);
        clickElement(logoutLink);
    }
}
