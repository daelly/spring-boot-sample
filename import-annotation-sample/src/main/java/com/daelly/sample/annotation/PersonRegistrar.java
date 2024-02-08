package com.daelly.sample.annotation;

import com.daelly.sample.bean.Person;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;

import java.util.Map;

public class PersonRegistrar implements ImportBeanDefinitionRegistrar {

    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        Map<String, Object> attrs = importingClassMetadata.getAnnotationAttributes(EnablePerson.class.getName());
        String name = (String) attrs.get("name");
        BeanDefinition definition = BeanDefinitionBuilder.genericBeanDefinition(Person.class)
                .addPropertyValue("name", name)
                .addPropertyValue("age", attrs.get("age"))
                .addPropertyValue("gender", attrs.get("gender"))
                .getBeanDefinition();

        registry.registerBeanDefinition("person:" + name, definition);
    }
}
