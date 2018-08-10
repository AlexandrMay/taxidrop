package stepDefinations.Actions;

import Properties.ReusableMethods;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.When;
import stepDefinations.StepData;

import static io.restassured.RestAssured.given;

public class passenger_actions extends ReusableMethods {

    private StepData data;

    public passenger_actions(StepData data) {
        this.data = data;
    }

    private String resultToken;
    private String resultPassword;
    private String resultPasswordOld;



    @Given("^sending passenger/profile.info request using (.+)$")
    public void sending_passengerprofileinfo_request_using(String token) throws Throwable {
        if (token.equals("\"true\"")){
        resultToken = getTempProperty("passengerAuthorizationToken", "src/main/java/Properties/passengerAuthorizationToken.properties");
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
            resultToken = getTempProperty("passengerAuthorizationToken", "src/main/java/Properties/passengerAuthorizationToken.properties");
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
            resultToken = getTempProperty("passengerAuthorizationToken", "src/main/java/Properties/passengerAuthorizationToken.properties");
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

}
