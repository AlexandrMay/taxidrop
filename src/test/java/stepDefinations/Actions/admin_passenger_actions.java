package stepDefinations.Actions;

import Properties.ReusableMethods;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.When;
import stepDefinations.StepData;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;

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


    @Given("^sending /admin/passengers.list$")
    public void sending_adminpassengerslist() throws Throwable {
        data.request = given().header("Authorization", "Bearer " + properties.getProperty("admin_father")).header("Content-Type", "application/json");
    }

    @When("^GET request \"([^\"]*)\" is sent$")
    public void get_request_something_is_sent(String strArg1) throws Throwable {
        data.response = data.request.when().get(strArg1);
        System.out.println(data.response.prettyPrint());
        data.r = rawToString(data.response);
    }

    @And("^count of passengers equals to DB$")
    public void count_of_passengers_equals_to_db() throws Throwable {
        data.json = data.response.then().body("\"total\"", equalTo(data.passengers()));
    }

    @Given("^sending /admin/owner.applications$")
    public void sending_adminownerapplications() throws Throwable {
        data.request = given().header("Authorization", "Bearer " + properties.getProperty("admin_father")).header("Content-Type", "application/json");
    }


    @And("^count of owner_applications equals to DB$")
    public void count_of_ownerapplications_equals_to_db() throws Throwable {
        data.json = data.response.then().body("\"total\"", equalTo(data.ownerApplications()));
    }



}
