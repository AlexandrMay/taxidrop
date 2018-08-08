package stepDefinations.Actions;

import Properties.ReusableMethods;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.When;
import stepDefinations.StepData;

import static io.restassured.RestAssured.given;

public class driver_actions extends ReusableMethods {

    private String resultToken;

    private StepData data;

    public driver_actions(StepData data) {
        this.data = data;
    }


    @Given("^Sending request with correct token$")
    public void sending_request_with_correct_token() throws Throwable {
        data.request = given().header("Authorization", "Bearer " + properties.getProperty("driver_one")).header("Content-Type", "application/json");
    }

    @When("^GET request is sent$")
    public void get_request_send_to_something() throws Throwable {
        data.response = data.request.when().get("/driver/profile.info");
        System.out.println(data.response.prettyPrint());
        data.r = rawToString(data.response);
    }

    @And("^Response contains referral code$")
    public void response_contains_refferral_code() throws Throwable {
        data.js = rawToJson(data.json);
        data.driversRefferralCode = data.js.get("referral_code");
    }

    @Given("^Sending profile.edit request with correct token and using (.+), (.+), (.+), (.+), (.+) parameters$")
    public void sending_profileedit_request_with_correct_token_and_using_parameters(String token, String photo, String first_name, String last_name, String email) throws Throwable {
        if (token.equals("\"bearer\"")) {
            resultToken = properties.getProperty("driver_one");
        }
        else {
            resultToken = token;
        }
        data.request = given().header("Authorization", "Bearer " + resultToken).header("Content-Type", "application/json")
                .body("{\"photo\":"  + photo + ", \"first_name\":" + first_name + ", \"last_name\":" + last_name + ", \"email\":" + email + "}");
    }

    @Given("^Sending rating.info request using (.+)$")
    public void sending_ratinginfo_request_using(String token) throws Throwable {
        if (token.equals("\"bearer\"")) {
            resultToken = properties.getProperty("driver_one");
        }
        else {
            resultToken = token;
        }
        data.request = given().header("Authorization", "Bearer " + resultToken);
    }

    @When("^GET request send to (.+)$")
    public void get_request_send_to(String resource) throws Throwable {
        data.response = data.request.when().get(resource);
        System.out.println(data.response.prettyPrint());
        data.r = rawToString(data.response);
    }

    @Given("^Sending pass.change request using (.+), (.+), (.+)$")
    public void sending_passchange_request_using_(String token, String password, String passwordold) throws Throwable {
        if (token.equals("\"bearer\"")) {
            resultToken = properties.getProperty("driver_one");
        }
        else {
            resultToken = token;
        }
        data.request = given().header("Authorization", "Bearer " + resultToken)
                .body("{\"password\":"  + password + ", \"password_old\":" + passwordold + "}");
    }

    @Given("^Sending profile.delete request using (.+)$")
    public void sending_profiledelete_request_using(String token) throws Throwable {
        if (token.equals("\"bearer\"")) {
            resultToken = properties.getProperty("driver_one");
        }
        else {
            resultToken = token;
        }
        data.request = given().header("Authorization", "Bearer " + resultToken);
    }

    @When("^DELETE request /driver/profile.delete is sent$")
    public void delete_request_send_to_something() throws Throwable {
        data.response = data.request.when().delete("/driver/profile.delete");
        System.out.println(data.response.prettyPrint());
        data.r = rawToString(data.response);
    }

    @When("^PUT request /driver/profile.edit is sent$")
    public void put_request_driverprofileedit_is_sent() throws Throwable {
        data.response = data.request.when().put("/driver/profile.edit");
        System.out.println(data.response.prettyPrint());
        data.r = rawToString(data.response);
    }

    @When("^PUT request /driver/pass.change is sent$")
    public void put_request_driverpasschange_is_sent() throws Throwable {
        data.response = data.request.when().put("/driver/pass.change");
        System.out.println(data.response.prettyPrint());
        data.r = rawToString(data.response);
    }

}
