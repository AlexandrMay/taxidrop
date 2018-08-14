package Properties;

import sun.applet.Main;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class test extends ReusableMethods {
    public static void main(String[] args) throws IOException, ClassNotFoundException, SQLException {
        SQL sql = new SQL();
    ///    sql.getIntData("SELECT * FROM roles WHERE name = 'test_role'", "id");
      //  sql.setData("UPDATE notifications SET status = 0 WHERE notifications.id = 1");
sql.getIntArrayData("SELECT * FROM notifications WHERE user_id = 2", "id");
//        Redis redis = new Redis();
//        redis.getInfo();




//
//        Redis redis = new Redis();
//
//        List<String> list = redis.jedis.lrange("AccessToken", 0 ,5);
//
//        for(int i = 0; i<list.size(); i++) {
//            System.out.println("Stored string in redis:: "+list.get(i));
//        }



    }
}
