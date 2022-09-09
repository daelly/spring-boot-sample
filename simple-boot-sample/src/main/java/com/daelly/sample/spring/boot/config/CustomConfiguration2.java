package com.daelly.sample.spring.boot.config;

import com.daelly.sample.spring.boot.bean.Bean3;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 *
 */
@Configuration
@ConditionalOnBean(CustomConfiguration1.class)
@AutoConfigureAfter(CustomConfiguration1.class)
public class CustomConfiguration2 {

    @Bean("firstBean3")
    public Bean3 fistBean3() {
        return new Bean3();
    }

    @Bean("secondBean3")
    public Bean3 secondBean3() {
        return new Bean3();
    }
}
