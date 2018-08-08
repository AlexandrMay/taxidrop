package Properties;

import sun.applet.Main;

import java.io.IOException;
import java.sql.SQLException;

public class test extends ReusableMethods {
    public static void main(String[] args) throws IOException, ClassNotFoundException, SQLException {
        SQL sql = new SQL();
        sql.getData();
    }
}
