package com.daelly.sample.aop.service;

import com.daelly.sample.aop.annotation.Advisable;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class TestService {

    @Advisable
    public String test(String name) {
        log.info("current thread:{}", Thread.currentThread().getName());
        return "Hello, " + name;
    }
}
