package com.daelly.sample.spring.boot.config;

import com.daelly.sample.spring.boot.bean.Bean3;
import com.daelly.sample.spring.boot.bean.Bean4;
import com.daelly.sample.spring.boot.bean.JetConfig;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 *
 */
@Configuration
@ConditionalOnBean(CustomConfiguration1.class)
@AutoConfigureAfter(CustomConfiguration1.class)
@EnableConfigurationProperties(JetConfig.class)
public class CustomConfiguration2 {

    private final JetConfig jetConfig;

    public CustomConfiguration2(JetConfig jetConfig) {
        this.jetConfig = jetConfig;
    }

    @Bean("firstBean3")
    public Bean3 firstBean3() {
        return new Bean3();
    }

    @Bean("secondBean3")
    public Bean3 secondBean3() {
        return new Bean3();
    }

    @Bean("bean4")
    public Bean4 bean4() {
        return new Bean4(jetConfig);
    }
}
