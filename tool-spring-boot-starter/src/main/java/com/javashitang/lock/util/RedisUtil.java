package com.javashitang.lock.util;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.params.SetParams;

/**
 * @Author lilimin
 * @Date 2024/2/18
 */
public class RedisUtil {

    private static JedisPool jedisPool;
    private static final String OK = "OK";
    private static final Long LONG_ONE = 1L;
    private static final String script = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";

    static {
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxIdle(3);
        config.setMinIdle(2);
        jedisPool = new JedisPool(config, "127.0.0.1", 6379);
    }

    public static boolean tryLock(String key, String value, long expire) {
        Jedis jedis = jedisPool.getResource();
        SetParams setParams = new SetParams();
        setParams.nx().px(expire);
        return OK.equals(jedis.set(key, value, setParams));
    }

    public static boolean releaseLock(String key, String value) {
        Jedis jedis = jedisPool.getResource();
        return LONG_ONE.equals(jedis.eval(script, 1, key, value));
    }
}
