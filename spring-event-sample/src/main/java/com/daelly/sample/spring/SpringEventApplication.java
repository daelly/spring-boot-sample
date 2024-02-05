package com.daelly.sample.spring;


import com.daelly.sample.spring.event.Object1Event;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SpringEventApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringEventApplication.class, args);
    }

    @Bean
    public CommandLineRunner cmd1(ApplicationEventPublisher publisher) {
        return (args) -> publisher.publishEvent(new Object1Event(this));
    }
}
