import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/resources",
        glue = {"stepdefinitions"},
        plugin = {
                "pretty",
                "html:target/cucumber-report",
                "json:target/cucumber-report/cucumber.json",
                "junit:target/cucumber-report/junit.xml"
        })
public class RunCukesTest {

}
