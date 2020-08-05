package com.fh.util;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

//redis的连接池
public class RedisUtils {
    //从静态块中初始化
    private static JedisPool jedisPool;

    private RedisUtils(){

    }

    //static  静态块
    static {
        //创建redis池的配置
        JedisPoolConfig jedisPoolConfig=new JedisPoolConfig();
        jedisPoolConfig.setMaxTotal(1000);
        jedisPoolConfig.setMaxIdle(100);
        jedisPoolConfig.setMinIdle(50);
        jedisPoolConfig.setMaxWaitMillis(30000);
        //初始化redis池
        jedisPool=new JedisPool(jedisPoolConfig,"192.168.220.133",6379,30000);
    }
    //从池中拿连接
    public static Jedis getJedis(){
        return jedisPool.getResource();
    };
    //连接用还给池中
    public static void returnJedis(Jedis jedis){
        jedisPool.returnResource(jedis);
    }

    public static void main(String[] args) {
        Jedis jedis = RedisUtils.getJedis();
        System.out.println(jedis.ping());
        RedisUtils.returnJedis(jedis);
    }


}
