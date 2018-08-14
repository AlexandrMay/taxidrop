package stepDefinations.Actions.passenger;

import Properties.ReusableMethods;
import Properties.SQL;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import stepDefinations.StepData;

import java.util.ArrayList;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;


public class passenger_notifications extends ReusableMethods {

    private ArrayList<Integer> list = new ArrayList<Integer>();

    SQL sql = new SQL();

    private String resultToken;

    private StepData data;

    public passenger_notifications(StepData data) {
        this.data = data;
    }


    @Given("^sending /passenger/notification request using (.+)$")
    public void sending_passengernotification_request_using(String token) throws Throwable {
        if (token.equals("\"true\"")){
            resultToken = properties.getProperty("first_passenger");
        } else {
            resultToken = token;
        }
        data.request = given().header("Authorization", "Bearer " + resultToken).header("Content-Type", "application/json");
    }

    @And("^Response /passenger/notification.list contains (.+) and (.+)$")
    public void response_passengernotificationlist_contains_and(String key, String value) throws Throwable {
        list = sql.getIntArrayData("SELECT * FROM notifications WHERE user_id = 2", "id");
        if (value.equals("\"fromDB\"")) {
            data.json = data.response.then().body(key, equalTo(list));
        } else {
            data.json = data.response.then().body(key, equalTo(value));
        }
    }

    @And("^Response /passenger/notification.count contains (.+) and (.+)$")
    public void response_passengernotificationcount_contains_and(String key, String value) throws Throwable {
        if (value.equals("\"fromDB\"")) {
            data.json = data.response.then().body(key, equalTo(sql.getCountData("SELECT COUNT(*) FROM notifications WHERE user_id = 2 AND seen = 0")));
        } else {
            data.json = data.response.then().body(key, equalTo(value));
        }
    }


}


