package com.daelly.sample.spring.boot.config;

import com.daelly.sample.spring.boot.bean.Bean1;
import com.daelly.sample.spring.boot.bean.Bean2;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@ConditionalOnProperty(prefix = "custom.configuration1", name = "enable", matchIfMissing = true)
@Configuration
public class CustomConfiguration1 {

    @Bean
    public Bean1 bean1() {
        return new Bean1();
    }

    @Bean
    public Bean2 bean2() {
        return new Bean2();
    }
}
