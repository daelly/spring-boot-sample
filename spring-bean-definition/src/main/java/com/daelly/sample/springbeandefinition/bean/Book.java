package com.daelly.sample.springbeandefinition.bean;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Book {

    private String title;

    private String author;

    public Book() {}

    public Book(String title, String author) {
        this.title = title;
        this.author = author;
    }
}
