package stepDefinations.Admin;

import Properties.ReusableMethods;
import cucumber.api.DataTable;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.apache.commons.lang3.StringUtils;
import stepDefinations.StepData;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;

public class admin_registration extends ReusableMethods {

    private StepData data;

    public admin_registration(StepData data) {
        this.data = data;
    }

    private String resultPassword;
    private String resultToken;


    //admin_add

    @Given("^Sending admin_add request with$")
    public void sending_adminadd_request_with(DataTable table) throws Throwable {
        resultPassword = convert(table.raw().get(0).get(5));
        System.out.println("ПАРОЛЬ АДМИНА " + resultPassword);
        data.request = given().header("Authorization", "Bearer " + properties.getProperty("admin_father")).header("Content-Type", "application/json")
                .body("{" +
                        "\"photo\":" + table.raw().get(0).get(0) + "," +
                        "\"first_name\":" + table.raw().get(0).get(1) + "," +
                        "\"last_name\":" + table.raw().get(0).get(2) + "," +
                        "\"phone_number\":" + table.raw().get(0).get(3) + "," +
                        "\"email\":" + table.raw().get(0).get(4) + ", " +
                        "\"password\":" + resultPassword + ", " +
                        "\"role_id\":" + table.raw().get(0).get(6) + "}");
    }

    @When("^POST request send with correct resource$")
    public void post_request_send_with_correct_resource() throws Throwable {
        data.response = data.request.when().post("/admin/admin.add");
        System.out.println(data.response.prettyPrint());
        data.r = rawToString(data.response);
    }

    @Then("^Status_code is 200$")
    public void statuscode_is_200() throws Throwable {
        data.json = data.response.then().assertThat().statusCode(200);
    }

    @And("^Response contains id of admin$")
    public void response_contains() throws Throwable {
        data.js = rawToJson(data.json);
        data.adminId = data.js.get("id");
    }


    //admin_add with errors

    @Given("^Sending admin_add request using (.+), (.+), (.+), (.+), (.+), (.+), (.+), (.+) parameters$")
    public void sending_adminadd_request_with_correct_token_and_using_parameters(String token, String photo, String firstname, String lastname, String phonenumber, String email, String roleid, String password) throws Throwable {
        if (token.equals("\"true\"")) {
            resultToken = properties.getProperty("admin_father");
        }
        else {
            resultToken = token;
        }
        if (password.equals("\"pass\"")) {
            resultPassword = convert(password);
        }
        else {
            resultPassword = password;
        }

        data.request = given().header("Authorization", "Bearer " + resultToken).header("Content-Type", "application/json")
                .body("{" +
                        "\"photo\":" + photo + "," +
                        "\"first_name\":" + firstname + "," +
                        "\"last_name\":" + lastname + "," +
                        "\"phone_number\":" + phonenumber + "," +
                        "\"email\":" + email + ", " +
                        "\"password\":" + resultPassword + ", " +
                        "\"role_id\":" + roleid + "}");
    }

    @When("^POST request send to (.+)$")
    public void request_send_to_resource_something(String strArg1) throws Throwable {
        data.response = data.request.when().post(strArg1);
        System.out.println(data.response.prettyPrint());
        data.r = rawToString(data.response);
    }

    @And("^Response contains (.+) and (.+)$")
    public void response_contains_and(String errorkey, String errortext) throws Throwable {
        if (StringUtils.isNumeric(errortext)) {
            data.json = data.response.then().body(errorkey, equalTo(Integer.parseInt(errortext)));
        } else {
            data.json = data.response.then().body(errorkey, equalTo(errortext));
        }

    }


    //admin_authorization

    @Given("^Sending request with generated API key for admin using$")
    public void sending_request_with_generated_api_key_for_admin_using_something_and_something(DataTable table) throws Throwable {
        data.request = given().header("Authorization", "Key " + passengerAdminToken()).header("Content-Type", "application/json").body("{\"email\":" + table.raw().get(0).get(0) + ",\"password\":" + convert(table.raw().get(0).get(1)) + "}");
    }

    @When("^POST request send with authorization resource$")
    public void post_request_send_to_something() throws Throwable {
        data.response = data.request.when().post("/admin/authorization");
        System.out.println(data.response.prettyPrint());
        data.r = rawToString(data.response);
    }

    @Then("^Status-code \"([^\"]*)\" is received$")
    public void statuscode_something_is_received(int strArg1) throws Throwable {
        data.json = data.response.then().assertThat().statusCode(strArg1);
    }

    @And("^Response contains authorization token$")
    public void response_contains_authorization_token() throws Throwable {
        data.js = rawToJson(data.json);
        data.adminAuthToken = data.js.get("token");
        System.out.println(data.adminAuthToken);
    }


//admin_password recovery

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


    @And("^Response contains error$")
    public void response_contains_error(DataTable table) throws Throwable {
        data.json = data.response.then().body(table.raw().get(0).get(0), equalTo(table.raw().get(0).get(1)));
    }

    @Then("^Statuscode (.+) is received$")
    public void statuscode_is_received(int statuscode) throws Throwable {
        data.json = data.response.then().assertThat().statusCode(statuscode);
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

//deleting

    @Given("^Sending request to delete admin$")
    public void sending_request_to_delete_admin() throws Throwable {
        data.request = given().header("Authorization", "Bearer " + properties.getProperty("admin_father")).header("Content-Type", "application/json");
    }

    @When("^DELETE request send$")
    public void delete_request_send() throws Throwable {
        data.response = data.request.when().delete("/admin/admin.delete/" + data.adminId);
        System.out.println(data.response.prettyPrint());
        data.r = rawToString(data.response);
    }
}
