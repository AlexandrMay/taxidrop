package stepDefinations;

import Properties.ReusableMethods;
import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import io.restassured.RestAssured;

import java.io.FileInputStream;
import java.io.IOException;


public class hooks extends ReusableMethods {

    private StepData data;

    public hooks(StepData data) {
        this.data = data;
    }

    @Before
    public void getData() throws IOException {

        FileInputStream fis = new FileInputStream("src/main/java/Properties/environment.properties");
        properties.load(fis);
        RestAssured.baseURI = properties.getProperty("HOST");
    }

    @After
    public void logs(Scenario scenario) {
        byte[] log = data.r.getBytes();
        scenario.embed(log, "text/html");
    }
}
