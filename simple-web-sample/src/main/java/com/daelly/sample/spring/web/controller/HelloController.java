package com.daelly.sample.spring.web.controller;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Base64Utils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.nio.charset.StandardCharsets;

@RestController
@RequestMapping("/hello")
public class HelloController {

    @GetMapping("${greet.uri:#{T(com.daelly.sample.spring.web.utils.WebConst).DEFAULT_GREET_URI}}")
    public String greet(String name) {
        return "Hello, " + name;
    }

    @RequestMapping("/hex")
    public ResponseEntity<String> hex(String str) {
        String encoded = Base64Utils.encodeToString(str.getBytes(StandardCharsets.UTF_8));
        return ResponseEntity.created(null).contentType(MediaType.TEXT_PLAIN).body(encoded);
    }
}
