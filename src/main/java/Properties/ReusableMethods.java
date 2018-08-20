package Properties;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import org.apache.commons.codec.digest.DigestUtils;


import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;


public abstract class ReusableMethods {


    public String passengerAdminToken() {return convert1(currentDate("dd/MM/yyyy") + properties.getProperty("admin_passenger_token"));}

    public String driverToken() {return convert1(currentDate("dd/MM/yyyy") + properties.getProperty("driver_token"));}

    public static void setSomePropertyToFile(String path) throws IOException {
        FileOutputStream fis = new FileOutputStream(path);
        props.store(fis, "comment");
    }

    public String getTempProperty(String propertyName, String filepath) throws IOException {
        FileInputStream fis = new FileInputStream(filepath);
        temp.load(fis);
        return temp.getProperty(propertyName);
    }

    public static Properties props = new Properties();

    public static Properties properties = new Properties();

    public static Properties temp = new Properties();


    public String currentDate(String pattern) {
        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat(pattern);
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

    public String currentTime(String pattern) {
        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        return format.format(date);
    }

    public static String someDate(String pattern, int amount) {
        Date date = new Date();
        Calendar instance = Calendar.getInstance();
        instance.setTime(date);
        instance.add(Calendar.DAY_OF_MONTH, amount);
        Date newDate = instance.getTime();
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        return format.format(newDate);
    }

}
