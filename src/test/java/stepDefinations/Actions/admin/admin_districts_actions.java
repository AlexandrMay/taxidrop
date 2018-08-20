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

public class admin_districts_actions extends ReusableMethods {

    SQL sql = new SQL();

    private ArrayList<Integer> list = new ArrayList<Integer>();

    private StepData data;

    public admin_districts_actions(StepData data) {
        this.data = data;
    }

    private String resultToken;

    private int resultId;


    @Given("^sending /admin/districts request using (.+)$")
    public void sending_admindistricts_request_using(String token) throws Throwable {
        if (token.equals("\"true\"")) {
            resultToken = properties.getProperty("admin_father");
        } else {
            resultToken = token;
        }

        data.request = given().header("Authorization", "Bearer " + resultToken).header("Content-Type", "application/json");
    }

    @And("^Response of /admin/district.list contains (.+) and (.+)$")
    public void response_of_admindistrictlist_contains_and(String key, String value) throws Throwable {
        list = sql.getIntArrayData("SELECT * FROM districts", "id");
        if (value.equals("\"fromDB\"")) {
            data.json = data.response.then().body(key, equalTo(list));
        } else {
            data.json = data.response.then().body(key, equalTo(value));
        }
    }

    @Given("^sending /admin/district.add request using (.+), (.+), (.+), (.+), (.+)$")
    public void sending_admindistrictadd_request_using_(String token, String name, String polygon, int cityid, int cityidenabled) throws Throwable {
        String resultCityId, resultCityEnabled;
        if (cityid == -1) {
            resultCityId = null;
        } else {
            resultCityId = String.valueOf(cityid);
        }

        if (cityidenabled == -1) {
            resultCityEnabled = null;
        } else {
            resultCityEnabled = String.valueOf(cityidenabled);
        }

        if (token.equals("\"true\"")) {
            resultToken = properties.getProperty("admin_father");
        } else {
            resultToken = token;
        }

        data.request = given().header("Authorization", "Bearer " + resultToken).header("Content-Type", "application/json").
                body("{\"name\": " + name + "," +
                        "\"polygon\": " + polygon + "," +
                        "\"city_id\": " + resultCityId + "," +
                        "\"enabled\": " + resultCityEnabled +"}");
    }

    @And("^Response /admin/district.add contains (.+) and (.+)$")
    public void response_admindistrictadd_contains_and(String key, String value) throws Throwable {
        list = sql.getIntArrayData("SELECT * FROM districts WHERE name = 'Auto district'", "id");
        if (value.equals("\"fromDB_1\"")) {
            data.json = data.response.then().body(key, equalTo(list.get(0)));
            data.js = rawToJson(data.json);
            data.adminDistrictId = data.js.get("district_id");
        } else if (value.equals("\"fromDB_2\"")){
            data.json = data.response.then().body(key, equalTo(list.get(1)));
        } else {
            data.json = data.response.then().body(key, equalTo(value));
        }
    }

    @When("^PUT /admin/district.edit request with (.+) and (.+) is sent$")
    public void put_admindistrictedit_request_with_and_is_sent(String resource, String districtid) throws Throwable {
        if (districtid.equals("\"district_id\"")) {
            resultId = data.adminDistrictId;
        } else {
            resultId = Integer.parseInt(districtid);
        }
        data.response = data.request.when().put(resource + resultId);
        System.out.println(data.response.prettyPrint());
        data.r = rawToString(data.response);
    }

    @And("^Response of /district.disable contains (.+) and (.+)$")
    public void response_of_districtdisable_contains_and(String key, String value) throws Throwable {
        String resultValue;
        if (value.equals("\"repeatError\"")) {
            resultValue = "Districts with ID. '"+data.adminDistrictId+"' already disabled.";
        } else {
            resultValue = value;
        }

        if (StringUtils.isNumeric(resultValue)) {
            data.json = data.response.then().body(key, equalTo(Integer.parseInt(resultValue)));
        } else {
            data.json = data.response.then().body(key, equalTo(resultValue));
        }
    }

    @When("^DELETE /admin/district.delete request with (.+) and (.+) is sent$")
    public void delete_admindistrictdelete_request_with_and_is_sent(String resource, String districtid) throws Throwable {
        if (districtid.equals("\"district_id_1\"")) {
            resultId = data.adminDistrictId;
        } else if (districtid.equals("\"district_id_2\"")){
            resultId = data.adminDistrictId + 1;
        } else {
            Integer.parseInt(districtid);
        }
        data.response = data.request.when().delete(resource + resultId);
        System.out.println(data.response.prettyPrint());
        data.r = rawToString(data.response);
    }

}
