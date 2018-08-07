package stepDefinations.Admin;

import Properties.ReusableMethods;
import cucumber.api.java.en.Given;
import stepDefinations.StepData;

import static io.restassured.RestAssured.given;

public class admin_actions extends ReusableMethods {

    private StepData data;

    public admin_actions(StepData data) {
        this.data = data;
    }

    private String resultToken;


    @Given("^sending /admin/drivers.list using (.+)$")
    public void sending_admindriverslist_using(String token) throws Throwable {
        if (token.equals("\"true\"")) {
            resultToken = properties.getProperty("admin_father");
        } else {
            resultToken = token;
        }

        data.request = given().header("Authorization", "Bearer " + resultToken).header("Content-Type", "application/json");
    }






}
