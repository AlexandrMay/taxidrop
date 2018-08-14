package stepDefinations.Actions.driver;

import Properties.ReusableMethods;
import Properties.SQL;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import stepDefinations.StepData;

import java.util.ArrayList;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;

public class driver_notifications extends ReusableMethods {

    private ArrayList<Integer> list = new ArrayList<Integer>();

    SQL sql = new SQL();

    private String resultToken;

    private StepData data;

    public driver_notifications(StepData data) {
        this.data = data;
    }

    @Given("^sending /driver/notification request using (.+)$")
    public void sending_drivernotification_request_using(String token) throws Throwable {
        if (token.equals("\"true\"")){
            resultToken = properties.getProperty("first_driver");
        } else {
            resultToken = token;
        }
        data.request = given().header("Authorization", "Bearer " + resultToken).header("Content-Type", "application/json");
    }

    @And("^Response /driver/notification.list contains (.+) and (.+)$")
    public void response_drivernotificationlist_contains_and(String key, String value) throws Throwable {
        list = sql.getIntArrayData("SELECT * FROM notifications WHERE user_id = 1", "id");
        if (value.equals("\"fromDB\"")) {
            data.json = data.response.then().body(key, equalTo(list));
        } else {
            data.json = data.response.then().body(key, equalTo(value));
        }
    }

    @And("^Response /driver/notification.count contains (.+) and (.+)$")
    public void response_drivernotificationcount_contains_and(String key, String value) throws Throwable {
        if (value.equals("\"fromDB\"")) {
            data.json = data.response.then().body(key, equalTo(sql.getCountData("SELECT COUNT(*) FROM notifications WHERE user_id = 1 AND seen = 0")));
        } else {
            data.json = data.response.then().body(key, equalTo(value));
        }
    }






}
