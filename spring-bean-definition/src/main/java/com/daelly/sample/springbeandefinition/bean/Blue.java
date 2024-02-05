package com.daelly.sample.springbeandefinition.bean;

import org.springframework.stereotype.Component;

@Component
public class Blue extends Tint {

    @Override
    public String toString() {
        return "Blue { label = '" + label + " '}";
    }
}
