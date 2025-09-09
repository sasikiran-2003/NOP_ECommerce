package tests;

import com.aventstack.extentreports.*;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import utils.PropertyReader;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestResult;
import org.testng.annotations.*;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;

public class BaseTest {
    protected WebDriver driver;
    protected WebDriverWait wait;

    protected static ExtentReports extent;
    protected static ThreadLocal<ExtentTest> extentTest = new ThreadLocal<>();

    @BeforeSuite
    public void setUpSuite() {
        try {
            Files.createDirectories(Paths.get("test-output/reports"));
            Files.createDirectories(Paths.get("test-output/screenshots"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        ExtentSparkReporter sparkReporter = new ExtentSparkReporter("test-output/reports/ExtentReport.html");
        sparkReporter.config().setDocumentTitle("nopCommerce Automation Report");
        sparkReporter.config().setReportName("nopCommerce Test Automation Report");
        sparkReporter.config().setTheme(Theme.DARK);

        extent = new ExtentReports();
        extent.attachReporter(sparkReporter);
        extent.setSystemInfo("OS", System.getProperty("os.name"));
        extent.setSystemInfo("Java Version", System.getProperty("java.version"));
        extent.setSystemInfo("User Name", System.getProperty("user.name"));
    }

    @BeforeMethod
    @Parameters("browser")
    public void setUp(@Optional("chrome") String browser, Method method) {
        // ✅ Create ExtentTest instance for each test method
        ExtentTest test = extent.createTest(method.getName());
        extentTest.set(test);

        if (browser.equalsIgnoreCase("chrome")) {
            WebDriverManager.chromedriver().setup();
            ChromeOptions options = new ChromeOptions();
            options.addArguments("--disable-blink-features=AutomationControlled");
            options.setExperimentalOption("excludeSwitches", new String[]{"enable-automation"});
            options.setExperimentalOption("useAutomationExtension", false);
            options.addArguments("start-maximized");
            options.addArguments("user-agent=Mozilla/5.0 (Windows NT 10.0; Win64; x64) " +
                    "AppleWebKit/537.36 (KHTML, like Gecko) Chrome/114.0.0.0 Safari/537.36");
         // ✅ Add stability options
            options.addArguments("--disable-dev-shm-usage");
            options.addArguments("--disable-gpu");
            options.addArguments("--no-sandbox");
            options.addArguments("--disable-extensions");
            options.addArguments("--disable-web-security");
            options.addArguments("--disable-features=VizDisplayCompositor");
            options.addArguments("--remote-allow-origins=*");
            options.addArguments("--disable-blink-features=AutomationControlled");
            options.addArguments("--start-maximized");
            
            // ✅ Add user agent
            options.addArguments("--user-agent=Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36");
            
            // ✅ Set page load strategy
            options.setPageLoadStrategy(PageLoadStrategy.NORMAL);
            driver = new ChromeDriver(options);
            
         // ✅ Increase timeouts
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
            driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(30));
            driver.manage().window().maximize();
            
            wait = new WebDriverWait(driver, Duration.ofSeconds(20));
            
            try {
                driver.get(PropertyReader.getProperty("url"));
            } catch (Exception e) {
                System.out.println("Failed to load initial URL: " + e.getMessage());
            }
        } else if (browser.equalsIgnoreCase("firefox")) {
            WebDriverManager.firefoxdriver().setup();
            driver = new FirefoxDriver();
        } else if (browser.equalsIgnoreCase("edge")) {
            WebDriverManager.edgedriver().setup();
            driver = new EdgeDriver();
        }

        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        wait = new WebDriverWait(driver, Duration.ofSeconds(15));

        driver.get(PropertyReader.getProperty("url"));
    }

    @AfterMethod
    public void tearDown(ITestResult result) {
        ExtentTest logger = extentTest.get();
        
        if (logger != null) {
            String status;
            switch (result.getStatus()) {
                case ITestResult.FAILURE:
                    status = "FAILURE";
                    captureScreenshot(result.getName(), status);
                    logger.log(Status.FAIL, MarkupHelper.createLabel(result.getName() + " - Test Case Failed", ExtentColor.RED));
                    logger.log(Status.FAIL, MarkupHelper.createLabel("Failure Reason: " + result.getThrowable(), ExtentColor.RED));
                    break;
                case ITestResult.SKIP:
                    status = "SKIP";
                    captureScreenshot(result.getName(), status);
                    logger.log(Status.SKIP, MarkupHelper.createLabel(result.getName() + " - Test Case Skipped", ExtentColor.ORANGE));
                    break;
                case ITestResult.SUCCESS:
                    status = "SUCCESS";
                    captureScreenshot(result.getName(), status);
                    logger.log(Status.PASS, MarkupHelper.createLabel(result.getName() + " - Test Case Passed", ExtentColor.GREEN));
                    break;
            }
        }
        
        // ✅ Enhanced cleanup
        if (driver != null) {
            try {
                driver.manage().deleteAllCookies();
                driver.close();
            } catch (Exception e) {
                System.out.println("Error during driver cleanup: " + e.getMessage());
                // Force kill if needed
                try {
                    if (driver != null) {
                        ((ChromeDriver) driver).quit();
                    }
                } catch (Exception ex) {
                    System.out.println("Force quit failed: " + ex.getMessage());
                }
            }
            driver = null;
        }
    }


    private void captureScreenshot(String testName, String status) {
        try {
            TakesScreenshot ts = (TakesScreenshot) driver;
            File source = ts.getScreenshotAs(OutputType.FILE);

            String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            String screenshotName = testName + "_" + status + "_" + timeStamp + ".png";
            String screenshotPath = "test-output/screenshots/" + screenshotName;

            Files.copy(source.toPath(), new File(screenshotPath).toPath());

            if (extentTest.get() != null) {
                extentTest.get().addScreenCaptureFromPath(screenshotPath);
            }
        } catch (Exception e) {
            System.out.println("Exception while taking screenshot: " + e.getMessage());
        }
    }

    @AfterSuite
    public void tearDownSuite() {
        if (extent != null) {
            extent.flush();
        }
    }

    public WebDriver getDriver() {
        return driver;
    }
}
