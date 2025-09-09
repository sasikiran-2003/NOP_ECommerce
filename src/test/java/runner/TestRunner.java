package runner;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.testng.annotations.DataProvider;

@CucumberOptions(
    features = "src/test/java/features",  // ✅ Updated path
    glue = {"stepdefinitions"},  // ✅ Simplified glue
    plugin = {
        "pretty",
        "html:test-output/reports/cucumber.html",
        "json:test-output/reports/cucumber.json",
        "junit:test-output/reports/cucumber.xml",

    },
    monochrome = true
)
public class TestRunner extends AbstractTestNGCucumberTests {
    @Override
    @DataProvider(parallel = false)  // ✅ Disable parallel for stability
    public Object[][] scenarios() {
        return super.scenarios();
    }
}
