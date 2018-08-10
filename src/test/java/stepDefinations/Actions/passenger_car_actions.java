package stepDefinations.Actions;

import Properties.ReusableMethods;
import cucumber.api.DataTable;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.When;
import stepDefinations.StepData;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;

public class passenger_car_actions extends ReusableMethods {

    private String resultToken;

    private StepData data;

    public passenger_car_actions(StepData data) {
        this.data = data;
    }

    @Given("^sending /passenger/car.add request$")
    public void sending_passengercaradd_request(DataTable table) throws Throwable {
        data.request = given().header("Authorization", "Bearer " + getTempProperty("passengerAuthorizationToken", "src/main/java/Properties/passengerAuthorizationToken.properties")).header("Content-Type", "application/json").
                body("{\"make\":" + table.raw().get(0).get(0) + "," +
                      "\"model\":" + table.raw().get(0).get(1) + "," +
                      "\"license_plate_number\":" + table.raw().get(0).get(2) + "," +
                      "\"car_photo\":" + table.raw().get(0).get(3) + "," +
                      "\"proof_of_ownership\":" + table.raw().get(0).get(3) + "," +
                      "\"insurance\":" + table.raw().get(0).get(3) + "}");
        }

    @And("^id of car equals to DB$")
    public void id_of_car_equals_to_db() throws Throwable {
        data.json = data.response.then().body("\"car_id\"", equalTo(data.carId(29)));
        data.js = rawToJson(data.json);
        data.passengerCarId = data.js.get("car_id");
    }

    @Given("^sending /passenger/car.add request using (.+), (.+), (.+), (.+), (.+), (.+), (.+)$")
    public void sending_passengercaradd_request_using_(String token, String make, String model, String licenseplatenumber, String carphoto, String proofofownership, String insurance) throws Throwable {
        if (token.equals("\"true\"")){
            resultToken = getTempProperty("passengerAuthorizationToken", "src/main/java/Properties/passengerAuthorizationToken.properties");
        } else {
            resultToken = token;
        }

        data.request = given().header("Authorization", "Bearer " + resultToken).header("Content-Type", "application/json").
                body("{\"make\":" + make + "," +
                        "\"model\":" + model + "," +
                        "\"license_plate_number\":" + licenseplatenumber + "," +
                        "\"car_photo\":" + carphoto + "," +
                        "\"proof_of_ownership\":" + proofofownership + "," +
                        "\"insurance\":" + insurance + "}");
        }

    @Given("^sending /passenger/cars.list request using (.+)$")
    public void sending_passengercarslist_request_using(String token) throws Throwable {
        if (token.equals("\"true\"")){
            resultToken = getTempProperty("passengerAuthorizationToken", "src/main/java/Properties/passengerAuthorizationToken.properties");
        } else {
            resultToken = token;
        }

        data.request = given().header("Authorization", "Bearer " + resultToken).header("Content-Type", "application/json");
    }

    @Given("^sending /passenger/car.delete request using (.+)$")
    public void sending_passengercardelete_request_using_(String token) throws Throwable {
        if (token.equals("\"true\"")){
            resultToken = getTempProperty("passengerAuthorizationToken", "src/main/java/Properties/passengerAuthorizationToken.properties");
        } else {
            resultToken = token;
        }

        data.request = given().header("Authorization", "Bearer " + resultToken).header("Content-Type", "application/json");
    }

    @When("^DELETE /passenger/car.delete request with (.+) and (.+) is sent$")
    public void delete_passengercardelete_request_with_and_is_sent(String resource, String carid) throws Throwable {
        int resultId;
        if (carid.equals("\"true\"")) {
            resultId = data.passengerCarId + 2;
        } else {
            resultId = Integer.parseInt(carid);
        }
        data.response = data.request.when().delete(resource + resultId);
        System.out.println(data.response.prettyPrint());
        data.r = rawToString(data.response);
    }





}








