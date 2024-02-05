package com.daelly.sample.springbeandefinition.bean;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.concurrent.atomic.AtomicInteger;

public class CounterBean implements InitializingBean, DisposableBean {

    private static AtomicInteger index = new AtomicInteger(0);

    private int counter;

    public CounterBean() {
        counter = index.incrementAndGet();
        System.out.println("init counter with: " + counter);
    }

    public int getCounter() {
        return counter;
    }

    public void initMethod() {
        System.out.println("-----------------------------------initMethod");
    }

    public void destroyMethod() {
        System.out.println("-----------------------------------destroyMethod");
    }

    @PostConstruct
    public void postConstructMethod() {
        System.out.println("-----------------------------------postConstructMethod");
    }

    @PreDestroy
    public void preDestroyMethod() {
        System.out.println("-----------------------------------preDestroyMethod");
    }

    @Override
    public void destroy() throws Exception {
        System.out.println("-----------------------------------destroy");
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("-----------------------------------afterPropertiesSet");
    }
}
