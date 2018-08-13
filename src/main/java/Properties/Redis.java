package Properties;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.exceptions.JedisException;


public class Redis {

    //address of your redis server
    private static final String redisHost = "10.0.0.3";
    private static final Integer redisPort = 6379;

    //the jedis connection pool..
    private static JedisPool pool = null;

    public Redis() {
        //configure our pool connection
        pool = new JedisPool(redisHost, redisPort);

    }

    public void getInfo() {

        //get a jedis connection jedis connection pool
        Jedis jedis = pool.getResource();
        try {
            //save to redis
            System.out.println(jedis.get("CarCurrentLocation"));

            //after saving the data, lets retrieve them to be sure that it has really added in redis
//            Set members = jedis.getSet("CarCurrentLocation");
//            for (String member : members) {
//                System.out.println(member);
//            }
        } catch (JedisException e) {
            //if something wrong happen, return it back to the pool
            if (null != jedis) {
                pool.returnBrokenResource(jedis);
                jedis = null;
            }
        } finally {
            ///it's important to return the Jedis instance to the pool once you've finished using it
            if (null != jedis)
                pool.returnResource(jedis);
        }
    }

}
