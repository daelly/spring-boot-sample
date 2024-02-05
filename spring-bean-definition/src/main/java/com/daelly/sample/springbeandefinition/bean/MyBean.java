package com.daelly.sample.springbeandefinition.bean;

public class MyBean {

    private String message;

    public void doSomething() {
        System.out.println("Hello, MyBean");
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
