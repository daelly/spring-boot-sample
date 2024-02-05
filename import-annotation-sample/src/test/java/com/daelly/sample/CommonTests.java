package com.daelly.sample;

import com.daelly.sample.bean.Book;
import com.daelly.sample.configuration.AppConfig;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class CommonTests {

    @Test
    public void test1() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        Book book = context.getBean(Book.class);
        System.out.println(book.getName());
        context.close();
    }
}
