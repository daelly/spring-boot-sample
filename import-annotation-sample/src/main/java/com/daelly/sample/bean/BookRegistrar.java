package com.daelly.sample.bean;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;

public class BookRegistrar implements ImportBeanDefinitionRegistrar {

    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        BeanDefinition definition = BeanDefinitionBuilder.genericBeanDefinition(Book.class)
                .setScope(BeanDefinition.SCOPE_PROTOTYPE)
                .addPropertyValue("name", "War and Piece")
                .getBeanDefinition();

        registry.registerBeanDefinition("myBook", definition);
    }
}
