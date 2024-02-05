package com.daelly.sample.spring.xml.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.framework.AopContext;

public class UserService {

    private static final Logger log = LoggerFactory.getLogger(UserService.class);

    public void add() {
        log.info("adding user.....");
        UserService proxy = (UserService) AopContext.currentProxy();
        proxy.setPass();
    }

    public void setPass() {
        log.info("setting password.....");
    }
}
