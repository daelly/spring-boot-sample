package com.daelly.sample.springbeandefinition.processor;

import com.daelly.sample.springbeandefinition.bean.Fruit;
import com.daelly.sample.springbeandefinition.bean.Orange;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.stereotype.Component;

@Component
public class OrangeRegisterPostProcessor implements BeanDefinitionRegistryPostProcessor {

    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
        System.out.println("postProcessBeanDefinitionRegistry in OrangeRegisterPostProcessor started.");

        // 在postProcessBeanDefinitionRegistry中动态注册bean
        if (!registry.containsBeanDefinition("orange")) {
            BeanDefinition orangeDefinition = BeanDefinitionBuilder.genericBeanDefinition(Orange.class).getBeanDefinition();
            registry.registerBeanDefinition("orange", orangeDefinition);
        }
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        System.out.println("postProcessBeanFactory in OrangeRegisterPostProcessor started.");

        // 这里可以取到上面注册的orange，说明是先执行 postProcessBeanDefinitionRegistry，再执行 postProcessBeanFactory
        String[] fruitNames = beanFactory.getBeanNamesForType(Fruit.class);
        for (String name : fruitNames) {
            BeanDefinition beanDefinition = beanFactory.getBeanDefinition(name);
            beanDefinition.getPropertyValues().add("type", name);
        }
    }
}
