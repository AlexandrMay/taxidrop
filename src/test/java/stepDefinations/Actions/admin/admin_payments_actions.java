package stepDefinations.Actions.admin;

import Properties.ReusableMethods;
import Properties.SQL;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import org.apache.commons.lang3.StringUtils;
import stepDefinations.StepData;

import java.util.ArrayList;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;

public class admin_payments_actions extends ReusableMethods {

    private StepData data;

    public admin_payments_actions(StepData data) {
        this.data = data;
    }

    private String resultToken;

    private ArrayList<Integer> list = new ArrayList<Integer>();

    SQL sql = new SQL();

    @Given("^sending /admin/request using (.+)$")
    public void sending_adminrequest_using(String token) throws Throwable {
        if (token.equals("\"true\"")){
            resultToken = properties.getProperty("admin_father");
        } else {
            resultToken = token;
        }

        data.request = given().header("Authorization", "Bearer " + resultToken).header("Content-Type", "application/json");
    }

    @And("^Response /admin/passenger/payment.list contains (.+) and (.+)$")
    public void response_adminpassengerpaymentlist_contains_and(String key, String value) throws Throwable {
        list = sql.getIntArrayData("SELECT * FROM payments WHERE user_id = " + data.passengerId + "", "id");
        if (value.equals("\"fromDB\"")) {
            data.json = data.response.then().body(key, equalTo(list));
        } else {
            data.json = data.response.then().body(key, equalTo(value));
        }
    }




}
