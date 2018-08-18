package stepDefinations;

import Properties.SQL;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;

import java.io.IOException;
import java.sql.SQLException;

public class StepData extends SQL {

    public Response response;
    public RequestSpecification request;
    public ValidatableResponse json;
    public String r;
    public JsonPath js;

    public int passengerId;
    public int driverID;

    {
        try {
            driverID = Integer.parseInt(getTempProperty("driverID", "src/main/java/Properties/IDs.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            passengerId = Integer.parseInt(getTempProperty("passengerID", "src/main/java/Properties/IDs.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static int adminId;
    public static String driversRefferralCode;
    public static int roleId;
    public static int passengerCarId;
    public static int passengerAddressId;
    public static int passengerRouteId;
    public static int passengerBankId;
    public static int driverCarId;
    public static int driverBankId;



    public static String driverRegistrationToken;
    public static String passengerRegistrationToken;

    public int admins() throws SQLException {return getCountData("SELECT COUNT(*) FROM administrators");}
    public int drivers() throws SQLException {return getCountData("SELECT COUNT(*) FROM users WHERE type = 0");}
    public int roles() throws SQLException {return getCountData("SELECT COUNT(*) FROM roles");}
    public int notifications() throws SQLException {return getCountData("SELECT COUNT(*) FROM notifications");}
    public int driverApplications() throws SQLException {return getCountData("SELECT COUNT(*) FROM users WHERE type = 0 AND status = 0 OR status = 1");}
    public int passengers() throws SQLException {return getCountData("SELECT COUNT(*) FROM users WHERE type = 1 OR type = 2");}
    public int ownerApplications() throws SQLException {return getCountData("SELECT COUNT(*) FROM users WHERE type = 1 AND status = 0 OR status = 1");}
    public int roleId() throws SQLException {return getIntData("SELECT * FROM roles WHERE name = 'AutoRole'", "id");}
    public int carId(int ownerId) throws SQLException {return getIntData("SELECT * FROM cars WHERE owner_id = " + ownerId + "" , "id");}
    public int addressId() throws SQLException {return getIntData("SELECT * FROM addresses WHERE name = 'Bot’s address'", "id");}



}
