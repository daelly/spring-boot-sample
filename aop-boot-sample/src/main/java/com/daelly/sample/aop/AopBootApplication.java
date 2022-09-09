package com.daelly.sample.aop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@SpringBootApplication
public class AopBootApplication {

    public static void main(String[] args) {
        SpringApplication.run(AopBootApplication.class, args);
    }
}
