package redis;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class RedisApi {
    
    private static JedisPool pool;
    
    private static Properties prop = null;
    
    static {
        InputStream in = RedisApi.class.getClassLoader()
                .getResourceAsStream("redis/redis.properties");
        
        prop = new Properties();
        try {
            prop.load(in);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        
        JedisPoolConfig config = new JedisPoolConfig();
        // 控制一个pool可分配多少个jedis实例，通过pool.getResource()来获取；  
        // 如果赋值为-1，则表示不限制；如果pool已经分配了maxActive个jedis实例，则此时pool的状态为exhausted(耗尽)。  
        config.setMaxTotal(Integer.valueOf(prop.getProperty("MAX_TOTAL")));
        //控制一个pool最多有多少个状态为idle(空闲的)的jedis实例。  
        config.setMaxIdle(Integer.valueOf(prop.getProperty("MAX_IDLE")));
        
        // 表示当borrow(引入)一个jedis实例时，最大的等待时间，如果超过等待时间，则直接抛出JedisConnectionException；  
        config.setMaxWaitMillis(Integer.valueOf(prop.getProperty("MAX_WAIT_MILLIS")));
        
        //在borrow一个jedis实例时，是否提前进行validate操作；如果为true，则得到的jedis实例均是可用的；  
        config.setTestOnBorrow(Boolean.valueOf(prop.getProperty("TEST_ON_BORROW")));
        //在进行returnObject对返回的connection进行validateObject校验
        config.setTestOnReturn(Boolean.valueOf(prop.getProperty("TEST_ON_RETURN")));
        //定时对线程池中空闲的链接进行validateObject校验
        config.setTestWhileIdle(Boolean.valueOf(prop.getProperty("TEST_WHILE_IDLE")));
        pool = new JedisPool(config, prop.getProperty("REDIS_IP"),
                Integer.valueOf(prop.getProperty("REDIS_PORT")), 100000);
    }
    
    /**
     * 构建redis连接池
     */
    public static JedisPool getPool() {
        
        if (pool == null) {
            
            JedisPoolConfig config = new JedisPoolConfig();
            // 控制一个pool可分配多少个jedis实例，通过pool.getResource()来获取；  
            // 如果赋值为-1，则表示不限制；如果pool已经分配了maxActive个jedis实例，则此时pool的状态为exhausted(耗尽)。  
            config.setMaxTotal(Integer.valueOf(prop.getProperty("MAX_TOTAL")));
            //控制一个pool最多有多少个状态为idle(空闲的)的jedis实例。  
            config.setMaxIdle(Integer.valueOf(prop.getProperty("MAX_IDLE")));
            
            // 表示当borrow(引入)一个jedis实例时，最大的等待时间，如果超过等待时间，则直接抛出JedisConnectionException；  
            config.setMaxWaitMillis(Integer.valueOf(prop.getProperty("MAX_WAIT_MILLIS")));
            
            //在borrow一个jedis实例时，是否提前进行validate操作；如果为true，则得到的jedis实例均是可用的；  
            config.setTestOnBorrow(Boolean.valueOf(prop.getProperty("TEST_ON_BORROW")));
            //在进行returnObject对返回的connection进行validateObject校验
            config.setTestOnReturn(Boolean.valueOf(prop.getProperty("TEST_ON_RETURN")));
            //定时对线程池中空闲的链接进行validateObject校验
            config.setTestWhileIdle(Boolean.valueOf(prop.getProperty("TEST_WHILE_IDLE")));
            pool = new JedisPool(config, prop.getProperty("REDIS_IP"),
                    Integer.valueOf(prop.getProperty("REDIS_PORT")));
        }
        
        return pool;
    }
    
    public static void returnResource(JedisPool pool, Jedis redis) {
        if (redis != null) {
            pool.returnResource(redis);
        }
    }
    
    /** 
     * 获取数据 
     *  
     * @param key 
     * @return 
     */
    public static String get(String key) {
        Jedis jedis = null;
        String value = null;
        try {
            jedis = pool.getResource();
            value = jedis.get(key);
        }
        catch (Exception e) {
            
        }
        finally {
            returnResource(pool, jedis);
        }
        return value;
    }
    
    /**
     * set数据
     */
    public static String set(String key, String value) {
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            return jedis.set(key, value);
        }
        catch (Exception e) {
            
            return "0";
        }
        finally {
            returnResource(pool, jedis);
        }
    }
    
    public static Long lpush(String key, String[] strings) {
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            return jedis.lpush(key, strings);
        }
        catch (Exception e) {
            
            return 0L;
        }
        finally {
            returnResource(pool, jedis);
        }
    }
    
    public static List<String> lrange(String key) {
        Jedis jedis = null;
        String value = null;
        try {
            jedis = pool.getResource();
            return jedis.lrange(key, 0, -1);
        }
        catch (Exception e) {
            return null;
        }
        finally {
            returnResource(pool, jedis);
        }
    }
    
    public static String hmset(String key, Map map) {
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            return jedis.hmset(key, map);
        }
        catch (Exception e) {
            
            return "0";
        }
        finally {
            returnResource(pool, jedis);
        }
    }
    
    public static List<String> hmget(String key, String... strings) {
        Jedis jedis = null;
        String value = null;
        try {
            jedis = pool.getResource();
            return jedis.hmget(key, strings);
        }
        catch (Exception e) {
            
        }
        finally {
            returnResource(pool, jedis);
        }
        return null;
    }
    
    public static Set<String> hkeys(String key) {
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            return jedis.hkeys(key);
        }
        catch (Exception e) {
            
        }
        finally {
            returnResource(pool, jedis);
        }
        return null;
    }
    
    public static List<String> hvals(String key) {
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            return jedis.hvals(key);
        }
        catch (Exception e) {
            
        }
        finally {
            returnResource(pool, jedis);
        }
        return null;
    }
    
    public static Long hdel(String key, String key1) {
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            return jedis.hdel(key, key1);
        }
        catch (Exception e) {
            
        }
        finally {
            returnResource(pool, jedis);
        }
        return null;
    }
    
    public static boolean exist(String key) {
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            return jedis.exists(key);
        }
        catch (Exception e) {
            
        }
        finally {
            returnResource(pool, jedis);
        }
        return false;
    }
    
    public static Long del(String key) {
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            jedis.del(key);
        }
        catch (Exception e) {
            
        }
        finally {
            returnResource(pool, jedis);
        }
        return null;
    }
}
