package stepDefinations.Actions;

import Properties.ReusableMethods;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.When;
import stepDefinations.StepData;

import static io.restassured.RestAssured.given;

public class admin_driver_actions extends ReusableMethods {

    private StepData data;

    public admin_driver_actions(StepData data) {
        this.data = data;
    }

    private String resultToken;


    @Given("^sending /admin/drivers.list using (.+)$")
    public void sending_admindriverslist_using(String token) throws Throwable {
        if (token.equals("\"true\"")) {
            resultToken = properties.getProperty("admin_father");
        } else {
            resultToken = token;
        }

        data.request = given().header("Authorization", "Bearer " + resultToken).header("Content-Type", "application/json");
    }

    @Given("^sending /admin/driver.get using (.+)$")
    public void sending_admindriverget_using(String token) throws Throwable {
        if (token.equals("\"true\"")) {
            resultToken = properties.getProperty("admin_father");
        } else {
            resultToken = token;
        }

        data.request = given().header("Authorization", "Bearer " + resultToken).header("Content-Type", "application/json");
    }

    @Given("^sending /admin/driver.edit using (.+), (.+), (.+), (.+)$")
    public void sending_admindriveredit_using_(String token, String firstname, String lastname, String email) throws Throwable {
        if (token.equals("\"true\"")) {
            resultToken = properties.getProperty("admin_father");
        } else {
            resultToken = token;
        }

        data.request = given().header("Authorization", "Bearer " + resultToken).header("Content-Type", "application/json").
                body("{\"first_name\":" + firstname + "," +
                        "\"last_name\":" + lastname + "," +
                        "\"email\":" + email + "}");
    }

    @Given("^sending /admin/driver.delete using (.+)$")
    public void sending_admindriverdelete_using(String token) throws Throwable {
        if (token.equals("\"true\"")) {
            resultToken = properties.getProperty("admin_father");
        } else {
            resultToken = token;
        }

        data.request = given().header("Authorization", "Bearer " + resultToken).header("Content-Type", "application/json");
    }

    @When("^DELETE request send to (.+) with (.+)$")
    public void delete_request_send_to_with(String resource, int driver_id) throws Throwable {
        data.response = data.request.when().delete(resource + driver_id);
        System.out.println(data.response.prettyPrint());
        data.r = rawToString(data.response);
    }

    @Given("^sending /admin/driver.block using (.+)$")
    public void sending_admindriverblock_using(String token) throws Throwable {
        if (token.equals("\"true\"")) {
            resultToken = properties.getProperty("admin_father");
        } else {
            resultToken = token;
        }

        data.request = given().header("Authorization", "Bearer " + resultToken).header("Content-Type", "application/json");
    }

    @Given("^sending /admin/driver.activate using (.+)$")
    public void sending_admindriveractivate_using(String token) throws Throwable {
        if (token.equals("\"true\"")) {
            resultToken = properties.getProperty("admin_father");
        } else {
            resultToken = token;
        }

        data.request = given().header("Authorization", "Bearer " + resultToken).header("Content-Type", "application/json");
    }

    @Given("^sending /admin/driver.applications using (.+)$")
    public void sending_admindriverapplications_using(String token) throws Throwable {
        if (token.equals("\"true\"")) {
            resultToken = properties.getProperty("admin_father");
        } else {
            resultToken = token;
        }

        data.request = given().header("Authorization", "Bearer " + resultToken).header("Content-Type", "application/json");
    }

}
