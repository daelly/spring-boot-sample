package com.daelly.sample.aop.config;

import com.daelly.sample.aop.annotation.AdvisableAnnotationAdvisingBeanPostProcessor;
import com.daelly.sample.aop.annotation.AdvisableAnnotationBeanNameBeanPostProcessor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Role;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

import java.util.concurrent.Executor;

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

    @Bean("taskExecutor")
    public Executor taskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(10);
        executor.setMaxPoolSize(100);
        executor.setThreadNamePrefix("MyTask-Executor");
        return executor;
    }

    @Bean
    public ThreadPoolTaskScheduler scheduleExecutor() {
        ThreadPoolTaskScheduler scheduler = new ThreadPoolTaskScheduler();
        scheduler.setPoolSize(10);
        scheduler.setThreadNamePrefix("MyScheduler-");
        return scheduler;
    }

    @Bean
    @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
    public AdvisableAnnotationAdvisingBeanPostProcessor advisingBeanPostProcessor(@Qualifier("scheduleExecutor") ThreadPoolTaskScheduler scheduleExecutor) {
        AdvisableAnnotationAdvisingBeanPostProcessor advisingBeanPostProcessor = new AdvisableAnnotationAdvisingBeanPostProcessor();
        advisingBeanPostProcessor.setScheduler(scheduleExecutor);
        return advisingBeanPostProcessor;
    }

    @Bean
    @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
    public AdvisableAnnotationBeanNameBeanPostProcessor advisableAnnotationBeanNameBeanPostProcessor() {
        return new AdvisableAnnotationBeanNameBeanPostProcessor();
    }
}
