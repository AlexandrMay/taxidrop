package stepDefinations.Actions;

import Properties.ReusableMethods;
import cucumber.api.java.en.Given;
import stepDefinations.StepData;

import static io.restassured.RestAssured.given;

public class admin_passenger_actions extends ReusableMethods {

    private StepData data;

    public admin_passenger_actions(StepData data) {
        this.data = data;
    }

    private String resultToken;



    @Given("^sending request using (.+)$")
    public void sending_adminpassengerslist_using(String token) throws Throwable {
        if (token.equals("\"true\"")) {
            resultToken = properties.getProperty("admin_father");
        } else {
            resultToken = token;
        }

        data.request = given().header("Authorization", "Bearer " + resultToken).header("Content-Type", "application/json");
    }

    @Given("^sending /admin/passenger.edit using (.+), (.+), (.+), (.+)$")
    public void sending_adminpassengeredit_using_(String token, String firstname, String lastname, String email) throws Throwable {
        if (token.equals("\"true\"")) {
            resultToken = properties.getProperty("admin_father");
        } else {
            resultToken = token;
        }

        data.request = given().header("Authorization", "Bearer " + resultToken).header("Content-Type", "application/json").
                body("{\"first_name\":" + firstname + "," +
                        "\"last_name\":" + lastname + "," +
                        "\"email\":" + email + "}");
    }

    @Given("^sending /admin/passenger.delete using (.+)$")
    public void sending_adminpassengerdelete_using(String token) throws Throwable {
        if (token.equals("\"true\"")) {
            resultToken = properties.getProperty("admin_father");
        } else {
            resultToken = token;
        }

        data.request = given().header("Authorization", "Bearer " + resultToken).header("Content-Type", "application/json");
    }

}
