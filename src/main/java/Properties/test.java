package Properties;

import sun.applet.Main;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class test extends ReusableMethods {
    public static void main(String[] args) throws IOException, ClassNotFoundException, SQLException {
        SQL sql = new SQL();
        ArrayList<Integer> list = new ArrayList<Integer>();
    ///    sql.getIntData("SELECT * FROM roles WHERE name = 'test_role'", "id");
      //  sql.setData("UPDATE notifications SET status = 0 WHERE notifications.id = 1");
        int counter = sql.getCountData("SELECT COUNT(*) FROM notifications WHERE type = 15 AND creator_id = 1");
        for (int i = 0; i < counter; i++) {
            list.add(i, 2);
            System.out.println(list.get(i));
        }
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
