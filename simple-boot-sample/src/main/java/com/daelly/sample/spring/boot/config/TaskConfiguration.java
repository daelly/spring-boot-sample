package com.daelly.sample.spring.boot.config;

import com.daelly.sample.spring.boot.task.MyTaskDecorator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskDecorator;

@Configuration
public class TaskConfiguration {

    @Bean
    public TaskDecorator myTaskDecorator() {
        return new MyTaskDecorator();
    }
}
