package stepDefinations.Admin;

import Properties.ReusableMethods;
import cucumber.api.DataTable;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import stepDefinations.StepData;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;

public class admin_registration extends ReusableMethods {

    private StepData data;

    public admin_registration(StepData data) {
        this.data = data;
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
        String decryptedPass = password;
        String encryptedPass = convert(password);
        String result;
        if (decryptedPass.equals("\"pass\"")) {
            result = encryptedPass;
        } else {
            result = decryptedPass;
        }
        System.out.println("МОЙ ПАРОЛЬ " + encryptedPass);
        data.request = given().header("Authorization", "Bearer " + properties.getProperty("admin_one")).header("Content-Type", "application/json").body("{\"photo\":" + data.photo + ",\"first_name\":" + firstname + ",\"last_name\":" + lastname + ",\"phone_number\":" + phonenumber + ",\"email\":" + email + ", \"password\":" + result + ", \"role_id\":" + roleid + "}");
    }

    @Given("^Sending request with generated API key for admin using$")
    public void sending_request_with_generated_api_key_for_admin_using_something_and_something(DataTable table) throws Throwable {
        data.request = given().header("Authorization", "Key " + passengerAdminToken()).header("Content-Type", "application/json").body("{\"email\":" + table.raw().get(0).get(0) + ",\"password\":" + convert(table.raw().get(0).get(1)) + "}");
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
        data.request = given().header("Authorization", "Key " + passengerAdminToken()).header("Content-Type", "application/json").body("{\"email\":" + table.raw().get(0).get(0) + "}");

    }

    @When("^PUT request send to \"([^\"]*)\"$")
    public void put_request_send_to_something(String strArg1) throws Throwable {
        data.response = data.request.when().put(strArg1);
        System.out.println(data.response.prettyPrint());
        data.r = rawToString(data.response);
    }

    @Given("^Sending request with incorrect token and parameters (.+), (.+), (.+), (.+), (.+), (.+)$")
    public void sending_request_with_incorrect_token(String firstname, String lastname, String phonenumber, String email, int roleid, String password) throws Throwable {
        data.request = given().header("Authorization", "Bearer 1" + properties.getProperty("admin_one")).header("Content-Type", "application/json").body("{\"photo\":" + data.photo + ",\"first_name\":" + firstname + ",\"last_name\":" + lastname + ",\"phone_number\":" + phonenumber + ",\"email\":" + email + ", \"password\":" + convert(password) + ", \"role_id\":" + roleid + "}");
    }

    @And("^Response contains error$")
    public void response_contains_error(DataTable table) throws Throwable {
        data.json = data.response.then().body(table.raw().get(0).get(0), equalTo(table.raw().get(0).get(1)));
    }

    @Then("^Statuscode (.+) is received$")
    public void statuscode_is_received(int statuscode) throws Throwable {
        data.json = data.response.then().assertThat().statusCode(statuscode);
    }

    @And("^Response contains (.+) and (.+)$")
    public void response_contains_and(String errorkey, String errortext) throws Throwable {
        data.json = data.response.then().body(errorkey, equalTo(errortext));
    }

    @Given("^Sending some request with (.+) using (.+) and (.+)$")
    public void sending_request_with_using_and(String admintoken, String email, String password) throws Throwable {
        String receivedData = admintoken;
        String realToken = passengerAdminToken();
        String resultToken;
        if (receivedData.equals("\"right\"")) {
            resultToken = realToken;
        } else {
            resultToken = admintoken;
        }
        String receivedPass = password;
        String modifiedPass = convert(password);
        String resultPass;
        if (receivedPass.equals("\"pass\"") || (receivedPass.equals("\"wrong\""))) {
            resultPass = modifiedPass;
        }
        else {
            resultPass = password;
        }
        data.request = given().header("Authorization", "Key " + resultToken).header("Content-Type", "application/json").body("{\"email\":" + email + ", \"password\":" + resultPass + "}");
    }

    @Given("^Sending error request with (.+) using (.+)$")
    public void sending_request_with_using_and(String admintoken, String email) throws Throwable {
        String receivedData = admintoken;
        String realToken = passengerAdminToken();
        String resultToken;
        if (receivedData.equals("\"right\"")) {
            resultToken = realToken;
        } else {
            resultToken = admintoken;
        }
        data.request = given().header("Authorization", "Key " + resultToken).header("Content-Type", "application/json").body("{\"email\":" + email + "}");
    }

    @When("^PUT request send to resource (.+)$")
    public void post_request_send_to(String resource) throws Throwable {
        data.response = data.request.when().put(resource);
        System.out.println(data.response.prettyPrint());
        data.r = rawToString(data.response);
    }
}
