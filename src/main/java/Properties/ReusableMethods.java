package Properties;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import org.apache.commons.codec.digest.DigestUtils;

import java.util.Properties;


public abstract class ReusableMethods {


    public static Properties properties = new Properties();


//Шифрование пароля в MD5

    public static String convert(String pass) {
        String md5Hex = DigestUtils.md5Hex(pass);
        String answer = "\"" + md5Hex + "\"";
        return answer;
    }

    // Форматирование полученного ответа в json

    public static JsonPath rawToJson(ValidatableResponse r) {
        String resp = r.extract().asString();
        JsonPath x = new JsonPath(resp);
        return x;
    }

    // Форматирование полученного ответа в String

    public static String rawToString(Response response) {
        String resp = response.asString();
        return resp;
    }

}
