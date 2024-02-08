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

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Book { name = '" + name + "' }";
    }
}
