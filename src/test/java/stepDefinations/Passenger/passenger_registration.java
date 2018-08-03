package stepDefinations.Passenger;

import Properties.ReusableMethods;
import cucumber.api.DataTable;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.When;
import stepDefinations.StepData;

import static io.restassured.RestAssured.given;

public class passenger_registration extends ReusableMethods {

    private StepData data;

    public passenger_registration(StepData data) {
        this.data = data;
    }


    @Given("^Sending request with correct token and with parameters$")
    public void sending_request_with_correct_token_and_with_parameters(DataTable table) throws Throwable {
        String textPass = table.raw().get(0).get(4);
        String md5pass = convert(textPass);
        String resultPass;
        if (textPass.equals("\"passengerpass\"")) {
            resultPass = md5pass;
            System.out.println(resultPass);
        }
        else resultPass = textPass;
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

    @And("^Response contains passengers authorization token$")
    public void response_contains_passengers_authorization_token() throws Throwable {
        data.js = rawToJson(data.json);
        data.passengerRegistrationToken = data.js.get("token");
    }

    @When("^POST request passenger/registration is sent$")
    public void post_request_passengerregistration_is_sent() throws Throwable {
        data.response = data.request.when().post("/passenger/registration");
        System.out.println(data.response.prettyPrint());
        data.r = rawToString(data.response);
    }



}
