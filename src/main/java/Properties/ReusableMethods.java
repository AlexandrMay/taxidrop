package Properties;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import org.apache.commons.codec.digest.DigestUtils;


import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;


public abstract class ReusableMethods {


    public String passengerAdminToken() {return convert1(currentDate() + properties.getProperty("admin_passenger_token"));}

    public String driverToken() {return convert1(currentDate() + properties.getProperty("driver_token"));}

    public static void setSomePropertyToFile() throws IOException {
        FileOutputStream fis = new FileOutputStream("src/main/java/Properties/temp.properties");
        props.store(fis, "comment");
    }

    public String getTempProperty(String propertyName) throws IOException {
        FileInputStream fis = new FileInputStream("src/main/java/Properties/temp.properties");
        temp.load(fis);
        return temp.getProperty(propertyName);
    }
    public static Properties props = new Properties();

    public static Properties properties = new Properties();

    public static Properties temp = new Properties();


    public String currentDate() {
        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        return format.format(date);
    }

//Шифрование в MD5

    public static String convert(String smth) {
        String md5Hex = DigestUtils.md5Hex(smth);
        String answer = "\"" + md5Hex + "\"";
        return answer;
    }

    public static String convert1(String smth) {
        String md5Hex = DigestUtils.md5Hex(smth);
        return md5Hex;
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
