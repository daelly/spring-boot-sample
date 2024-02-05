package com.daelly.sample.aop.config;

import com.daelly.sample.aop.advisor.Advisor1;
import com.daelly.sample.aop.advisor.Advisor2;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Role;

@Configuration
public class MultiAopConfiguration {

    @Bean
    @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
    public MyAdvisingBeanPostProcessor beanPostProcessor1() {
        MyAdvisingBeanPostProcessor beanPostProcessor = new MyAdvisingBeanPostProcessor();
        beanPostProcessor.setOrder(1);
        beanPostProcessor.setAdvisor(new Advisor1());
        return beanPostProcessor;
    }

    @Bean
    @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
    public MyAdvisingBeanPostProcessor beanPostProcessor2() {
        MyAdvisingBeanPostProcessor beanPostProcessor = new MyAdvisingBeanPostProcessor();
        beanPostProcessor.setOrder(2);
        beanPostProcessor.setAdvisor(new Advisor2());
        return beanPostProcessor;
    }
}
