package com.hgx.springboot.example.redis;

import com.hgx.springboot.example.redis.util.SpringUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;

@Import(value = {SpringUtil.class})
@SpringBootApplication
@ComponentScan(value = {"com.hgx.springboot.example.redis.*"})
public class ExampleRedisApplication {

    public static void main(String[] args) {
        SpringApplication.run(ExampleRedisApplication.class, args);
    }

}
