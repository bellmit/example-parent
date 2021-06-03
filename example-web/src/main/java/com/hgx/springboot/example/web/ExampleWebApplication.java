package com.hgx.springboot.example.web;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@ComponentScan(basePackages = {"com.hgx.springboot.example"})
@MapperScan(basePackages = {"com.hgx.springboot.example.*.mapper"})
@EnableScheduling
public class ExampleWebApplication {

    public static void main(String[] args) {
        SpringApplication.run(ExampleWebApplication.class, args);
    }

}
