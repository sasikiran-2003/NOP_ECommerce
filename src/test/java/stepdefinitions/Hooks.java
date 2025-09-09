package stepdefinitions;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import java.time.Duration;

public class Hooks {
    public static WebDriver driver;

    @Before
    public void setUp() {
        // Clean up any existing processes
        try {
            Runtime.getRuntime().exec("taskkill /f /im chromedriver.exe");
            Thread.sleep(2000);
        } catch (Exception e) {
            // Ignore cleanup errors
        }

        // Setup WebDriver with enhanced stability
        WebDriverManager.chromedriver().clearDriverCache().setup();
        
        ChromeOptions options = new ChromeOptions();
        // ✅ Enhanced Chrome options for stability
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--disable-gpu");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-extensions");
        options.addArguments("--disable-web-security");
        options.addArguments("--disable-features=VizDisplayCompositor");
        options.addArguments("--remote-allow-origins=*");
        options.addArguments("--disable-blink-features=AutomationControlled");
        options.addArguments("--start-maximized");
        options.addArguments("--disable-infobars");
        options.addArguments("--disable-notifications");
        
        // Set page load strategy for better stability
        options.setPageLoadStrategy(PageLoadStrategy.NORMAL);
        
        // User agent
        options.addArguments("--user-agent=Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36");
        
        driver = new ChromeDriver(options);
        
        // ✅ Enhanced timeouts
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(30));
        driver.manage().window().maximize();
        
        System.out.println("✅ WebDriver initialized successfully");
    }

    @After
    public void tearDown() {
        if (driver != null) {
            try {
                driver.manage().deleteAllCookies();
                driver.close();
                System.out.println("✅ WebDriver closed successfully");
            } catch (Exception e) {
                System.err.println("⚠️ Error during driver cleanup: " + e.getMessage());
                // Force kill processes
                try {
                    Runtime.getRuntime().exec("taskkill /f /im chromedriver.exe");
                } catch (Exception ex) {
                    System.err.println("Force cleanup failed: " + ex.getMessage());
                }
            }
            driver = null;
        }
    }
}
