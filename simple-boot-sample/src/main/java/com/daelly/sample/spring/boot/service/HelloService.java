package com.daelly.sample.spring.boot.service;

import com.daelly.sample.spring.boot.SimpleBootApplication;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Slf4j
@Service
public class HelloService {


    @Async
    public void hello() {
        String name = SimpleBootApplication.CONTEXT.get();
        log.info("hello to: {}", name);
    }


    @Async
    public CompletableFuture<String> greet() {
        String name = SimpleBootApplication.CONTEXT.get();
        return CompletableFuture.completedFuture("Hello, " + name);
    }
}
