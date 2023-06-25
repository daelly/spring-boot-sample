package com.daelly.sample.spring.web.config;

import com.daelly.sample.spring.web.controller.ConfigurableController;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty(name = {"sample.condition1", "sample.condition3"}, matchIfMissing = true)
public class SpringWebConfiguration {

    @Value("${sample.prop1:test}")
    private String prop1;

    @Bean
    public ConfigurableController configurableController() {
        ConfigurableController controller = new ConfigurableController();
        controller.setProp1(prop1);
        return controller;
    }
}
