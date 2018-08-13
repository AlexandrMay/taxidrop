package stepDefinations.Actions;

import Properties.ReusableMethods;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.When;
import org.apache.commons.lang3.StringUtils;
import stepDefinations.StepData;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;

public class admin_actions extends ReusableMethods {

    private StepData data;

    public admin_actions(StepData data) {
        this.data = data;
    }

    private String resultToken;

    private String resultRoleId;

    @Given("^sending /admin/admins.list request with (.+)$")
    public void sending_adminadminslist_request_with(String token) throws Throwable {
        if (token.equals("\"true\"")) {
            resultToken = properties.getProperty("admin_father");
        } else {
            resultToken = token;
        }

        data.request = given().header("Authorization", "Bearer " + resultToken).header("Content-Type", "application/json");
    }

    @And("^Response /admin/admins.list contains (.+) and (.+)$")
    public void response_adminadminslist_contains_and(String key, String value) throws Throwable {
        String resultValue;
        if (value.equals("\"fromDB\"")) {
           resultValue = String.valueOf(data.admins());
        } else {
            resultValue = value;
        }
        if (StringUtils.isNumeric(resultValue)) {
            data.json = data.response.then().body(key, equalTo(Integer.parseInt(resultValue)));
        } else {
            data.json = data.response.then().body(key, equalTo(resultValue));
        }
    }

    @Given("^sending /admin/admin.edit request with (.+), (.+), (.+), (.+), (.+), (.+), (.+)$")
    public void sending_adminadminedit_request_with_(String token, String photo, String firstname, String lastname, String phonenumber, String email, String roleid) throws Throwable {
        if(roleid.equals("\"roleID\"")) {
            resultRoleId = String.valueOf(data.roleId);
        } else {
            resultRoleId = roleid;
        }
        if (token.equals("\"true\"")) {
            resultToken = properties.getProperty("admin_father");
        } else {
            resultToken = token;
        }
        data.request = given().header("Authorization", "Bearer " + resultToken).header("Content-Type", "application/json")
                .body("{" +
                        "\"photo\":" + photo + "," +
                        "\"first_name\":" + firstname + "," +
                        "\"last_name\":" + lastname + "," +
                        "\"phone_number\":" + phonenumber + "," +
                        "\"email\":" + email + ", " +
                        "\"role_id\":" + resultRoleId + "}");
    }

    @When("^PUT /admin/admin.edit request with (.+) and (.+) is sent$")
    public void put_adminadminedit_request_with_and_is_sent(String resource, String id) throws Throwable {
        int resultId;
        if (id.equals("\"true\"")) {
            resultId = data.adminId;
        } else {
            resultId = Integer.parseInt(id);
        }
        data.response = data.request.when().put(resource + resultId);
        System.out.println(data.response.prettyPrint());
        data.r = rawToString(data.response);
    }

    @Given("^sending different requests using admin_token$")
    public void sending_different_requests_using_admintoken() throws Throwable {
        data.request = given().header("Authorization", "Bearer " + getTempProperty("adminAuthorizationToken", "src/main/java/Properties/token.properties")).header("Content-Type", "application/json");
    }

    @And("^Response of all requests contains (.+) and (.+)$")
    public void response_of_all_requests_contains_and(String key, String value) throws Throwable {
        String resultValue;
        if (value.equals("\"fromDB\"")) {
            resultValue = String.valueOf(data.passengers());
        } else {
            resultValue = value;
        }
        if (StringUtils.isNumeric(resultValue)) {
            data.json = data.response.then().body(key, equalTo(Integer.parseInt(resultValue)));
        } else {
            data.json = data.response.then().body(key, equalTo(resultValue));
        }
    }

}
