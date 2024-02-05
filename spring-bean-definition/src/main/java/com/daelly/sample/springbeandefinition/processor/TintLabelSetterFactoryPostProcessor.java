package com.daelly.sample.springbeandefinition.processor;

import com.daelly.sample.springbeandefinition.bean.Tint;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.ClassUtils;
import org.springframework.util.StringUtils;

@Component
public class TintLabelSetterFactoryPostProcessor implements BeanFactoryPostProcessor {

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        // 这个时候beanFactory里只有beanDefinition，
        // 不要在这里调用beanFactory的getBean方法，这会导致bean跳过一些必要的Advice而直接实例化，继而出现各种问题
        // 正确的做法是，访问beanDefinition，并且处理beanDefinition
        for (String beanName : beanFactory.getBeanDefinitionNames()) {
            BeanDefinition definition = beanFactory.getBeanDefinition(beanName);

            if (!StringUtils.hasLength(definition.getBeanClassName())) {
                continue;
            }

            Class<?> clazz = ClassUtils.resolveClassName(definition.getBeanClassName(), null);
            if (Tint.class.isAssignableFrom(clazz)) {
                definition.getPropertyValues().add("label", "postProcessBeanFactory_" + beanName);
            }
        }
    }
}
