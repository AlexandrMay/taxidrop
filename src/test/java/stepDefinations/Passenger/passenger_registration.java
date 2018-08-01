package stepDefinations.Passenger;

import Properties.ReusableMethods;
import cucumber.api.DataTable;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
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
        data.request = given().header("Authorization", "Key " + passengerAdminToken()).header("Content-Type", "application/json").body("{\"first_name\":" + table.raw().get(0).get(0) + ",\"last_name\":" + table.raw().get(0).get(1) + ", \"phone_number\":" + table.raw().get(0).get(2) + ", \"email\":" + table.raw().get(0).get(3) + ", \"password\":" + resultPass + ", \"invite_code\":" + table.raw().get(0).get(5) + ", \"token\":" + table.raw().get(0).get(6) + "}");
    }

    @And("^Response contains id of passenger$")
    public void response_contains_id_of_passenger() throws Throwable {
        data.js = rawToJson(data.json);
        data.passengerId = data.js.get("user_id");
    }
}
