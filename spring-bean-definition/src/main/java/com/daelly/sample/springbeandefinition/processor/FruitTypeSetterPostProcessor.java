package com.daelly.sample.springbeandefinition.processor;

import com.daelly.sample.springbeandefinition.bean.Fruit;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.stereotype.Component;

@Component
public class FruitTypeSetterPostProcessor implements BeanFactoryPostProcessor {

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        System.out.println("postProcessBeanFactory in FruitTypeSetterPostProcessor started.");

        String[] fruitNames = beanFactory.getBeanNamesForType(Fruit.class);
        for (String name : fruitNames) {
            BeanDefinition beanDefinition = beanFactory.getBeanDefinition(name);
            beanDefinition.getPropertyValues().add("type", name);
        }
    }
}
