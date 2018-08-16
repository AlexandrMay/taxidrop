package stepDefinations.common;

import Properties.ReusableMethods;
import Properties.SQL;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.testng.Assert;
import stepDefinations.StepData;

import java.util.ArrayList;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;


public class setting_car_to_driver extends ReusableMethods {

    private ArrayList<Integer> list = new ArrayList<Integer>();

    private StepData data;

    public setting_car_to_driver(StepData data) {
        this.data = data;
    }

    SQL sql = new SQL();

    private String resultToken;

    @Given("^sending /driver/car using (.+)$")
    public void sending_drivercar_using(String token) throws Throwable {
        if (token.equals("\"true\"")){
            resultToken = properties.getProperty("first_driver");
        } else {
            resultToken = token;
        }
        data.request = given().header("Authorization", "Bearer " + resultToken).header("Content-Type", "application/json");
    }

    @Given("^sending admin/notification.status request with status \"([^\"]*)\"$")
    public void sending_adminnotificationstatus_request_with_status_something(int strArg1) throws Throwable {
        data.request = given().header("Authorization", "Bearer " + properties.getProperty("admin_father")).header("Content-Type", "application/json").
                body("{\"status\":" + strArg1 + "}");
    }

    @When("^PUT admin/notification.status request with notification_type \"([^\"]*)\" is sent$")
    public void put_adminnotificationstatus_request_is_sent(int type) throws Throwable {
        data.response = data.request.when().put("/admin/notification.status/" + sql.getIntData("SELECT * FROM notifications WHERE type = " + type + " AND creator_id = 1", "id"));
        System.out.println(data.response.prettyPrint());
        data.r = rawToString(data.response);
    }

    @And("^Notification has \"([^\"]*)\" status in DB$")
    public void notification_has_something_status_in_db(int strArg1) throws Throwable {
        int counter = sql.getCountData("SELECT COUNT(*) FROM notifications WHERE type = 15 AND creator_id = 1");
        for (int i = 0; i < counter; i++) {
            list.add(i, strArg1);
        }
        Assert.assertEquals(list, sql.getIntArrayData("SELECT * FROM notifications WHERE type = 15 AND creator_id = 1", "status"));
    }

    @Given("^sending /passenger/driver using (.+)$")
    public void sending_passengerdriverproposal_using(String token) throws Throwable {
        if (token.equals("\"true\"")){
            resultToken = properties.getProperty("first_passenger");
        } else {
            resultToken = token;
        }
        data.request = given().header("Authorization", "Bearer " + resultToken).header("Content-Type", "application/json");
    }

    @Then("^Remove broken status from car$")
    public void remove_broken_status_from_car() throws Throwable {
        sql.setData("UPDATE cars SET broken = 0 WHERE cars.id = 2");
    }

    @When("^DELETE request send to (.+)$")
    public void delete_request_send_to(String resource) throws Throwable {
        data.response = data.request.when().delete(resource);
        System.out.println(data.response.prettyPrint());
        data.r = rawToString(data.response);
    }

    @And("^Response /passenger/driver.search contains (.+) and (.+)$")
    public void response_passengerdriversearch_contains_and(String key, String value) throws Throwable {
        list = sql.getIntArrayData("SELECT * FROM users WHERE type = 0", "id");
        if (value.equals("\"fromDB\"")) {
            data.json = data.response.then().body(key, equalTo(list));
        } else {
            data.json = data.response.then().body(key, equalTo(value));
        }
    }

    @Given("^sending /admin/driver using (.+)$")
    public void sending_admindriver_using(String token) throws Throwable {
        if (token.equals("\"true\"")){
            resultToken = properties.getProperty("admin_father");
        } else {
            resultToken = token;
        }
        data.request = given().header("Authorization", "Bearer " + resultToken).header("Content-Type", "application/json");
    }

    @Given("^car is mocked to driver from DB$")
    public void car_is_mocked_to_driver_from_db() throws Throwable {
        sql.setData("UPDATE cars SET driver_id = 1 WHERE cars.id = 2");
    }


}
