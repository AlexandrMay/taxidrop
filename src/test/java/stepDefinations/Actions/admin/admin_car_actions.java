package stepDefinations.Actions.admin;

import Properties.ReusableMethods;
import Properties.SQL;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.When;
import org.apache.commons.lang3.StringUtils;
import stepDefinations.StepData;

import java.util.ArrayList;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;

public class admin_car_actions extends ReusableMethods {

    SQL sql = new SQL();

    private StepData data;

    public admin_car_actions(StepData data) {
        this.data = data;
    }

    private String resultToken;

    private ArrayList<Integer> list = new ArrayList<Integer>();

    @Given("^sending /admin/cars request using (.+)$")
    public void sending_admincars_request_using(String token) throws Throwable {
        if (token.equals("\"true\"")) {
            resultToken = properties.getProperty("admin_father");
        } else {
            resultToken = token;
        }

        data.request = given().header("Authorization", "Bearer " + resultToken).header("Content-Type", "application/json");
    }

    @When("^GET /admin/passenger.cars request with (.+) and (.+) is sent$")
    public void get_adminpassengercars_request_with_and_is_sent(String resource, String passengerid) throws Throwable {
        int resultId;
        if (passengerid.equals("\"trueId\"")) {
            resultId = data.passengerId;
        } else {
            resultId = Integer.parseInt(passengerid);
        }
        data.response = data.request.when().get(resource + resultId);
        System.out.println(data.response.prettyPrint());
        data.r = rawToString(data.response);
    }

    @And("^Response /admin/passenger.cars contains (.+) and (.+)$")
    public void response_adminpassengercars_contains_and(String key, String value) throws Throwable {
        list = sql.getIntArrayData("SELECT * FROM cars WHERE owner_id = " + data.passengerId + "", "id");
        if (value.equals("\"fromDB\"")) {
            data.json = data.response.then().body(key, equalTo(list));
        } else {
            data.json = data.response.then().body(key, equalTo(value));
        }
    }

    @When("^GET /admin/driver.cars request with (.+) and (.+) is sent$")
    public void get_admindrivercars_request_with_and_is_sent(String resource, String driverid) throws Throwable {
        int resultId;
        if (driverid.equals("\"trueId\"")) {
            resultId = data.driverID;
        } else {
            resultId = Integer.parseInt(driverid);
        }
        data.response = data.request.when().get(resource + resultId);
        System.out.println(data.response.prettyPrint());
        data.r = rawToString(data.response);
    }

    @And("^Response /admin/driver.cars contains (.+) and (.+)$")
    public void response_admindrivercars_contains_and(String key, String value) throws Throwable {
        list = sql.getIntArrayData("SELECT * FROM cars WHERE owner_id = " + data.driverID + "", "id");
        if (value.equals("\"fromDB\"")) {
            data.json = data.response.then().body(key, equalTo(list));
        } else {
            data.json = data.response.then().body(key, equalTo(value));
        }
    }

    @When("^PUT /admin/car.approve request with (.+) and (.+) is sent$")
    public void put_admincarapprove_request_with_and_is_sent(String resource, String carid) throws Throwable {
        int resultId;
        if (carid.equals("\"carId\"")) {
            resultId = data.passengerCarId;
        } else {
            resultId = Integer.parseInt(carid);
        }
        data.response = data.request.when().put(resource + resultId);
        System.out.println(data.response.prettyPrint());
        data.r = rawToString(data.response);
    }

    @And("^Response /admin/car.approve contains (.+) and (.+)$")
    public void response_admincarapprove_contains_and(String key, String value) throws Throwable {
        if (value.equals("\"carId\"")) {
            data.json = data.response.then().body(key, equalTo(data.passengerCarId));
        } else if (value.equals("\"repeat\"")) {
            data.json = data.response.then().body(key, equalTo("Сar with ID '" + data.passengerCarId + "' already on active status."));
        } else {
            if (StringUtils.isNumeric(value)) {
                data.json = data.response.then().body(key, equalTo(Integer.parseInt(value)));
            } else {
                data.json = data.response.then().body(key, equalTo(value));
            }
        }
    }

    @Given("^sending /admin/car.edit request using (.+), (.+), (.+), (.+), (.+), (.+), (.+)$")
    public void sending_admincaredit_request_using_(String token, String make, String model, int year, String carphoto, String licenseplatenumber, String color) throws Throwable {
        String resultyear;
        if (token.equals("\"true\"")) {
            resultToken = properties.getProperty("admin_father");
        } else {
            resultToken = token;
        }
        if (year == 0) {
            resultyear = null;
        } else {
            resultyear = String.valueOf(year);
        }

        data.request = given().header("Authorization", "Bearer " + resultToken).header("Content-Type", "application/json").
                body("{\"make\":" + make + "," +
                          "\"model\":" + model + "," +
                          "\"year\":" + resultyear + "," +
                          "\"car_photo\":" + carphoto + "," +
                          "\"license_plate_number\":" + licenseplatenumber + "," +
                          "\"color\":" + color + "}");
    }

    @When("^DELETE /admin/car.delete request with (.+) and (.+) is sent$")
    public void delete_admincardelete_request_with_and_is_sent(String resource, String carid) throws Throwable {
        int resultId;
        if (carid.equals("\"carId\"")) {
            resultId = data.passengerCarId;
        } else {
            resultId = Integer.parseInt(carid);
        }
        data.response = data.request.when().delete(resource + resultId);
        System.out.println(data.response.prettyPrint());
        data.r = rawToString(data.response);
    }

    @And("^Response /admin/car.delete contains (.+) and (.+)$")
    public void response_admincardelete_contains_and(String key, String value) throws Throwable {
        if (value.equals("\"repeat\"")) {
            data.json = data.response.then().body(key, equalTo("Car with ID '" + data.passengerCarId + "' does not exist."));
        } else {
            if (StringUtils.isNumeric(value)) {
                data.json = data.response.then().body(key, equalTo(Integer.parseInt(value)));
            } else {
                data.json = data.response.then().body(key, equalTo(value));
            }
        }
    }








}
