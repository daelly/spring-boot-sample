package com.daelly.sample.spring.web.controller;

import org.springframework.web.bind.annotation.RequestMapping;

public class ConfigurableController extends AnnotationBaseController {

    protected String prop1;

    @RequestMapping("/test")
    public String test() {
        return "configurable controller worked! Prop1 is: " + prop1;
    }

    public void setProp1(String prop1) {
        this.prop1 = prop1;
    }
}
