package stepDefinations.Actions.admin;

import Properties.ReusableMethods;
import Properties.SQL;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.When;
import stepDefinations.StepData;

import java.util.ArrayList;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;

public class admin_cities_actions extends ReusableMethods {

    SQL sql = new SQL();

    private ArrayList<Integer> list = new ArrayList<Integer>();

    private StepData data;

    public admin_cities_actions(StepData data) {
        this.data = data;
    }

    private String resultToken;

    private int resultId;

    @Given("^sending /admin/city request using (.+)$")
    public void sending_admincity_request_using(String token) throws Throwable {
        if (token.equals("\"true\"")) {
            resultToken = properties.getProperty("admin_father");
        } else {
            resultToken = token;
        }

        data.request = given().header("Authorization", "Bearer " + resultToken).header("Content-Type", "application/json");
    }

    @And("^Response of /admin/city.list contains (.+) and (.+)$")
    public void response_of_admincitylist_contains_and(String key, String value) throws Throwable {
        list = sql.getIntArrayData("SELECT * FROM cities", "id");
        if (value.equals("\"fromDB\"")) {
            data.json = data.response.then().body(key, equalTo(list));
        } else {
            data.json = data.response.then().body(key, equalTo(value));
        }
    }

    @Given("^sending /admin/city.add request using (.+), (.+), (.+), (.+), (.+), (.+)$")
    public void sending_admincityadd_request_using_(String token, String name, int enabled, int countryid, float lat, float lng) throws Throwable {
        String resultEnable, resultCountryId, resultLat, resultLng;
        if (enabled == -1) {
            resultEnable = null;
        } else {
            resultEnable = String.valueOf(enabled);
        }

        if (countryid == -1) {
            resultCountryId = null;
        } else {
            resultCountryId = String.valueOf(countryid);
        }

        if (lat == -1) {
            resultLat = null;
        } else {
            resultLat = String.valueOf(lat);
        }

        if (lng == -1) {
            resultLng = null;
        } else {
            resultLng = String.valueOf(lng);
        }


        if (token.equals("\"true\"")) {
            resultToken = properties.getProperty("admin_father");
        } else {
            resultToken = token;
        }

        data.request = given().header("Authorization", "Bearer " + resultToken).header("Content-Type", "application/json").
                body("{\"name\": " + name + "," +
                        "\"enabled\": " + resultEnable + "," +
                        "\"country_id\": " + resultCountryId + "," +
                        "\"coords\": {" +
                        "\"lat\": " + resultLat + "," +
                        "\"lng\": " + resultLng + "}}");
    }

    @And("^Response /admin/city.add contains (.+) and (.+)$")
    public void response_admincityadd_contains_and(String key, String value) throws Throwable {
        list = sql.getIntArrayData("SELECT * FROM cities WHERE name = 'Auto city'", "id");
        if (value.equals("\"fromDB_1\"")) {
            data.json = data.response.then().body(key, equalTo(list.get(0)));
            data.js = rawToJson(data.json);
            data.adminCityId = data.js.get("city_id");
        } else if (value.equals("\"fromDB_2\"")){
            data.json = data.response.then().body(key, equalTo(list.get(1)));
        } else {
            data.json = data.response.then().body(key, equalTo(value));
        }
    }

    @When("^PUT /admin/city.edit request with (.+) and (.+) is sent$")
    public void put_admincityedit_request_with_and_is_sent(String resource, String cityid) throws Throwable {
        if (cityid.equals("\"cityID\"")) {
            resultId = data.adminCityId;
        } else {
            resultId = Integer.parseInt(cityid);
        }
        data.response = data.request.when().put(resource + resultId);
        System.out.println(data.response.prettyPrint());
        data.r = rawToString(data.response);
    }

    @When("^DELETE /admin/city.delete request with (.+) and (.+) is sent$")
    public void delete_admincitydelete_request_with_and_is_sent(String resource, String cityid) throws Throwable {
        if (cityid.equals("\"city_id_1\"")) {
            resultId = data.adminCityId;
        } else if (cityid.equals("\"city_id_2\"")){
            resultId = data.adminCityId + 1;
        } else {
            Integer.parseInt(cityid);
        }
        data.response = data.request.when().delete(resource + resultId);
        System.out.println(data.response.prettyPrint());
        data.r = rawToString(data.response);
    }


}
