package stepDefinations.Registration;

import Properties.ReusableMethods;
import cucumber.api.DataTable;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.When;
import stepDefinations.StepData;


import static io.restassured.RestAssured.given;

public class driver_registration extends ReusableMethods {

    private String resultPass;
    private String resultToken;

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
        if (table.raw().get(0).get(4).equals("\"driverpass\"")) {
            resultPass = convert(table.raw().get(0).get(4));
        }else {
            resultPass = table.raw().get(0).get(4);
        }
        System.out.println("Водительский пароль: " + resultPass);

        data.request = given().header("Authorization", "Key " + driverToken()).header("Content-Type", "application/json")
                .body("{" +
                "\"first_name\": " + table.raw().get(0).get(0) + "," +
                "\"last_name\": " + table.raw().get(0).get(1) + "," +
                "\"email\": " + table.raw().get(0).get(2) + "," +
                "\"phone_number\": " + table.raw().get(0).get(3) + "," +
                "\"password\": " + resultPass + "," +
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
                "\"license_plate_number\": " + table.raw().get(0).get(14) + "," +
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
                "\"token\": " + "\"" +data.driverRegistrationToken + "\"" + "}");

        System.out.println("РЕГ ТОКЕН: " + data.driverRegistrationToken);
    }

    @When("^POST request driver/registration send to correct resource$")
    public void post_request_driverregistration_send_to_correct_resource() throws Throwable {
        data.response = data.request.when().post("/driver/registration");
        System.out.println(data.response.prettyPrint());
        data.r = rawToString(data.response);
    }

    @Given("^Sending request with FB token to confirm drivers number$")
    public void sending_request_with_fb_token_to_confirm_drivers_number() throws Throwable {
        data.request = given().header("Authorization", "Key " + driverToken()).header("Content-Type", "application/json")
                .body("{\"user_type\": " + 0 + ", \"code\": " + properties.getProperty("FB_driver_token") + "}");

    }

    @When("^POST request phone.confirm is sent$")
    public void post_request_phoneconfirm_is_sent() throws Throwable {
        data.response = data.request.when().post("/phone.confirm");
        System.out.println(data.response.prettyPrint());
        data.r = rawToString(data.response);
    }

    @And("^Response contains drivers registration token$")
    public void response_contains_drivers_reg_token() throws Throwable {
        data.js = rawToJson(data.json);
        data.driverRegistrationToken = data.js.get("token");
    }

    //errors

    @Given("^Given sending driver/registration request using (.+), (.+), (.+), (.+), (.+), (.+), (.+)$")
    public void given_sending_driverregistration_request_using_(String token, String firstname, String lastname, String email, String phonenumber, String password, String registrationtoken) throws Throwable {
        if (password.equals("\"driverpass\"")) {
            resultPass = convert(password);
        }else {
            resultPass = password;
        }
        if (token.equals("\"true\"")) {
            resultToken = driverToken();
        }else {
            resultToken = token;
        }

        data.request = given().header("Authorization", "Key " + resultToken).header("Content-Type", "application/json")
                .body("{" +
                        "\"first_name\": " + firstname + "," +
                        "\"last_name\": " + lastname + "," +
                        "\"email\": " + email + "," +
                        "\"phone_number\": " + phonenumber + "," +
                        "\"password\": " + resultPass + "," +
                        "\"invite_code\": \"\" ," +
                        "\"driving_license\": \"base64\"," +
                        "\"passport\": \"base64\"," +
                        "\"photo\": \"base64\"," +
                        "\"license_number\": \"JT642S\"," +
                        "\"license_issue_date\": \"2010-01-01\"," +
                        "\"license_expire_date\": \"2030-01-01\"," +
                        "\"token\": " + registrationtoken + "}");
    }

    //authorize driver

    @Given("^sending driver/authorization request using$")
    public void sending_driverauthorization_request_using(DataTable table) throws Throwable {
        if (table.raw().get(0).get(2).equals("\"driverpass\"")) {
            resultPass = convert(table.raw().get(0).get(2));
        }else {
            resultPass = table.raw().get(0).get(2);
        }

        data.request = given().header("Authorization", "Key " + driverToken()).header("Content-Type", "application/json").
                body("{\"user_type\":" + table.raw().get(0).get(0) + ", " +
                        "\"phone_number\":" + table.raw().get(0).get(1) + ", " +
                        "\"password\":" + resultPass + "}");
    }

    @When("^POST request driver/authorization is sent$")
    public void post_request_driverauthorization_is_sent() throws Throwable {
        data.response = data.request.when().post("/authorization");
        System.out.println(data.response.prettyPrint());
        data.r = rawToString(data.response);
    }

    @And("^Response contains drivers authorization token$")
    public void response_contains_drivers_authorization_token() throws Throwable {
        data.js = rawToJson(data.json);
        String token = data.js.get("token");
        System.out.println("AUTH DRIVERS TOKEN: " + token);
        props.setProperty("driverAuthorizationToken", token);
        setSomePropertyToFile("src/main/java/Properties/token.properties");
    }



}


