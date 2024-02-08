package com.daelly.sample;

import com.daelly.sample.bean.Book;
import com.daelly.sample.bean.BookShelf;
import com.daelly.sample.bean.Color;
import com.daelly.sample.bean.Person;
import com.daelly.sample.configuration.AppConfig;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Map;

public class CommonTests {

    @Test
    public void test1() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        Book book = context.getBean(Book.class);
        System.out.println(book.getName());
        context.close();
    }

    @Test
    public void test2() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        BookShelf bookShelf = context.getBean(BookShelf.class);
        System.out.println(bookShelf.getBooks());
        context.close();
    }

    @Test
    public void test3() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        Map<String, Color> colors = context.getBeansOfType(Color.class);
        System.out.println(colors);
        context.close();
    }

    @Test
    public void test4() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        Person person = context.getBean(Person.class);
        System.out.println("Person' name = " + person.getName() + ", age = " + person.getAge() + ", gender = " + person.isGender());
        context.close();
    }
}
