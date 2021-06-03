/**
 * Copyright (C), 2015-2021, hkrt
 * FileName: RedisLock
 * Author:   gengxin.hao
 * Date:     2021/4/7 13:37
 * Description: redis 分布式锁
 * History:
 */
package com.hgx.springboot.example.redis.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * 〈redis 分布式锁〉
 *
 * @author gengxin.hao
 * @create 2021/4/7
 * @since 1.0.0
 */
@Slf4j
public class RedisLock {

    private RedisTemplate redisTemplate;

    private static final int DEFAULT_ACQUIRY_RESOLUTION_MILLIS = 100;

    /**
     * 锁等待时间，防止线程饥饿
     */
    private long timeOutMsecs = (long) 10 * 1000;

    /**
     * 锁超时时间，防止线程在入锁以后，无限的执行等待
     */
    private long expireMsecs = (long) 60 * 1000;

    /**
     * Lock key path.
     */
    private String lockKey;

    private volatile boolean locked = false;

    public RedisLock(RedisTemplate redisTemplate, String lockKey) {
        this.redisTemplate = redisTemplate;
        this.lockKey = lockKey + "_lock";
    }

    public RedisLock(RedisTemplate redisTemplate, String lockKey, long timeOutMsecs) {
        this(redisTemplate, lockKey);
        this.timeOutMsecs = timeOutMsecs;
    }

    public RedisLock(RedisTemplate redisTemplate, String lockKey, long timeOutMsecs, long expireMsecs) {
        this(redisTemplate, lockKey, timeOutMsecs);
        this.expireMsecs = expireMsecs;
    }

    private String get(String key) {
        Object obj = null;
        try {
            obj = redisTemplate.execute((RedisCallback<Object>) connection -> {
                StringRedisSerializer serializer = new StringRedisSerializer();
                byte[] data = connection.get(serializer.serialize(key));
                connection.close();
                if (data == null) {
                    return null;
                }
                return serializer.deserialize(data);
            });
        }catch (Exception e) {
            log.error("get redis error, key : {}", key);
        }
        return obj != null ? obj.toString() : null;
    }

    private Long del(final String key) {
        Object obj = null;
        try {
            obj = redisTemplate.execute((RedisCallback<Object>) connection -> {
                StringRedisSerializer serializer = new StringRedisSerializer();
                Long ret = connection.del(serializer.serialize(key));
                connection.close();
                return ret;
            });
        }catch (Exception e) {
            log.info("del redis error, key : {}", key);
        }
        return (long) (obj != null ? 1 : 0);
    }

    /**
     * @Description  
     * @author gengxin.hao
     * @CreateDate 2021/4/7 13:51
     * @param 
     * @return 
     * @exception 
     * @Version 1.0
     */
    private boolean setNX(final String key, final String value) {
        Object obj = null;
        try {
            obj = redisTemplate.execute((RedisCallback<Object>) connection -> {
                StringRedisSerializer serializer = new StringRedisSerializer();
                Boolean success = connection.setNX(serializer.serialize(key), serializer.serialize(value));
                connection.close();
                return success;
            });
        } catch (Exception e) {
            log.error("setNX redis error, key : {}", key);
        }
        return obj != null ? (Boolean) obj : false;
    }

    private String getSet(final String key, final String value) {
        Object obj = null;
        try {
            obj = redisTemplate.execute((RedisCallback<Object>) connection -> {
                StringRedisSerializer serializer = new StringRedisSerializer();
                byte[] ret = connection.getSet(serializer.serialize(key), serializer.serialize(value));
                connection.close();
                return serializer.deserialize(ret);
            });
        }catch (Exception e) {
            log.error("getSet redis error, key : {}", key);
        }
        return obj != null ? (String) obj : null;
    }

    /**
     * @Description  
     * @author gengxin.hao
     * @CreateDate 2021/4/7 14:01
     * @param 
     * @return 
     * @exception 
     * @Version 1.0
     */
    public synchronized boolean lock() {
        long timeOut = timeOutMsecs;
        while (timeOut >= 0) {
            long expires = System.currentTimeMillis() + expireMsecs + 1;
            //锁到期时间
            String expiresStr = String.valueOf(expires);
            if (this.setNX(lockKey, expiresStr)) {
                locked = true;
                return true;
            }
            //redis里的时间
            String currentValueStr = this.get(lockKey);
            if (currentValueStr != null
                    && Long.parseLong(currentValueStr) < System.currentTimeMillis()) {
                //判断是否为空，不为空的情况下，如果被其他线程设置了值，则第二个条件判断是过不去的
                String oldValueStr = this.getSet(lockKey, expiresStr);
                //获取上一个锁到期时间，并设置现在的锁到期时间
                //只有一个线程才能获取上一个线程上的时间，因为Jedis.getSet是同步的
                if (oldValueStr != null && oldValueStr.equals(currentValueStr)) {
                    //防止误删（覆盖，因为key是相同的）了他人的锁--这里达不到效果，这里值会被覆盖，但是因为相差了很少的时间，
                    // 所以可以接受
                    // [分布式的情况下]:如过这个时候，多个线程恰好都到了这里，但是只有一个线程的设置值和当前值相同，
                    // 他才有权利获取锁
                    locked = true;
                    return true;
                }
            }
            if (currentValueStr == null) {
                return false;
            }
            timeOut -= DEFAULT_ACQUIRY_RESOLUTION_MILLIS;
        }
        return false;
    }

    public synchronized void unLock() {
        if (locked) {
            this.del(lockKey);
            locked = false;
        }
    }


}
