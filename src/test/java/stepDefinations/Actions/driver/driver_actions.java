package stepDefinations.Actions.driver;

import Properties.ReusableMethods;
import Properties.SQL;
import cucumber.api.DataTable;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.When;
import stepDefinations.StepData;

import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;

public class driver_actions extends ReusableMethods {

    private String resultToken;

    private StepData data;

    public driver_actions(StepData data) {
        this.data = data;
    }

    private ArrayList<Integer> list = new ArrayList<Integer>();

    SQL sql = new SQL();


    @Given("^Sending request with correct token$")
    public void sending_request_with_correct_token() throws Throwable {
        data.request = given().header("Authorization", "Bearer " + getTempProperty("driverAuthorizationToken", "src/main/java/Properties/token.properties")).header("Content-Type", "application/json");
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
            resultToken = getTempProperty("driverAuthorizationToken", "src/main/java/Properties/token.properties");
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
            resultToken = getTempProperty("driverAuthorizationToken", "src/main/java/Properties/token.properties");
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
            resultToken = getTempProperty("driverAuthorizationToken", "src/main/java/Properties/token.properties");
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
            resultToken = getTempProperty("driverAuthorizationToken", "src/main/java/Properties/token.properties");
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

    @Given("^sending /driver/district.list request using (.+)$")
    public void sending_driverdistrictlist_request_using(String token) throws Throwable {
        if (token.equals("\"true\"")) {
            resultToken = getTempProperty("driverAuthorizationToken", "src/main/java/Properties/token.properties");
        }
        else {
            resultToken = token;
        }
        data.request = given().header("Authorization", "Bearer " + resultToken);
    }

    @And("^Response of /driver/district.list contains (.+) and (.+)$")
    public void response_of_driverdistrictlist_contains_and(String key, String value) throws Throwable {
        list = sql.getIntArrayData("SELECT * FROM districts WHERE city_id = 1 AND enabled = 1", "id");
        if (value.equals("\"fromDB\"")) {
            data.json = data.response.then().body(key, equalTo(list));
        } else {
            data.json = data.response.then().body(key, equalTo(value));
        }
    }

    @Given("^sending /countries.list request using (.+)$")
    public void sending_countrieslist_request_using(String token) throws Throwable {
        if (token.equals("\"true\"")) {
            resultToken = driverToken();
        }
        else {
            resultToken = token;
        }
        data.request = given().header("Authorization", "Key " + resultToken);
    }

    @And("^Response of /countries.list contains (.+) and (.+)$")
    public void response_of_countrieslist_contains_and(String key, String value) throws Throwable {
        list = sql.getIntArrayData("SELECT * FROM countries", "id");
        if (value.equals("\"fromDB\"")) {
            data.json = data.response.then().body(key, equalTo(list));
        } else {
            data.json = data.response.then().body(key, equalTo(value));
        }
    }

    @Given("^sending /driver/district.block using (.+) and of districts contains (.+), (.+), (.+)$")
    public void sending_driverdistrictblock_using_and_of_districts_contains(String token, int distr1, int distr2, int distr3) throws Throwable {
        if(distr1 == 0 & distr2 == 0 & distr3 == 0) {
            list.isEmpty();
        } else {
            list.add(0, distr1);
            list.add(1, distr2);
            list.add(2, distr3);
        }
        if (token.equals("\"true\"")) {
            resultToken = getTempProperty("driverAuthorizationToken", "src/main/java/Properties/token.properties");
        }
        else {
            resultToken = token;
        }
        data.request = given().header("Authorization", "Bearer " + resultToken).
                body("{\"districts\": " + list + "}");
    }

}
