package stepDefinations.Actions.admin;

import Properties.ReusableMethods;
import Properties.SQL;
import cucumber.api.DataTable;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.apache.commons.lang3.StringUtils;
import stepDefinations.StepData;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;

public class admin_notifications extends ReusableMethods {

    SQL sql = new SQL();

    private String resultToken;

    private StepData data;

    public admin_notifications(StepData data) {
        this.data = data;
    }


    @Given("^sending /admin/notifications request using (.+)$")
    public void sending_adminnotificationslist_request_using(String token) throws Throwable {
        if (token.equals("\"true\"")){
            resultToken = properties.getProperty("admin_father");
        } else {
            resultToken = token;
        }

        data.request = given().header("Authorization", "Bearer " + resultToken).header("Content-Type", "application/json");
    }

    @And("^Response /admin/notifications.list contains (.+) and (.+)$")
    public void response_adminnotificationslist_contains_and(String key, String value) throws Throwable {
        String resultValue;
        if (value.equals("\"fromDB\"")) {
            resultValue = String.valueOf(data.notifications());

        } else {
            resultValue = value;
        }

        if (StringUtils.isNumeric(resultValue)) {
            data.json = data.response.then().body(key, equalTo(Integer.parseInt(resultValue)));
        } else {
            data.json = data.response.then().body(key, equalTo(resultValue));
        }
    }

    @Given("^sending /admin/notification.status request using (.+), (.+)$")
    public void sending_adminnotificationstatus_request_using_(String token, int notificationstatus) throws Throwable {
        if (token.equals("\"true\"")){
            resultToken = properties.getProperty("admin_father");
        } else {
            resultToken = token;
        }

        data.request = given().header("Authorization", "Bearer " + resultToken).header("Content-Type", "application/json").
                body("{\"status\":" + notificationstatus + "}");
    }

    @And("^Set 0 status to this notification from DB$")
    public void set_0_status_to_this_notification_from_db() throws Throwable {
        sql.setData("UPDATE notifications SET status = 0 WHERE notifications.id = 1");
    }

    @Given("^sending /admin/notification.status$")
    public void sending_adminnotificationstatus() throws Throwable {
        data.request = given().header("Authorization", "Bearer " + properties.getProperty("admin_father")).header("Content-Type", "application/json").
                body("{\"status\": " + 0 + "}");
    }

    @When("^PUT request /admin/notification.status is sent$")
    public void put_request_adminnotificationstatus_is_sent() throws Throwable {
        data.response = data.request.when().put("/admin/notification.status/1");
        System.out.println(data.response.prettyPrint());
        data.r = rawToString(data.response);
    }

    @Then("^Set 0 status to this notification from DB after$")
    public void set_0_status_to_this_notification_from_db_after(DataTable table) throws Throwable {
        data.json = data.response.then().body(table.raw().get(0).get(0), equalTo(table.raw().get(0).get(1)));
        sql.setData("UPDATE notifications SET status = 0 WHERE notifications.id = 1");
    }


    @Given("^sending /admin/notification.answer request using (.+), (.+)$")
    public void sending_adminnotificationanswer_request_using_(String token, String text) throws Throwable {
        if (token.equals("\"true\"")){
            resultToken = properties.getProperty("admin_father");
        } else {
            resultToken = token;
        }

        data.request = given().header("Authorization", "Bearer " + resultToken).header("Content-Type", "application/json").
                body("{\"text\":" + text + "}");
    }













}
