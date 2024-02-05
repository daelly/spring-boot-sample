package com.daelly.sample.springbeandefinition.bean;

import org.springframework.stereotype.Component;

@Component
public class Apple extends Fruit {

    @Override
    public String toString() {
        return "Apple { type = '" + type + "' }";
    }
}
