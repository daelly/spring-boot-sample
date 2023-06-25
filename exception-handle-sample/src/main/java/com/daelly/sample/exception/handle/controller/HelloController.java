package com.daelly.sample.exception.handle.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @GetMapping("/hello")
    public String hello(String param) {
        if ("1".equals(param)) {
            throw new RuntimeException("param is 1");
        }
        return "hello";
    }
}
