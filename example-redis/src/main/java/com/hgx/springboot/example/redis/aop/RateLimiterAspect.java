/**
 * Copyright (C), 2015-2021, hkrt
 * FileName: RateLimiterAspect
 * Author:   gengxin.hao
 * Date:     2021/3/3 13:09
 * Description:
 * History:
 */
package com.hgx.springboot.example.redis.aop;

import com.hgx.springboot.example.redis.annotation.RateLimiter;
import com.hgx.springboot.example.redis.exception.RateLimiterException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.scripting.support.ResourceScriptSource;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 〈限流〉
 *
 * @author gengxin.hao
 * @create 2021/3/3
 * @since 1.0.0
 */
@Component
@Slf4j
@Aspect
public class RateLimiterAspect {

    private RedisTemplate<String,String> redisTemplate;

    private DefaultRedisScript<Number> redisScript;

    @Autowired
    public void setRedisTemplate(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @PostConstruct
    public void init() {
        redisScript = new DefaultRedisScript<>();
        redisScript.setResultType(Number.class);
        redisScript.setScriptSource(new ResourceScriptSource(new ClassPathResource("redis/ratelimiterScript.lua")));
    }

    private static ThreadLocal<String> ipThreadLocal = new ThreadLocal<>();

    @Around("@annotation(com.hgx.springboot.example.redis.annotation.RateLimiter)")
    public Object interceptor(ProceedingJoinPoint joinPoint) throws Throwable {
        try {
            //获取方法相关信息
            MethodSignature signature = (MethodSignature) joinPoint.getSignature();
            Method method = signature.getMethod();
            Class<?> targetClass = method.getDeclaringClass();
            RateLimiter rateLimiter = method.getAnnotation(RateLimiter.class);
            if (rateLimiter == null) {
                return joinPoint.proceed();
            }
            HttpServletRequest request = ((ServletRequestAttributes)
                    RequestContextHolder.getRequestAttributes()).getRequest();

            if (rateLimiter.restrictionsIp()) {
                ipThreadLocal.set(getIpAddr(request));
            }
            StringBuffer stringBuffer = new StringBuffer(rateLimiter.key());
            if (StringUtils.isNotBlank(ipThreadLocal.get())) {
                stringBuffer.append(ipThreadLocal.get()).append("-");
            }
            stringBuffer.append("-").append(targetClass.getName()).append("- ")
                    .append(method.getName());
            List<String> keys = Collections.singletonList(stringBuffer.toString());
            Number number = redisTemplate.execute(redisScript, keys, rateLimiter.count(), rateLimiter.time());
            if (number != null && number.intValue() != 0 && number.intValue() <= Integer.valueOf(rateLimiter.count())) {
                log.info("限流时间段内访问第{}次",number.toString());
                return joinPoint.proceed();
            }else {
                log.info("已经到设置限流次数,当前次数:{}", rateLimiter.count() + 1);
                Map<String,Object> result = new HashMap<>();
                result.put("rspCode","99");
                result.put("rspMsg","您的请求过于频繁,请稍后再试");
                return result;
//                throw new RateLimiterException("服务器繁忙,请稍后再试");
            }
        }finally {
            ipThreadLocal.remove();
        }


    }

    public static String getIpAddr(HttpServletRequest request) {
        String ipAddress = null;
        try {
            ipAddress = request.getHeader("x-forwarded-for");
            if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
                ipAddress = request.getHeader("Proxy-Client-IP");
            }
            if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
                ipAddress = request.getHeader("WL-Proxy-Client-IP");
            }
            if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
                ipAddress = request.getRemoteAddr();
            }
            // 对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
            if (ipAddress != null && ipAddress.length() > 15) {
                // "***.***.***.***".length()= 15
                if (ipAddress.indexOf(",") > 0) {
                    ipAddress = ipAddress.substring(0, ipAddress.indexOf(","));
                }
            }
        } catch (Exception e) {
            ipAddress = "";
        }
        return ipAddress;
    }
}
