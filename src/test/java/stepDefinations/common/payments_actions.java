package stepDefinations.common;

import Properties.ReusableMethods;
import Properties.SQL;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.When;
import stepDefinations.StepData;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;

public class payments_actions extends ReusableMethods {

    private int resultId;

    private StepData data;

    public payments_actions(StepData data) {
        this.data = data;
    }

    SQL sql = new SQL();

    private String resultToken;

    @Given("^sending /passenger/request using (.+)$")
    public void sending_passengerrequest_using(String token) throws Throwable {
        if (token.equals("\"true\"")) {
            resultToken = properties.getProperty("first_passenger");
        } else {
            resultToken = token;
        }

        data.request = given().header("Authorization", "Bearer " + resultToken).header("Content-Type", "application/json");
    }

    @And("^Response /passenger/wallet contains (.+) and (.+)$")
    public void response_passengerwallet_contains_and(String key, String value) throws Throwable {
        int accountBalance = sql.getIntData("SELECT * FROM wallets WHERE user_id = 2", "total_amount");
        if (value.equals("\"fromDB\"")) {
            data.json = data.response.then().body(key, equalTo(accountBalance));
        } else {
            data.json = data.response.then().body(key, equalTo(value));
        }
    }

    @Given("^sending /passenger/payment.withdraw using (.+), (.+)$")
    public void sending_passengerpaymentwithdraw_using_(String token, int amount) throws Throwable {
        if (token.equals("\"true\"")) {
            resultToken = properties.getProperty("first_passenger");
        } else {
            resultToken = token;
        }

        data.request = given().header("Authorization", "Bearer " + resultToken).header("Content-Type", "application/json").
                body("{\"amount\": " + amount + "}");
    }

    @Given("^sending /driver/wallet using (.+)$")
    public void sending_driverwallet_using(String token) throws Throwable {
        if (token.equals("\"true\"")) {
            resultToken = properties.getProperty("first_driver");
        } else {
            resultToken = token;
        }

        data.request = given().header("Authorization", "Bearer " + resultToken).header("Content-Type", "application/json");
    }

    @And("^Response /driver/wallet contains (.+) and (.+)$")
    public void response_driverwallet_contains_and(String key, String value) throws Throwable {
        int accountBalance = sql.getIntData("SELECT * FROM wallets WHERE user_id = 1", "total_amount");
        if (value.equals("\"fromDB\"")) {
            data.json = data.response.then().body(key, equalTo(accountBalance));
        } else {
            data.json = data.response.then().body(key, equalTo(value));
        }
    }

    @And("^Payments and notifications are deleted from DB$")
    public void payments_and_notifications_are_deleted_from_db() throws Throwable {
        sql.setData("DELETE FROM notifications WHERE creator_id = 2 AND type = 17");
        sql.setData("DELETE FROM payments WHERE user_id = 2 AND amount = 45000");
    }

    @Given("^sending /passenger/bank.add using (.+), (.+), (.+), (.+)$")
    public void sending_passengerbankadd_using_(String token, String bankname, String bankaccount, String accountname) throws Throwable {
        if (token.equals("\"true\"")) {
            resultToken = properties.getProperty("first_passenger");
        } else {
            resultToken = token;
        }

            data.request = given().header("Authorization", "Bearer " + resultToken).header("Content-Type", "application/json").
                    body("{\"bank_name\": " + bankname + "," +
                            "\"bank_account\": " + bankaccount + "," +
                            "\"account_name\": " + accountname + "}");

    }

    @And("^Response of /passenger/bank.add contains (.+) and (.+)$")
    public void response_of_passengerbankadd_contains_and(String key, String value) throws Throwable {
        if (value.equals("\"fromDB\"")) {
            data.json = data.response.then().body(key, equalTo(sql.getIntData("SELECT * FROM banks WHERE user_id = 2", "id")));
            data.js = rawToJson(data.json);
            data.passengerBankId = data.js.get("id");
        } else {
            data.json = data.response.then().body(key, equalTo(value));
        }
    }

    @Given("^sending /passenger/bank.delete request using (.+)$")
    public void sending_passengerbankdelete_request_using(String token) throws Throwable {
        if (token.equals("\"true\"")) {
            resultToken = properties.getProperty("first_passenger");
        } else {
            resultToken = token;
        }

        data.request = given().header("Authorization", "Bearer " + resultToken).header("Content-Type", "application/json");
    }

    @When("^DELETE /passenger/bank.delete request with (.+) and (.+) is sent$")
    public void delete_passengerbankdelete_request_with_and_is_sent(String resource, String bankid) throws Throwable {
        if (bankid.equals("\"bank_id\"")) {
            resultId = data.passengerBankId;
        } else {
            resultId = Integer.parseInt(bankid);
        }
        data.response = data.request.when().delete(resource + resultId);
        System.out.println(data.response.prettyPrint());
        data.r = rawToString(data.response);
    }

    @Given("^sending /driver/bank.add using (.+), (.+), (.+), (.+)$")
    public void sending_driverbankadd_using_(String token, String bankname, String bankaccount, String accountname) throws Throwable {
        if (token.equals("\"true\"")) {
            resultToken = getTempProperty("driverAuthorizationToken", "src/main/java/Properties/token.properties");
        } else {
            resultToken = token;
        }

        data.request = given().header("Authorization", "Bearer " + resultToken).header("Content-Type", "application/json").
                body("{\"bank_name\": " + bankname + "," +
                        "\"bank_account\": " + bankaccount + "," +
                        "\"account_name\": " + accountname + "}");
    }

    @And("^Response of /driver/bank.add contains (.+) and (.+)$")
    public void response_of_driverbankadd_contains_and(String key, String value) throws Throwable {
        if (value.equals("\"fromDB\"")) {
            data.json = data.response.then().body(key, equalTo(sql.getIntData("SELECT * FROM banks WHERE user_id = " + data.driverID + "", "id")));
            data.js = rawToJson(data.json);
            data.driverBankId = data.js.get("bank_id");
        } else {
            data.json = data.response.then().body(key, equalTo(value));
        }
    }

    @Given("^sending /driver/bank.delete request using (.+)$")
    public void sending_driverbankdelete_request_using(String token) throws Throwable {
        if (token.equals("\"true\"")) {
            resultToken = getTempProperty("driverAuthorizationToken", "src/main/java/Properties/token.properties");
        } else {
            resultToken = token;
        }

        data.request = given().header("Authorization", "Bearer " + resultToken).header("Content-Type", "application/json");
    }

    @When("^DELETE /driver/bank.delete request with (.+) and (.+) is sent$")
    public void delete_driverbankdelete_request_with_and_is_sent(String resource, String bankid) throws Throwable {
        if (bankid.equals("\"bank_id\"")) {
            resultId = data.driverBankId;
        } else {
            resultId = Integer.parseInt(bankid);
        }
        data.response = data.request.when().delete(resource + resultId);
        System.out.println(data.response.prettyPrint());
        data.r = rawToString(data.response);
    }


















}
