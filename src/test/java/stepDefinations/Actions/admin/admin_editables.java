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

public class admin_editables extends ReusableMethods {

    SQL sql = new SQL();

    private ArrayList<Integer> list = new ArrayList<Integer>();

    private StepData data;

    public admin_editables(StepData data) {
        this.data = data;
    }

    private String resultToken;

    private int resultId;


    @Given("^sending /admin/news request using (.+)$")
    public void sending_adminnews_request_using(String token) throws Throwable {
        if (token.equals("\"true\"")) {
            resultToken = properties.getProperty("admin_father");
        } else {
            resultToken = token;
        }

        data.request = given().header("Authorization", "Bearer " + resultToken).header("Content-Type", "application/json");
    }

    @And("^Response of /admin/news.list contains (.+) and (.+)$")
    public void response_of_adminnewslist_contains_and(String key, String value) throws Throwable {
        if (value.equals("\"fromDB_status_0\"")) {
            list = sql.getIntArrayData("SELECT * FROM news WHERE status = 0", "id");
            data.json = data.response.then().body(key, equalTo(list));
        } else if (value.equals("\"fromDB_status_1\"")) {
            list = sql.getIntArrayData("SELECT * FROM news WHERE status = 1", "id");
            data.json = data.response.then().body(key, equalTo(list));
        } else if (value.equals("\"fromDB\"")) {
            list = sql.getIntArrayData("SELECT * FROM news WHERE status = 1 OR status = 0", "id");
            data.json = data.response.then().body(key, equalTo(list));
        } else {
            data.json = data.response.then().body(key, equalTo(value));
        }
    }

    @Given("^sending /admin/news.add request using (.+), (.+), (.+), (.+), (.+)$")
    public void sending_adminnewsadd_request_using_(String token, String title, String text, String image, String publisheddate) throws Throwable {
        String resultDate;
        if (token.equals("\"true\"")) {
            resultToken = properties.getProperty("admin_father");
        } else {
            resultToken = token;
        }

        if (publisheddate.equals("\"tomorrow_date\"")) {
            resultDate = "\"" + someDate("yyyy-MM-dd HH:mm:ss", 1) + "\"";
        } else if (publisheddate.equals("\"current_date\"")) {
            resultDate = "\"" + someDate("yyyy-MM-dd HH:mm:ss", 0) + "\"";
        } else if (publisheddate.equals("\"yesterday_date\"")) {
            resultDate = "\"" + someDate("yyyy-MM-dd HH:mm:ss", -1) + "\"";
        } else {
            resultDate = publisheddate;
        }

        data.request = given().header("Authorization", "Bearer " + resultToken).header("Content-Type", "application/json").
                body("{\"title\": " + title + "," +
                        "\"text\": " + text + "," +
                        "\"image\": " + image + "," +
                         "\"published_date\": " + resultDate + "}");
    }

    @And("^Response /admin/news.add contains (.+) and (.+)$")
    public void response_adminnewsadd_contains_and(String key, String value) throws Throwable {
        list = sql.getIntArrayData("SELECT * FROM news WHERE title = 'auto title'", "id");
        if (value.equals("\"article_id_1\"")) {
            data.json = data.response.then().body(key, equalTo(list.get(0)));
            data.js = rawToJson(data.json);
            data.adminNewsId = data.js.get("article_id");
        } else if (value.equals("\"article_id_2\"")){
            data.json = data.response.then().body(key, equalTo(list.get(1)));
        } else {
            data.json = data.response.then().body(key, equalTo(value));
        }
    }

    @When("^PUT /admin/news.edit request with (.+) and (.+) is sent$")
    public void put_adminnewsedit_request_with_and_is_sent(String resource, String newsid) throws Throwable {
        if (newsid.equals("\"newsId\"")) {
            resultId = data.adminNewsId;
        } else {
            resultId = Integer.parseInt(newsid);
        }
        data.response = data.request.when().put(resource + resultId);
        System.out.println(data.response.prettyPrint());
        data.r = rawToString(data.response);
    }

    @Given("^sending /admin/news.status request using (.+), (.+)$")
    public void sending_adminnewsstatus_request_using_(String token, int status) throws Throwable {
        String resultStatus;
        if (status == -1) {
            resultStatus = null;
        } else {
            resultStatus = String.valueOf(status);
        }
        if (token.equals("\"true\"")) {
            resultToken = properties.getProperty("admin_father");
        } else {
            resultToken = token;
        }
        data.request = given().header("Authorization", "Bearer " + resultToken).header("Content-Type", "application/json").
                body("{\"status\": " + resultStatus + "}");
    }

    @When("^DELETE /admin/news.delete request with (.+) and (.+) is sent$")
    public void delete_adminnewsdelete_request_with_and_is_sent(String resource, String newsid) throws Throwable {
        if (newsid.equals("\"newsId_1\"")) {
            resultId = data.adminNewsId;
        } else if (newsid.equals("\"newsId_2\"")){
            resultId = data.adminNewsId + 1;
        } else {
            Integer.parseInt(newsid);
        }
        data.response = data.request.when().delete(resource + resultId);
        System.out.println(data.response.prettyPrint());
        data.r = rawToString(data.response);
    }


    @And("^Response of /admin/help.list contains (.+) and (.+)$")
    public void response_of_adminhelplist_contains_and(String key, String value) throws Throwable {
        if (value.equals("\"type0\"")) {
            list = sql.getIntArrayData("SELECT * FROM helps WHERE type = 0", "id");
            data.json = data.response.then().body(key, equalTo(list));
        } else if (value.equals("\"type1\"")) {
            list = sql.getIntArrayData("SELECT * FROM helps WHERE type = 1", "id");
            data.json = data.response.then().body(key, equalTo(list));
        } else if (value.equals("\"type2\"")) {
            list = sql.getIntArrayData("SELECT * FROM helps WHERE type = 2", "id");
            data.json = data.response.then().body(key, equalTo(list));
        } else {
            data.json = data.response.then().body(key, equalTo(value));
        }
    }

    @Given("^sending /admin/help.add request using (.+), (.+), (.+), (.+), (.+)$")
    public void sending_adminhelpadd_request_using_(String token, String title, int type, String appeal1, String appeal2) throws Throwable {
        String resultType;
        ArrayList<String> list2 = new ArrayList<String>();
        if (appeal1.equals("\"empty\"") & appeal2.equals("\"empty\"")) {
            list2.isEmpty();
        } else {
            list2.add(0, appeal1);
            list2.add(1, appeal2);
        }

        if (type == -1){
            resultType = null;
        } else {
            resultType = String.valueOf(type);
        }

        if (token.equals("\"true\"")) {
            resultToken = properties.getProperty("admin_father");
        } else {
            resultToken = token;
        }
        data.request = given().header("Authorization", "Bearer " + resultToken).header("Content-Type", "application/json").
                body("{\"title\": " + title + "," +
                        "\"type\": " + resultType + "," +
                        "\"appeals\": " + list2 + "}");
    }

    @And("^Response of /admin/help.add contains (.+) and (.+)$")
    public void response_of_adminhelpadd_contains_and(String key, String value) throws Throwable {
        if (value.equals("\"section_id\"")) {
            resultId = sql.getIntData("SELECT * FROM helps WHERE title = 'auto title'", "id");
            data.json = data.response.then().body(key, equalTo(resultId));
            data.js = rawToJson(data.json);
            data.adminHelpId = data.js.get("section_id");
        } else {
            data.json = data.response.then().body(key, equalTo(value));
        }
    }

    @When("^GET /admin/help.info request with (.+) and (.+) is sent$")
    public void get_adminhelpinfo_request_with_and_is_sent(String resource, String helpid) throws Throwable {
        if (helpid.equals("\"helpId\"")){
            resultId = data.adminHelpId;
        } else {
            Integer.parseInt(helpid);
        }
        data.response = data.request.when().get(resource + resultId);
        System.out.println(data.response.prettyPrint());
        data.r = rawToString(data.response);
    }

    @And("^Response /admin/help.info/ contains (.+) and (.+)$")
    public void response_adminhelpinfo_contains_and(String key, String value) throws Throwable {
        if (value.equals("\"helpId\"")) {
            data.json = data.response.then().body(key, equalTo(data.adminHelpId));
        } else {
            data.json = data.response.then().body(key, equalTo(value));
        }
    }

    @When("^PUT /admin/help.edit request with (.+) and (.+) is sent$")
    public void put_adminhelpedit_request_with_and_is_sent(String resource, String helpid) throws Throwable {
        if (helpid.equals("\"helpId\"")){
            resultId = data.adminHelpId;
        } else {
            Integer.parseInt(helpid);
        }
        data.response = data.request.when().put(resource + resultId);
        System.out.println(data.response.prettyPrint());
        data.r = rawToString(data.response);
    }

    @When("^DELETE /admin/help.delete request with (.+) and (.+) is sent$")
    public void delete_adminhelpdelete_request_with_and_is_sent(String resource, String helpid) throws Throwable {
        if (helpid.equals("\"helpId\"")){
            resultId = data.adminHelpId;
        } else {
            Integer.parseInt(helpid);
        }
        data.response = data.request.when().delete(resource + resultId);
        System.out.println(data.response.prettyPrint());
        data.r = rawToString(data.response);
    }

    @And("^Response /admin/about contains (.+) and (.+)$")
    public void response_adminabout_contains_and(String key, String value) throws Throwable {
        if (value.equals("\"fromDB\"")) {
            data.json = data.response.then().body(key, equalTo(sql.getIntData("SELECT * FROM news WHERE status = 2", "admin_id")));
        } else {
            data.json = data.response.then().body(key, equalTo(value));
        }
    }

    @Given("^sending /admin/about.edit request using (.+), (.+)$")
    public void sending_adminaboutedit_request_using_(String token, String text) throws Throwable {
        if (token.equals("\"true\"")) {
            resultToken = properties.getProperty("admin_father");
        } else {
            resultToken = token;
        }
        data.request = given().header("Authorization", "Bearer " + resultToken).header("Content-Type", "application/json").
                body("{\"text\": " + text + "}");
    }


}
