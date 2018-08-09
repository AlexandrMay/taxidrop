package stepDefinations;

import Properties.SQL;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;

import java.sql.SQLException;

public class StepData extends SQL {

    public Response response;
    public RequestSpecification request;
    public ValidatableResponse json;
    public String r;
    public JsonPath js;
    public static int adminId;
    public static String adminAuthToken;
    public static int passengerId;
    public static int driverID;
    public static String driversRefferralCode;
    public static int roleId;

    public static String driverRegistrationToken;
    public static String driverAuthorizationToken;

    public static String passengerRegistrationToken;

    public static int drivers() throws SQLException {return getCountData("SELECT COUNT(*) FROM users WHERE type = 0");}
    public static int roles() throws SQLException {return getCountData("SELECT COUNT(*) FROM roles");}
    public static int driverApplications() throws SQLException {return getCountData("SELECT COUNT(*) FROM users WHERE type = 0 AND status = 0 OR status = 1");}
    public static int passengers() throws SQLException {return getCountData("SELECT COUNT(*) FROM users WHERE type = 1 OR type = 2");}
    public static int ownerApplications() throws SQLException {return getCountData("SELECT COUNT(*) FROM users WHERE type = 1 AND status = 0 OR status = 1");}

    public static int roleId() throws SQLException {return getIntData("SELECT * FROM roles WHERE name = 'AutoRole'", "id");}

}
