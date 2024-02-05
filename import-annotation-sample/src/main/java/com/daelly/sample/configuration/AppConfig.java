package com.daelly.sample.configuration;

import com.daelly.sample.bean.Book;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(Book.class)
public class AppConfig {
}
