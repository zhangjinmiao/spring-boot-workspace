package com.jimzhang.springbootredis.cache;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.*;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * redis公共服务类
 * Created by admin on 2017/08/17.
 */
@Service
public class RedisCacheService {

    // inject the actual template
    @Autowired
    RedisTemplate redisTemplate;

    /**
     * key - value 操作
     */
    @Resource(name = "redisTemplate")
    ValueOperations<Serializable, Object> valueOperations;

    /**
     * hash 操作
     */
    @Resource(name = "redisTemplate")
    HashOperations<String, Object, Object> hashOperations;

    /**
     * list 操作 // inject the template as ListOperations
     */
    @Resource(name = "redisTemplate")
    ListOperations<String , Object> listOperations;

    /**
     * set 操作
     */
    @Resource(name = "redisTemplate")
    SetOperations<String, Object> setOperations;

    /**
     * 有序 set 操作
     */
    @Resource(name = "redisTemplate")
    ZSetOperations<String, Object> zsetOperations;

    /**
     * 普通写入缓存
     * @param key
     * @param value
     * @return
     */
    public boolean set(final String key, Object value) {
        boolean result = false;
        try {
            valueOperations.set(key, value);
            result = true;
        }catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 写入缓存设置时效时间
     * @param key
     * @param value
     * @param expireTime
     * @return
     */
    public boolean set(final String key, Object value, Long expireTime) {
        boolean result = false;
        try {
            valueOperations.set(key, value);
            expire(key, expireTime);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 设置超时时间
     * @param key
     * @param seconds
     */
    public void expire(String key,long seconds){
        redisTemplate.expire(key,seconds,TimeUnit.SECONDS);
    }


    /**
     * redis中添加,如果key已经存在，返回false
     * @param key
     * @param value
     */
    public boolean setnx(String key , Object value){
        return valueOperations.setIfAbsent(key,value);
    }

    /**
     * 读取缓存
     * @param key
     * @return
     */
    public Object get(String key){
        return valueOperations.get(key);
    }

    /**
     * 判断缓存中是否有对应的value
     * @param key
     * @return
     */
    public boolean exists(final String key) {
        return redisTemplate.hasKey(key);
    }

    /**
     * 哈希 添加
     * @param key
     * @param hashKey
     * @param value
     */
    public void hset(String key, Object hashKey, Object value) {
        hashOperations.put(key, hashKey, value);
    }

    /**
     * 哈希 添加 Map
     * @param key
     * @param map
     */
    public void hmSet(String key,Map<String,Object>map){
        hashOperations.putAll(key,map);
    }


    /**
     * 哈希获取数据
     *
     * @param key
     * @param hashKey
     * @return
     */
    public Object hget(String key, Object hashKey) {
        return hashOperations.get(key, hashKey);
    }


    /**
     * 删除hash值
     * @param key
     * @param filed
     */
    public long hdelete(String key, Object... filed){
        return  hashOperations.delete(key,filed);
    }


    /**
     * 删除key 对应的value
     * @param key
     */
    public void remove(final String key) {
        if (exists(key)) {
            redisTemplate.delete(key);
        }
    }

    /**
     * 批量删除key 对应的value
     * @param keys
     */
    public void remove(final String... keys) {
        for (String key : keys) {
            remove(key);
        }
    }

    /**
     * 批量删除key
     * @param pattern
     */
    public void removePattern(final String pattern) {
        Set<Serializable> keys = redisTemplate.keys(pattern +"*");
        if (keys.size() > 0)
            redisTemplate.delete(keys);
    }

    /**
     * 获取key集合
     * @param pattern
     * @return
     */
    public Collection<String> keys(String pattern) {
        return redisTemplate.keys(pattern);
    }

    /**
     * 列表添加
     * @param k
     * @param v
     */
    public void lPush(String k, Object v) {
        listOperations.rightPush(k, v);
    }

    /**
     * 列表获取
     *
     * @param k
     * @param l
     * @param l1
     * @return
     */
    public List<Object> lRange(String k, long l, long l1) {
        return listOperations.range(k, l, l1);
    }

    /**
     * 集合添加
     * @param key
     * @param value
     */
    public void add(String key, Object value) {
        setOperations.add(key, value);
    }

    /**
     * 集合获取
     * @param key
     * @return
     */
    public Set<Object> getMembers(String key) {
//        SetOperations<String, Object> set = redisTemplate.opsForSet();
        return setOperations.members(key);
    }

    /**
     * 有序集合添加
     * @param key
     * @param value
     * @param scoure
     */
    public void zAdd(String key, Object value, double scoure) {
        zsetOperations.add(key, value, scoure);
    }

    /**
     * 有序集合获取
     * @param key
     * @param scoure
     * @param scoure1
     * @return
     */
    public Set<Object> rangeByScore(String key, double scoure, double scoure1) {
        return zsetOperations.rangeByScore(key, scoure, scoure1);
    }
}
