package com.daelly.sample.spring.boot.bean;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.retry.annotation.Retryable;

@Slf4j
public class Bean3 {

    public Bean3() {
        log.info("Bean3 construct--------------------------------------------------------------------");
    }

    @Retryable
    public void trying() {
        if (RandomUtils.nextBoolean()) {
            log.error("Bean3 trying exception---------------------------------------------------------------");
            throw new RuntimeException("custom exception");
        }

        log.info("Bean3 trying success---------------------------------------------------------------");
    }
}
