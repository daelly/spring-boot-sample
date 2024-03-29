package com.daelly.sample.springbeandefinition.bean;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

public class Person implements InitializingBean, DisposableBean {

    private String name;

    private int age;

    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
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
