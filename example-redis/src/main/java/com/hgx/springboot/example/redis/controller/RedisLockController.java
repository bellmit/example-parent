/**
 * Copyright (C), 2015-2021, hkrt
 * FileName: RedisLockController
 * Author:   gengxin.hao
 * Date:     2021/4/7 17:21
 * Description:
 * History:
 */
package com.hgx.springboot.example.redis.controller;

import com.hgx.springboot.example.redis.exception.ExampleException;
import com.hgx.springboot.example.redis.util.KeyLocker;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CountDownLatch;

/**
 * 〈〉
 *
 * @author gengxin.hao
 * @create 2021/4/7
 * @since 1.0.0
 */
@Slf4j
@RestController
@RequestMapping(value = "/redis")
public class RedisLockController {

    private Integer count = 1000;

    private int errorCount = 0;

    @RequestMapping(value = {"/lock"}, method = {RequestMethod.GET,RequestMethod.POST})
    public String lock() {
        log.info("begin count--");
        try {
            CountDownLatch countDownLatch = new CountDownLatch(1000);
            for (int i = 0; i < 100; i++) {
                new Thread(() -> {
                    try {
                        KeyLocker.lock("product1",()->{
                            count = count - 1;
                            log.info("ProductCount count:{}", count);
                        });
                    } catch (ExampleException e) {
                        synchronized (this){
                            errorCount++;
                        }
                        log.info("lock exception:{}", e.getErrCode());
                    }
                    countDownLatch.countDown();
                },"Thread" + i).start();
            }
            countDownLatch.await();
        }catch (Exception e) {
            log.info("lock ==== exception:{}", e);
        }
        log.info("errorCount:{}", errorCount);
        return null;
    }
}
