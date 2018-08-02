package stepDefinations.driver_registration;

import Properties.ReusableMethods;
import cucumber.api.DataTable;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import stepDefinations.StepData;


import static io.restassured.RestAssured.given;

public class driver_registration extends ReusableMethods {

    private StepData data;

    public driver_registration(StepData data) {
        this.data = data;
    }


    @And("^Response contains id of driver$")
    public void response_contains_id_of_driver() throws Throwable {
        data.js = rawToJson(data.json);
        data.driverID = data.js.get("user_id");
    }

    @Given("^Sending request to register driver with correct token and with parameters$")
    public void sending_request_to_register_driver_with_correct_token_and_with_parameters(DataTable table) throws Throwable {
        String pass = table.raw().get(0).get(4);
        String result;
        if (pass.equals("\"driverpass\"")) {
            result = convert(pass);
        }else {
            result = pass;
        }
        System.out.println("Водительский пароль: " + result);

        data.request = given().header("Authorization", "Key " + driverToken()).header("Content-Type", "application/json")
                .body("{" +
                "\"first_name\": " + table.raw().get(0).get(0) + "," +
                "\"last_name\": " + table.raw().get(0).get(1) + "," +
                "\"email\": " + table.raw().get(0).get(2) + "," +
                "\"phone_number\": " + table.raw().get(0).get(3) + "," +
                "\"password\": " + result + "," +
                "\"invite_code\": " + table.raw().get(0).get(5) + "," +
                "\"driving_license\": " + table.raw().get(0).get(6) + "," +
                "\"passport\": " + table.raw().get(0).get(7) + "," +
                "\"photo\": " + table.raw().get(0).get(8) + "," +
                "\"license_number\": " + table.raw().get(0).get(9) + "," +
                "\"license_issue_date\": " + table.raw().get(0).get(10)+ "," +
                "\"license_expire_date\": " + table.raw().get(0).get(11) + "," +
                "\"car\": {" +
                "\"make\": " + table.raw().get(0).get(12) + "," +
                "\"model\": " + table.raw().get(0).get(13) + "," +
                "\"licence_plate_number\": " + table.raw().get(0).get(14) + "," +
                "\"car_photo\": " + table.raw().get(0).get(15) + "," +
                "\"proof_of_ownership\": " + table.raw().get(0).get(16) + "," +
                "\"insurance\": " + table.raw().get(0).get(17) + "}" + "," +
                "\"bank\": {" +
                "\"bank_name\": " + table.raw().get(0).get(18) + "," +
                "\"account_number\": " + table.raw().get(0).get(19) + "," +
                "\"account_name\": " + table.raw().get(0).get(20) + "," +
                "\"bvn\": " + table.raw().get(0).get(21) + "" +
                "}," +
                "\"owner_phone_numbers\": [" +
                "" +table.raw().get(0).get(22) + "" +
                "]," +
                "\"token\": " + table.raw().get(0).get(23) + "}");
    }
}
