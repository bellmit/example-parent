/**
 * Copyright (C), 2015-2021, hkrt
 * FileName: RateLimiterException
 * Author:   gengxin.hao
 * Date:     2021/3/3 16:48
 * Description: 限流异常
 * History:
 */
package com.hgx.springboot.example.redis.exception;

/**
 * 〈限流异常〉
 *
 * @author gengxin.hao
 * @create 2021/3/3
 * @since 1.0.0
 */
public class RateLimiterException extends Exception{

    public RateLimiterException(String message) {
        super(message);
    }
}
