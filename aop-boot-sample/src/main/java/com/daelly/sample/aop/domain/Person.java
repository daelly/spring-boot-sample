package com.daelly.sample.aop.domain;

public class Person {

    public String eat(String food) {
        System.out.println("person eat " + food);
        return "I'm full";
    }
}
