/**
 * Copyright (C), 2015-2021, hkrt
 * FileName: RateLimiterController
 * Author:   gengxin.hao
 * Date:     2021/3/2 20:38
 * Description:
 * History:
 */
package com.hgx.springboot.example.redis.controller;

import com.hgx.springboot.example.redis.annotation.RateLimiter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * 〈〉
 *
 * @author gengxin.hao
 * @create 2021/3/2
 * @since 1.0.0
 */
@RestController
@RequestMapping(value = {"/rateLimiter"})
@Slf4j
public class RateLimiterController {

    @RequestMapping(value = {"/test"})
    @RateLimiter(count = 5, time = 10,restrictionsIp = true)
    public Map<String,Object> rateLimiterTest() {
        Map<String,Object> result = new HashMap<>();
        try {
            log.info("正常请求,已处理");
            result.put("rspCode","00");
            result.put("rspMsg","已处理");
        }catch (Exception e) {
            log.info("异常:{}",e);
            result.put("rspCode","99");
            result.put("rspMsg","已处理");
        }
        return result;
    }
}
