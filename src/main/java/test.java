import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;

public class test {

    public static void main(String[] args) {
        Response request = given().header("Authorization", "Key 24b17e4a2a1374481382aa7edd0df87b").header("Content-Type", "application/json").body("{\"first_name\": \"Auto\"}")
                .when().post("https://api-dev.kross.taxi/api/v1/driver/registration");
        System.out.println(request.prettyPrint());
        ValidatableResponse resp = request.then().assertThat().statusCode(200);
    }

}
