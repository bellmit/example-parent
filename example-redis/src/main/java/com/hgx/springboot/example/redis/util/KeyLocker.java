/**
 * Copyright (C), 2015-2021, hkrt
 * FileName: KeyLocker
 * Author:   gengxin.hao
 * Date:     2021/3/4 16:55
 * Description:
 * History:
 */
package com.hgx.springboot.example.redis.util;

import com.hgx.springboot.example.redis.exception.ExampleException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;

/**
 * 〈根据指定key做线程同步〉
 *
 * @author gengxin.hao
 * @create 2021/3/4
 * @since 1.0.0
 */
@Slf4j
public class KeyLocker {

    /**
     * 锁获取失败
     */
    public static final String ERR_LOCK_GET_FAIL = "E999982";

    /**
     * 等待锁的时间，单位ms
     */
    private static final long LOCK_TIME_OUT_MILLS = 10 * 1000;

    public static void lock(String key, Runner runner) throws ExampleException{
        //创建锁对象
        RedisLock lock = new RedisLock(SpringUtil.getBean(StringRedisTemplate.class), key, LOCK_TIME_OUT_MILLS,
                LOCK_TIME_OUT_MILLS);
        try {
            if (lock.lock()) {
                runner.run();
            } else {
                // 锁获取失败，则抛异常
                throw new ExampleException(KeyLocker.ERR_LOCK_GET_FAIL);
            }
        } catch (ExampleException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            lock.unLock();
        }
    }

    public interface Runner<T> {

        /**
         * @Description  同步代码
         * @author gengxin.hao
         * @CreateDate 2021/4/7 16:45
         * @param
         * @return
         * @exception
         * @Version 1.0
         */
        void run() throws ExampleException;
    }
}
