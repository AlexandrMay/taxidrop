package Properties;

import redis.clients.jedis.Jedis;

public class Redis {

    Jedis jedis = new Jedis("10.0.0.3", 6379);

    public Jedis getJedis() {
        return jedis;
    }

}
