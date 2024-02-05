package com.daelly.sample.aop.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class TestService {

    public void greet(String name) {
        log.info("Hello, {}", name);
    }
}
