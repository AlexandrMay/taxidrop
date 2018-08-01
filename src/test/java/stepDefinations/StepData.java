package stepDefinations;



import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;

public class StepData {

    public Response response;
    public RequestSpecification request;
    public ValidatableResponse json;
    public String r;
    public JsonPath js;
    public static int adminId;
    public static String adminAuthToken;
    public static int passengerId;
    public static String photo = "\"base64\"";
}
