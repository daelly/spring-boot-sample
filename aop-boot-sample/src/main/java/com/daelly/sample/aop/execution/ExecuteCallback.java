package com.daelly.sample.aop.execution;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.BeanNameAware;

import java.lang.reflect.Method;

@Slf4j
public class ExecuteCallback implements BeanNameAware {

    protected String beanName;

    protected Object bean;

    protected Method method;

    public ExecuteCallback(Object bean, Method method) {
        this.bean = bean;
        this.method = method;
    }

    public String execute(String name) {
        try {
            return (String) method.invoke(bean, name);
        } catch (Exception e) {
            log.error("ExecuteCallback execute fail:", e);
        }

        return null;
    }

    @Override
    public void setBeanName(String name) {
        this.beanName = name;
    }

    @Override
    public String toString() {
        return "ExecuteCallback{" +
                "beanName='" + beanName + '\'' +
                ", bean=" + bean +
                ", method=" + method +
                '}';
    }
}
