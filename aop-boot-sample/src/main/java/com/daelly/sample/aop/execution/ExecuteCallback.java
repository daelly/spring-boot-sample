package com.daelly.sample.aop.execution;

import org.springframework.beans.factory.BeanNameAware;

import java.lang.reflect.Method;

public class ExecuteCallback implements BeanNameAware {

    protected String beanName;

    protected Object bean;

    protected Method method;

    public ExecuteCallback(Object bean, Method method) {
        this.bean = bean;
        this.method = method;
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
