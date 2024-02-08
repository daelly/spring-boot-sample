package com.daelly.sample.bean;

import java.util.List;

public class BookShelf {

    private List<Book> books;

    public BookShelf() {
    }

    public BookShelf(List<Book> books) {
        this.books = books;
    }

    public List<Book> getBooks() {
        return books;
    }
}
