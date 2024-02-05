package com.daelly.sample.bean;

public class Book {

    private String name;

    public Book() {
        this.name = "unknown";
    }

    public Book(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
