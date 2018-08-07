package Properties;

import sun.applet.Main;

import java.io.IOException;

public class test extends ReusableMethods {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        SQL sql = new SQL();
        sql.passengersCount();
    }
}
