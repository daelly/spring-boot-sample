package com.daelly.sample.spring.xml.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.framework.AopContext;

public class CartService {

    private static final Logger log = LoggerFactory.getLogger(CartService.class);

    public void remove() {
        log.info("removing from cart....");
        CartService proxy = (CartService) AopContext.currentProxy();
        proxy.decr();
    }

    public void decr() {
        log.info("decr stoke......");
    }
}
