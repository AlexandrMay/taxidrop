package stepDefinations.Actions.passenger;

import Properties.ReusableMethods;
import Properties.SQL;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.When;
import org.apache.commons.lang3.StringUtils;
import stepDefinations.StepData;

import java.util.ArrayList;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;

public class passenger_actions extends ReusableMethods {

    private StepData data;

    public passenger_actions(StepData data) {
        this.data = data;
    }

    private String resultToken;
    private String resultPassword;
    private String resultPasswordOld;

    SQL sql = new SQL();




    @Given("^sending passenger/profile.info request using (.+)$")
    public void sending_passengerprofileinfo_request_using(String token) throws Throwable {
        if (token.equals("\"true\"")){
        resultToken = getTempProperty("passengerAuthorizationToken", "src/main/java/Properties/token.properties");
        } else if (token.equals("\"father\"")) {
            resultToken = properties.getProperty("admin_father");
        } else {
            resultToken = token;
        }

        data.request = given().header("Authorization", "Bearer " + resultToken).header("Content-Type", "application/json");
    }

    @Given("^sending passenger/profile.edit request using (.+), (.+), (.+), (.+), (.+)$")
    public void sending_passengerprofileedit_request_using_(String token, String photo, String firstname, String lastname, String email) throws Throwable {
        if (token.equals("\"true\"")){
            resultToken = getTempProperty("passengerAuthorizationToken", "src/main/java/Properties/token.properties");
        } else {
            resultToken = token;
        }
        data.request = given().header("Authorization", "Bearer " + resultToken).header("Content-Type", "application/json").
                body("{\"photo\":" + photo + ", " +
                      "\"first_name\":" + firstname + ", " +
                       "\"last_name\":" + lastname + "," +
                        "\"email\":" + email + "}");
    }

    @When("^PUT request send to (.+)$")
    public void put_request_send_to(String resource) throws Throwable {
        data.response = data.request.when().put(resource);
        System.out.println(data.response.prettyPrint());
        data.r = rawToString(data.response);
    }

    @Given("^sending /passenger/pass.change request using (.+), (.+), (.+)$")
    public void sending_passengerpasschange_request_using_(String token, String password, String passwordold) throws Throwable {
        if (token.equals("\"true\"")){
            resultToken = getTempProperty("passengerAuthorizationToken", "src/main/java/Properties/token.properties");
        } else {
            resultToken = token;
        }
        if (password.equals("\"false\"") || password.equals("null")){
            resultPassword = password;
        } else {
            resultPassword = convert(password);
        }
        if (passwordold.equals("\"false\"") || passwordold.equals("null")){
            resultPasswordOld = passwordold;
        } else {
            resultPasswordOld = convert(passwordold);
        }

        data.request = given().header("Authorization", "Bearer " + resultToken).header("Content-Type", "application/json").
                body("{\"password\":" + resultPassword + ", " +
                        "\"password_old\":" + resultPasswordOld + "}");
    }

    @Given("^sending /passenger/address.add request using (.+), (.+), (.+)$")
    public void sending_passengeraddressadd_request_using_(String token, String address, String name) throws Throwable {
        if (token.equals("\"true\"")){
            resultToken = getTempProperty("passengerAuthorizationToken", "src/main/java/Properties/token.properties");
        } else {
            resultToken = token;
        }

        data.request = given().header("Authorization", "Bearer " + resultToken).header("Content-Type", "application/json").
                body("{\"address\":" + address + ", " +
                        "\"name\":" + name + "}");
    }

    @And("^Response /passenger/address.add contains (.+) and (.+)$")
    public void response_passengeraddressadd_contains_and(String key, String value) throws Throwable {
        String resultValue;
        if (value.equals("\"fromBD\"")) {
            resultValue = String.valueOf(data.addressId());
        } else {
            resultValue = value;
        }

        if (StringUtils.isNumeric(resultValue)) {
            data.json = data.response.then().body(key, equalTo(Integer.parseInt(resultValue)));
            data.js = rawToJson(data.json);
            data.passengerAddressId = data.js.get("address_id");
        } else {
            data.json = data.response.then().body(key, equalTo(resultValue));
        }
    }

    @Given("^sending /passenger/address.edit request using (.+), (.+), (.+)$")
    public void sending_passengeraddressedit_request_using_(String token, String address, String name) throws Throwable {
        if (token.equals("\"true\"")){
            resultToken = getTempProperty("passengerAuthorizationToken", "src/main/java/Properties/token.properties");
        } else {
            resultToken = token;
        }

        data.request = given().header("Authorization", "Bearer " + resultToken).header("Content-Type", "application/json").
                body("{\"address\":" + address + ", " +
                        "\"name\":" + name + "}");
    }

    @When("^PUT /passenger/address.edit request with (.+) and (.+) is sent$")
    public void put_passengeraddressedit_request_with_and_is_sent(String resource, String id) throws Throwable {
        int resultId;
        if (id.equals("\"addressId\"")) {
            resultId = data.passengerAddressId;
        } else {
            resultId = Integer.parseInt(id);
        }
        data.response = data.request.when().put(resource + resultId);
        System.out.println(data.response.prettyPrint());
        data.r = rawToString(data.response);
    }

    @Given("^sending /passenger/address request using (.+)$")
    public void sending_passengeraddresslist_request_using(String token) throws Throwable {
        if (token.equals("\"true\"")){
            resultToken = getTempProperty("passengerAuthorizationToken", "src/main/java/Properties/token.properties");
        } else {
            resultToken = token;
        }
        data.request = given().header("Authorization", "Bearer " + resultToken).header("Content-Type", "application/json");
    }

    @And("^Response /passenger/address.list contains (.+) and (.+)$")
    public void response_passengeraddresslist_contains_and(String key, String value) throws Throwable {
        ArrayList<Integer> list = new ArrayList<Integer>();
        list.add(0, sql.getIntData("SELECT * FROM addresses WHERE name ='New Bot’s address'", "id"));
        if (value.equals("\"addressId\"")) {
            data.json = data.response.then().body(key, equalTo(list));

        } else {
            data.json = data.response.then().body(key, equalTo(value));
        }
    }

    @When("^GET /passenger/address.info request with (.+) and (.+) is sent$")
    public void get_passengeraddressinfo_request_with_and_is_sent(String resource, String id) throws Throwable {
        int resultId;
        if (id.equals("\"addressId\"")) {
            resultId = data.passengerAddressId;
        } else {
            resultId = Integer.parseInt(id);
        }
        data.response = data.request.when().get(resource + resultId);
        System.out.println(data.response.prettyPrint());
        data.r = rawToString(data.response);
    }

    @And("^Response /passenger/address.info contains (.+) and (.+)$")
    public void response_passengeraddressinfo_contains_and(String key, String value) throws Throwable {
        if (value.equals("\"addressId\"")) {
            data.json = data.response.then().body(key, equalTo(data.passengerAddressId));
        } else {
            data.json = data.response.then().body(key, equalTo(value));
        }
    }

}
