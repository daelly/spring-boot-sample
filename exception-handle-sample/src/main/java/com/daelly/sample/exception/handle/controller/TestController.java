package com.daelly.sample.exception.handle.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class TestController {


    @GetMapping("/test")
    public String test(String param) {
        if ("1".equals(param)) {
            throw new RuntimeException("param is 1");
        }
        return "test";
    }

    @ExceptionHandler(RuntimeException.class)
    public String handleException(Exception e) {
        log.error("handleException handle exception:", e);
        return e.getMessage();
    }
}
