package com.daelly.sample.configuration;

import com.daelly.sample.bean.Book;
import com.daelly.sample.bean.BookShelf;
import org.springframework.context.annotation.Bean;

import java.util.Arrays;

public class Library {

    @Bean
    public Book book1() {
        return new Book("The Catcher in the Rye");
    }

    @Bean
    public Book book2() {
        return new Book("To Kill a Mockingbird");
    }

    @Bean
    public BookShelf bookShelf(Book book1, Book book2) {
        return new BookShelf(Arrays.asList(book1, book2));
    }
}
