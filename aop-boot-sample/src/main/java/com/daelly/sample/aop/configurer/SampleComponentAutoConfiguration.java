package com.daelly.sample.aop.configurer;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SampleComponentAutoConfiguration extends AbstractSampleComponentConfiguration {

    @Value("${sample.component.name:daelly}")
    protected String configName;

    @Value("${sample.component.age:21}")
    protected int configAge;

    @Bean
    public SampleComponent sampleComponent() {
        SampleComponent sampleComponent = new SampleComponent();
        sampleComponent.setName(configName);
        sampleComponent.setAge(configAge);

        sampleComponent.configurer(name, age);
        return sampleComponent;
    }
}
