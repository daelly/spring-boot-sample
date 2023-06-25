package com.daelly.sample.aop.configurer;

import java.util.function.Supplier;

public class SampleComponent {

    private String name;

    private Integer age;

    public void configurer(Supplier<String> name, Supplier<Integer> age) {
        this.name = name.get();
        this.age = age.get();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }
}
