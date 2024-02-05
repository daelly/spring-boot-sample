package com.daelly.sample.spring.boot;

import com.alibaba.ttl.TransmittableThreadLocal;
import com.daelly.sample.spring.boot.service.HelloService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;

import java.util.concurrent.CompletableFuture;

@Slf4j
@EnableAsync
@EnableRetry
@SpringBootApplication
public class SimpleBootApplication {

    public static final TransmittableThreadLocal<String> CONTEXT = new TransmittableThreadLocal<>();

//    public static final ThreadLocal<String> CONTEXT = new ThreadLocal<>();

    public static void main(String[] args) {
        SpringApplication.run(SimpleBootApplication.class, args);
    }

    @Autowired
    private HelloService helloService;

    @Bean
    public CommandLineRunner cmd1() {
        return (args) -> {
            String name = "Daelly";
            log.info("cmd1 set with:{}", name);
            CONTEXT.set(name);
            helloService.hello();
        };
    }

    @Bean
    public CommandLineRunner cmd2() {
        return (args) -> {
            String name = "Ripper";
            log.info("cmd2 set with:{}", name);
            CONTEXT.set(name);
            CompletableFuture<String> future = helloService.greet();
            log.info("cmd2 get from future:{}", future.get());
        };
    }
}
