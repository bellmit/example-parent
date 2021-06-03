/**
 * Copyright (C), 2015-2021, hkrt
 * FileName: RateLimiter
 * Author:   gengxin.hao
 * Date:     2021/3/3 13:05
 * Description: 限流注解
 * History:
 */
package com.hgx.springboot.example.redis.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 〈限流注解〉
 *
 * @author gengxin.hao
 * @create 2021/3/3
 * @since 1.0.0
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface RateLimiter {

    /**
     * 限流唯一标识
     * @return
     */
    String key() default "rate.limit";

    /**
     * 限流时间
     * @return
     */
    int time() default 1;

    /**
     * 限流次数
     */
    int count() default 100;

    /**
     * 是否限制IP,默认否
     * @return
     */
    boolean restrictionsIp() default false;
}
