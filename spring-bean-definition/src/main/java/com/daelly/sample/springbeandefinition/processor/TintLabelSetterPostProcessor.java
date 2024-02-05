package com.daelly.sample.springbeandefinition.processor;

import com.daelly.sample.springbeandefinition.bean.Tint;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

@Component
public class TintLabelSetterPostProcessor implements BeanPostProcessor {
    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        // bean 已经被示例化，可以直接操作bean
        if (bean instanceof Tint) {
            Tint tint = (Tint) bean;
            tint.setLabel("postProcessAfterInitialization_" + beanName);
        }
        return bean;
    }
}
