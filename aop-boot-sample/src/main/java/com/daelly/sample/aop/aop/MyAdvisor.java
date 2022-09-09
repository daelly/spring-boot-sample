package com.daelly.sample.aop.aop;

import org.springframework.aop.support.StaticMethodMatcherPointcutAdvisor;

import java.lang.reflect.Method;

public class MyAdvisor extends StaticMethodMatcherPointcutAdvisor {

    @Override
    public boolean matches(Method method, Class<?> targetClass) {
        return targetClass.getSimpleName().equals("HelloService") && method.getName().equals("hello");
    }
}
