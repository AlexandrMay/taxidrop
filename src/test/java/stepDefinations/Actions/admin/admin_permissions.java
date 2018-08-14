package stepDefinations.Actions.admin;

import Properties.ReusableMethods;
import cucumber.api.DataTable;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.When;
import stepDefinations.StepData;

import java.util.ArrayList;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;

public class admin_permissions extends ReusableMethods {

    private String resultToken;
    private ArrayList<String> list = new ArrayList<String>();

    private StepData data;

    public admin_permissions(StepData data) {
        this.data = data;
    }


    @Given("^sending /admin/role request with data$")
    public void sending_adminrole_request(DataTable table) throws Throwable {
        data.request = given().header("Authorization", "Bearer " + properties.getProperty("admin_father")).header("Content-Type", "application/json").
                body("{\"name\":" + table.raw().get(0).get(0) + "," +
                        "\"permissions\": [" + table.raw().get(0).get(1) + "," + table.raw().get(0).get(2) + "]}");
    }

    @When("^POST request \"([^\"]*)\" is sent$")
    public void post_request_something_is_sent(String strArg1) throws Throwable {
        data.response = data.request.when().post(strArg1);
        System.out.println(data.response.prettyPrint());
        data.r = rawToString(data.response);
    }

    @And("^response equals id of role that equals to id in DB$")
    public void response_equals_id_of_role_that_equals_to_id_in_db() throws Throwable {
        data.json = data.response.then().body("\"role_id\"", equalTo(data.roleId()));//equalTo(26));
        data.js = rawToJson(data.json);
        data.roleId = data.js.get("role_id");
        System.out.println("ROLE: " + data.roleId);
    }

    @Given("^sending /admin/role.add request using (.+), (.+), (.+), (.+)$")
    public void sending_adminroleadd_request_using_(String token, String name, String permission1, String permission2) throws Throwable {
        if (permission1.equals("\"empty\"")) {
            list.isEmpty();
        } else {
            list.add(0, permission1);
            list.add(1, permission2);
        }


        if (token.equals("\"true\"")) {
            resultToken = properties.getProperty("admin_father");
        } else {
            resultToken = token;
        }
        data.request = given().header("Authorization", "Bearer " + resultToken).header("Content-Type", "application/json").
                body("{\"name\":" + name + "," +
                        "\"permissions\": " + list + "}");
    }

    @Given("^sending /admin/role.edit request using (.+), (.+), (.+), (.+)$")
    public void sending_adminroleedit_request_using_(String token, String name, String permission1, String permission2) throws Throwable {
        if (permission1.equals("\"empty\"")) {
            list.isEmpty();
        } else {
            list.add(0, permission1);
            list.add(1, permission2);
        }
        if (token.equals("\"true\"")) {
            resultToken = properties.getProperty("admin_father");
        } else {
            resultToken = token;
        }
        data.request = given().header("Authorization", "Bearer " + resultToken).header("Content-Type", "application/json").
                body("{\"name\":" + name + "," +
                        "\"permissions\": " + list + "}");
    }

    @When("^PUT request with (.+) and (.+) is sent$")
    public void put_request_with_and_is_sent(String resource, String id) throws Throwable {
        int resultId;
        if (id.equals("\"roleID\"")) {
            resultId = data.roleId;
        } else {
            resultId = Integer.parseInt(id);
        }
        data.response = data.request.when().put(resource + resultId);
        System.out.println(data.response.prettyPrint());
        data.r = rawToString(data.response);
    }

    @Given("^sending /admin/role.get request using (.+)$")
    public void sending_adminroleedit_request_using(String token) throws Throwable {
        if (token.equals("\"true\"")) {
            resultToken = properties.getProperty("admin_father");
        } else {
            resultToken = token;
        }
        data.request = given().header("Authorization", "Bearer " + resultToken).header("Content-Type", "application/json");
    }

    @Given("^sending /admin/roles.list$")
    public void sending_adminroleslist() throws Throwable {
        data.request = given().header("Authorization", "Bearer " + properties.getProperty("admin_father")).header("Content-Type", "application/json");
    }

    @And("^count of roles equals to DB$")
    public void count_of_roles_equals_to_db() throws Throwable {
        data.json = data.response.then().body("\"total\"", equalTo(data.roles()));
    }

    @Given("^sending /admin/roles.list request using (.+)$")
    public void sending_adminroleslist_request_using(String token) throws Throwable {
        if (token.equals("\"true\"")) {
            resultToken = properties.getProperty("admin_father");
        } else {
            resultToken = token;
        }
        data.request = given().header("Authorization", "Bearer " + resultToken).header("Content-Type", "application/json");
    }

    @Given("^sending /admin/role.delete request using (.+)$")
    public void sending_adminroledelete_request_using(String token) throws Throwable {
        if (token.equals("\"true\"")) {
            resultToken = properties.getProperty("admin_father");
        } else {
            resultToken = token;
        }
        data.request = given().header("Authorization", "Bearer " + resultToken).header("Content-Type", "application/json");
    }

    @When("^DELETE request with (.+) and (.+) is sent$")
    public void delete_request_with_and_is_sent(String resource, String id) throws Throwable {
        int resultId;
        if (id.equals("\"roleID\"")) {
            resultId = data.roleId;
        } else {
            resultId = Integer.parseInt(id);
        }
        data.response = data.request.when().delete(resource + resultId);
        System.out.println(data.response.prettyPrint());
        data.r = rawToString(data.response);
    }




}
