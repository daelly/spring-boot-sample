package com.daelly.sample.aop.config;

import com.daelly.sample.aop.annotation.AdvisableAnnotationAdvisingBeanPostProcessor;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Role;

@Configuration(proxyBeanMethods = false)
@Role(BeanDefinition.ROLE_INFRASTRUCTURE)
public class AopConfiguration {

//    @Bean
//    public AdviseMethodInterceptor adviseMethodInterceptor() {
//        return new AdviseMethodInterceptor();
//    }
//
//    @Bean
//    public MyAdvisor myAdvisor(@Qualifier("adviseMethodInterceptor") AdviseMethodInterceptor adviseMethodInterceptor) {
//        MyAdvisor myAdvisor = new MyAdvisor();
//        myAdvisor.setAdvice(adviseMethodInterceptor);
//        return myAdvisor;
//    }

    @Bean
    @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
    public AdvisableAnnotationAdvisingBeanPostProcessor advisingBeanPostProcessor() {
        AdvisableAnnotationAdvisingBeanPostProcessor advisingBeanPostProcessor = new AdvisableAnnotationAdvisingBeanPostProcessor();
        return advisingBeanPostProcessor;
    }
}
