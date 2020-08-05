package com.fh.util;

import com.alibaba.fastjson.JSONObject;
import com.fh.model.po.Area;
import redis.clients.jedis.Jedis;

import java.util.List;

public class RedisUse {

    public static void set(String key,String value){
        Jedis jedis = RedisUtils.getJedis();
        jedis.set(key,value);
        RedisUtils.returnJedis(jedis);
    }

    public static void set(String key,String value,int seconds){
        Jedis jedis = RedisUtils.getJedis();
        jedis.setex(key,seconds,value);
        RedisUtils.returnJedis(jedis);
    }

    public static String get(String key){
        Jedis jedis = RedisUtils.getJedis();
        String value=jedis.get(key);
        RedisUtils.returnJedis(jedis);
        return value;
    }

    public static void hset(String key,String filed,String value){
        Jedis jedis = RedisUtils.getJedis();
        Long hset = jedis.hset(key, filed, value);
        RedisUtils.returnJedis(jedis);
    }

    public static long hlen(String key){
        Jedis jedis = RedisUtils.getJedis();
        Long hlen = jedis.hlen(key);
        RedisUtils.returnJedis(jedis);
        return hlen;
    }

    public static String hget(String key , String field){
        Jedis jedis = RedisUtils.getJedis();
        String hget = jedis.hget(key, field);
        RedisUtils.returnJedis(jedis);
        return hget;
    }

    public static long hdel(String key , String field){
        Jedis jedis = RedisUtils.getJedis();
        Long hdel = jedis.hdel(key, field);

        RedisUtils.returnJedis(jedis);
        return hdel;
    }

    public static List<String> hvals(String Key){
        Jedis jedis = RedisUtils.getJedis();
        List<String> hvals = jedis.hvals(Key);
        RedisUtils.returnJedis(jedis);
        return hvals;
    }
    public static boolean exists(String Key){
        Jedis jedis = RedisUtils.getJedis();
        boolean exists = jedis.exists(Key);
        RedisUtils.returnJedis(jedis);
        return exists;
    }

    public static long strlen(String key){
        Jedis jedis = RedisUtils.getJedis();
        Long strlen = jedis.strlen(key);
        RedisUtils.returnJedis(jedis);
        return strlen;
    }
    public static void del(String key){
        Jedis jedis = RedisUtils.getJedis();
        jedis.del(key);
        RedisUtils.returnJedis(jedis);
    }

    public static String areaIds(String areaIds) {
        Jedis jedis = RedisUtils.getJedis();
        String[] split = areaIds.split(",");
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i <split.length ; i++) {
            String s = split[i];
            String area_t = jedis.hget("t_area1", s);
            Area area = JSONObject.parseObject(area_t, Area.class);
            sb.append(area.getName()).append(",");
        }
        return sb.toString();
    }
}
