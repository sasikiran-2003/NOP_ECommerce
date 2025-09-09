package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;
import java.util.List;

public class ProductPage extends BasePage {

    @FindBy(className = "product-title")
    private List<WebElement> productTitles;

    @FindBy(css = ".button-2.product-box-add-to-cart-button")
    private List<WebElement> addToCartButtons;

    @FindBy(css = ".add-to-cart-button")
    private WebElement addToCartButton;

    @FindBy(className = "content")
    private WebElement notificationBar;

    @FindBy(css = ".close")
    private WebElement closeNotificationButton;

    @FindBy(id = "product_attribute_2")
    private WebElement ramDropdown;

    @FindBy(id = "product_attribute_3_6")
    private WebElement hdd320gbRadio;

    @FindBy(id = "product_attribute_3_7")
    private WebElement hdd400gbRadio;

    @FindBy(id = "product_attribute_4_8")
    private WebElement osHomeRadio;

    @FindBy(id = "product_attribute_4_9")
    private WebElement osPremiumRadio;

    @FindBy(id = "product_attribute_5_10")
    private WebElement softwareMicrosoft;

    @FindBy(id = "product_attribute_5_11")
    private WebElement softwareAcrobat;

    @FindBy(id = "product_attribute_5_12")
    private WebElement softwareTotal;

    @FindBy(id = "small-searchterms")
    private WebElement searchInput;

    @FindBy(css = ".search-box-button")
    private WebElement searchButton;

    @FindBy(className = "product-item")
    private List<WebElement> searchResults;
    
    @FindBy(className = "ico-cart")
    private WebElement shoppingCartLink;


    public ProductPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    public void navigateToProductCategory(String category) {
        driver.get("https://demo.nopcommerce.com/" + category.toLowerCase());
    }

    public void navigateToProduct(String productName) {
        driver.get("https://demo.nopcommerce.com/" + productName.toLowerCase().replace(" ", "-"));
    }

    public void addProductToCart(String productName) {
        for (int i = 0; i < productTitles.size(); i++) {
            if (productTitles.get(i).getText().equalsIgnoreCase(productName)) {
                addToCartButtons.get(i).click();
                break;
            }
        }
    }

    public void configureAndAddComputerToCart(String ram, String hdd, String os, List<String> softwareOptions) {
        new Select(ramDropdown).selectByVisibleText(ram);

        if ("320 GB".equals(hdd)) {
            clickElement(hdd320gbRadio);
        } else if ("400 GB".equals(hdd)) {
            clickElement(hdd400gbRadio);
        }

        if ("Home".equals(os)) {
            clickElement(osHomeRadio);
        } else if ("Premium".equals(os)) {
            clickElement(osPremiumRadio);
        }

        if (softwareOptions.contains("Microsoft Office")) clickElement(softwareMicrosoft);
        if (softwareOptions.contains("Acrobat Reader")) clickElement(softwareAcrobat);
        if (softwareOptions.contains("Total Commander")) clickElement(softwareTotal);

        clickAddToCart();
    }

    public void searchForProduct(String productName) {
        sendKeysToElement(searchInput, productName);
        clickElement(searchButton);
    }

    public boolean areSearchResultsDisplayed(String searchTerm) {
        return searchResults.stream()
                .anyMatch(result -> result.getText().toLowerCase().contains(searchTerm.toLowerCase()));
    }

    public void applyFilter(String filterName) {
        WebElement filterLink = driver.findElement(By.linkText(filterName));
        clickElement(filterLink);
    }

    public boolean areFilteredProductsDisplayed() {
        return searchResults.size() > 0;
    }

    public void clickAddToCart() {
        clickElement(addToCartButton);
    }

    public String getNotificationMessage() {
        return getElementText(notificationBar);
    }

    public void closeNotification() {
        clickElement(closeNotificationButton);
    }

    public void waitForNotification() {
        waitForElementToBeVisible(notificationBar);
    }
    
 // Add this method inside ProductPage
    public void clickShoppingCart() {
        clickElement(shoppingCartLink);
    }
    
    
}
