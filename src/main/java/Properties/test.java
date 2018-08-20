package Properties;

import sun.applet.Main;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class test extends ReusableMethods {
    public static void main(String[] args) throws IOException, ClassNotFoundException, SQLException {
        SQL sql = new SQL();
        Redis redis = new Redis();
        System.out.println(someDate("yyyy-MM-dd HH:mm:ss", 0));

//       // int a = Integer.parseInt(sql.getTime("SELECT * FROM order_statistic WHERE order_id = 53"));
//      //  sql.getString("SELECT * FROM order_statistic WHERE order_id = 53", "trip_time");
//
//        char[] arr = sql.getString("SELECT * FROM order_statistic WHERE order_id = 53", "trip_time").toCharArray();
//
//        String a = String.valueOf(arr[1]);
//
//        int b = Integer.parseInt(a);
//
//
//
//
//
//
//        System.out.println(b);




    }
    }

