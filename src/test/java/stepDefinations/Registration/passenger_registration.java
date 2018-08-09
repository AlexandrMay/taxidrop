package stepDefinations.Registration;

import Properties.ReusableMethods;
import cucumber.api.DataTable;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.When;
import stepDefinations.StepData;

import static io.restassured.RestAssured.given;

public class passenger_registration extends ReusableMethods {

    private String resultToken;
    private String resultFB;
    private String resultPass;

    private StepData data;

    public passenger_registration(StepData data) {
        this.data = data;
    }


    @Given("^Sending request with correct token and with parameters$")
    public void sending_request_with_correct_token_and_with_parameters(DataTable table) throws Throwable {
        if (table.raw().get(0).get(4).equals("\"passengerpass\"")) {
            resultPass = convert(table.raw().get(0).get(4));
            System.out.println("MY PASSWORD IS: " + resultPass);
        }
        else resultPass = table.raw().get(0).get(4);

        data.request = given().header("Authorization", "Key " + passengerAdminToken()).header("Content-Type", "application/json")
                .body("{\"first_name\":" + table.raw().get(0).get(0) + ", " +
                        "\"last_name\":" + table.raw().get(0).get(1) + ", " +
                        "\"phone_number\":" + table.raw().get(0).get(2) + ", " +
                        "\"email\":" + table.raw().get(0).get(3) + ", " +
                        "\"password\":" + resultPass + ", " +
                        "\"invite_code\":" + table.raw().get(0).get(5) + ", " +
                        "\"token\": " + "\"" + data.passengerRegistrationToken + "\"" + "}");

        System.out.println(data.passengerRegistrationToken);
    }

    @And("^Response contains id of passenger$")
    public void response_contains_id_of_passenger() throws Throwable {
        data.js = rawToJson(data.json);
        data.passengerId = data.js.get("user_id");
    }

    @Given("^Sending request with FB token to confirm passengers number$")
    public void sending_request_with_fb_token_to_confirm_passengers_number() throws Throwable {
        data.request = given().header("Authorization", "Key " + passengerAdminToken()).header("Content-Type", "application/json")
                .body("{\"user_type\": " + 1 + ", \"code\": " + properties.getProperty("FB_passenger_token") + "}");
    }

    @When("^POST request phone.confirm for passenger is sent$")
    public void post_request_phoneconfirm_for_passenger_is_sent() throws Throwable {
        data.response = data.request.when().post("/phone.confirm");
        System.out.println(data.response.prettyPrint());
        data.r = rawToString(data.response);
    }

    @And("^Response contains passengers registration token$")
    public void response_contains_passengers_registr_token() throws Throwable {
        data.js = rawToJson(data.json);
        data.passengerRegistrationToken = data.js.get("token");
    }

    @When("^POST request passenger/registration is sent$")
    public void post_request_passengerregistration_is_sent() throws Throwable {
        data.response = data.request.when().post("/passenger/registration");
        System.out.println(data.response.prettyPrint());
        data.r = rawToString(data.response);
    }

    @Given("^sending phone/confirm request using (.+), (.+) and (.+)$")
    public void sending_phoneconfirm_request_using_and(String token, String usertype, String fbcode) throws Throwable {
        if (token.equals("\"true\"")) {
            resultToken = passengerAdminToken();
        } else {
            resultToken = token;
        }
        if (fbcode.equals("\"FB\"")) {
            resultFB = properties.getProperty("FB_passenger_token");
        } else {
            resultFB = fbcode;
        }
        data.request = given().header("Authorization", "Key " + resultToken).header("Content-Type", "application/json").
                body("{\"user_type\":" + usertype + ", " +
                       "\"code\":" + resultFB + "}");
    }

    @Given("^sending passenger/registration request using (.+), (.+), (.+), (.+), (.+), (.+), (.+), (.+)$")
    public void sending_passengerregistration_request_using_(String token, String firstname, String lastname, String phonenumber, String email, String password, String invitecode, String registrationtoken) throws Throwable {
        if (token.equals("\"true\"")){
            resultToken = passengerAdminToken();
        } else {
            resultToken = token;
        }
        if (password.equals("\"passengerpass\"")){
            resultPass = convert(password);
        } else {
            resultPass = password;
        }
        System.out.println("PASS: " + resultPass);
        data.request = given().header("Authorization", "Key " + resultToken).header("Content-Type", "application/json")
                .body("{\"first_name\":" + firstname + ", " +
                        "\"last_name\":" + lastname + ", " +
                        "\"phone_number\":" + phonenumber + ", " +
                        "\"email\":" + email + ", " +
                        "\"password\":" + resultPass + ", " +
                        "\"invite_code\":" + invitecode + ", " +
                        "\"token\": " + registrationtoken + "}");
    }

    //autorize

    @Given("^sending passenger/authorization request using$")
    public void sending_passengerauthorization_request_using(DataTable table) throws Throwable {
        data.request = given().header("Authorization", "Key " + passengerAdminToken()).header("Content-Type", "application/json").
                body("{\"user_type\":" + table.raw().get(0).get(0) + ", " +
                      "\"phone_number\":" + table.raw().get(0).get(1) + ", " +
                      "\"password\":" + convert(table.raw().get(0).get(2)) + "}");
    }

    @When("^POST request passenger/authorization is sent$")
    public void post_request_passengerauthorization_is_sent() throws Throwable {
        data.response = data.request.when().post("/authorization");
        System.out.println(data.response.prettyPrint());
        data.r = rawToString(data.response);
    }

    @And("^Response contains passengers authorization token$")
    public void response_contains_passengers_authorization_token() throws Throwable {
        data.js = rawToJson(data.json);
        String token = data.js.get("token");
        System.out.println("AUTH TOKEN: " + token);
        props.setProperty("passengerAuthorizationToken", token);
        setSomePropertyToFile();
    }

}
