package stepDefinations.Admin;

import Properties.ReusableMethods;
import cucumber.api.DataTable;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import stepDefinations.StepData;

import static io.restassured.RestAssured.given;

public class admin_registration extends ReusableMethods {

    private StepData data;

    public admin_registration(StepData data) {
        this.data = data;
    }


    @Given("^Sending request with email \"([^\"]*)\" and password \"([^\"]*)\"$")
    public void sending_request_with_email_something_and_password_something(String strArg1, String strArg2) throws Throwable {

    }

    @When("^POST request send to resource (.+)$")
    public void request_send_to_resource_something(String strArg1) throws Throwable {
        data.response = data.request.when().post(strArg1);
        System.out.println(data.response.prettyPrint());
        data.r = rawToString(data.response);
    }

    @Then("^Status-code \"([^\"]*)\" is received$")
    public void statuscode_something_is_received(int strArg1) throws Throwable {
        data.json = data.response.then().assertThat().statusCode(strArg1);
    }

    @And("^Response contains id of admin$")
    public void response_contains() throws Throwable {
        data.js = rawToJson(data.json);
        data.adminId = data.js.get("id");
    }


    @Given("^Sending request with correct token and using (.+), (.+), (.+), (.+), (.+), (.+) parameters$")
    public void sending_request_with_correct_token(String firstname, String lastname, String phonenumber, String email, int roleid, String password) throws Throwable {
            data.request = given().header("Authorization", "Bearer " + properties.getProperty("admin_one")).header("Content-Type", "application/json").body("{\"photo\":" + data.photo + ",\"first_name\":" + firstname + ",\"last_name\":" + lastname + ",\"phone_number\":" + phonenumber + ",\"email\":" + email + ", \"password\":" + convert(password) + ", \"role_id\":" + roleid + "}");
        }

    @Given("^Sending request with generated API key for admin using$")
    public void sending_request_with_generated_api_key_for_admin_using_something_and_something(DataTable table) throws Throwable {
        data.request = given().header("Authorization", "Key " + properties.getProperty("admin_token")).header("Content-Type", "application/json").body("{\"email\":" + table.raw().get(0).get(0) + ",\"password\":" + convert(table.raw().get(0).get(1)) + "}");
    }

    @When("^POST request send to \"([^\"]*)\"$")
    public void post_request_send_to_something(String strArg1) throws Throwable {
        data.response = data.request.when().post(strArg1);
        System.out.println(data.response.prettyPrint());
        data.r = rawToString(data.response);
    }

    @And("^Response contains authorization token$")
    public void response_contains_authorization_token() throws Throwable {
        data.js = rawToJson(data.json);
        data.adminAuthToken = data.js.get("id");
    }

    @Given("^Sending request with generated API key for admin with$")
    public void sending_request_with_generated_api_key_for_admin_with(DataTable table) throws Throwable {
        data.request = given().header("Authorization", "Key " + properties.getProperty("admin_token")).header("Content-Type", "application/json").body("{\"email\":" + table.raw().get(0).get(0) +"}");

    }

    @When("^PUT request send to \"([^\"]*)\"$")
    public void put_request_send_to_something(String strArg1) throws Throwable {
        data.response = data.request.when().put(strArg1);
        System.out.println(data.response.prettyPrint());
        data.r = rawToString(data.response);
    }


}
