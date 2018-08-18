package stepDefinations.Actions.driver;

import Properties.ReusableMethods;
import Properties.SQL;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.When;
import stepDefinations.StepData;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;

public class driver_work_actions extends ReusableMethods {

    private ArrayList<Integer> list = new ArrayList<Integer>();

    private ArrayList<String> keyList = new ArrayList<String>();
    private ArrayList<Integer> keyValue = new ArrayList<Integer>();

    public void setArray() throws SQLException {

        char[] arr = sql.getString("SELECT * FROM order_statistic WHERE order_id = 53", "trip_time").toCharArray();

        String a = String.valueOf(arr[1]);

        int b = Integer.parseInt(a);

        keyList.add(0, "total_trips");
        keyList.add(1, "total_distance");
        keyList.add(2, "total_hours");
        keyList.add(3, "total_reviews");
        keyList.add(4, "rating");
        keyList.add(5, "wallet");

        keyValue.add(0, sql.getCountData("SELECT COUNT(*) FROM orders WHERE driver_id = 1 AND status = 2"));
        keyValue.add(1, sql.getIntData("SELECT * FROM order_statistic WHERE order_id = 53", "trip_distance"));
        keyValue.add(2, b);
        keyValue.add(3, sql.getCountData("SELECT COUNT(*) FROM reviews WHERE user_id = 1"));
        keyValue.add(4, sql.getIntData("SELECT * FROM reviews WHERE user_id = 1", "common_rate"));
        keyValue.add(5, sql.getIntData("SELECT * FROM wallets WHERE user_id = 1", "total_amount"));

    }

    SQL sql = new SQL();

    private String resultToken;

    private StepData data;

    public driver_work_actions(StepData data) {
        this.data = data;
    }


    @Given("^sending /driver/work request using (.+)$")
    public void sending_driverwork_request_using(String token) throws Throwable {
        if (token.equals("\"true\"")){
            resultToken = properties.getProperty("first_driver");
        } else {
            resultToken = token;
        }
        data.request = given().header("Authorization", "Bearer " + resultToken).header("Content-Type", "application/json");
    }

    @And("^Response /driver/work.cars contains (.+) and (.+)$")
    public void response_driverworkcars_contains_and(String key, String value) throws Throwable {
        list = sql.getIntArrayData("SELECT * FROM cars WHERE driver_id = 1 AND broken = 0", "id");
        if (value.equals("\"fromDB\"")) {
            data.json = data.response.then().body(key, equalTo(list));
        } else {
            data.json = data.response.then().body(key, equalTo(value));
        }
    }

    @Given("^sending /driver/work.radius request using (.+), (.+)$")
    public void sending_driverworkradius_request_using(String token, String radius) throws Throwable {
        if (token.equals("\"true\"")){
            resultToken = properties.getProperty("first_driver");
        } else {
            resultToken = token;
        }
        data.request = given().header("Authorization", "Bearer " + resultToken).header("Content-Type", "application/json").
                body("{\"radius\": " + radius + "}");

    }

    @Given("^sending /driver/work.statistics request$")
    public void sending_driverworkstatistics_request() throws Throwable {
        data.request = given().header("Authorization", "Bearer " + properties.getProperty("first_driver")).header("Content-Type", "application/json");
    }

    @When("^GET /driver/work.statistics request is sent$")
    public void get_driverworkstatistics_request_is_sent() throws Throwable {
        data.response = data.request.when().get("/driver/work.statistics");
        System.out.println(data.response.prettyPrint());
        data.r = rawToString(data.response);
    }

    @And("^Response contains data from DB$")
    public void response_contains_data_from_db() throws Throwable {
        setArray();
        for (int i = 0; i < keyValue.size(); i++) {
            data.json = data.response.then().body(keyList.get(i), equalTo(keyValue.get(i)));
        }
    }

    @Given("^sending /driver/work/closing.destination request using (.+), (.+), (.+), (.+)$")
    public void sending_driverworkclosingdestination_request_using_(String token, String lat, String lng, String address) throws Throwable {
        if (token.equals("\"true\"")){
            resultToken = properties.getProperty("first_driver");
        } else {
            resultToken = token;
        }
        data.request = given().header("Authorization", "Bearer " + resultToken).header("Content-Type", "application/json").
                body("{\"lat\": " + lat + "," +
                        "\"lng\": " + lng + "," +
                        "\"address\": " + address + "}");
    }






}
