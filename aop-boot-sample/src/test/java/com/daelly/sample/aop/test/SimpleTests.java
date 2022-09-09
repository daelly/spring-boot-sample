package com.daelly.sample.aop.test;

import com.daelly.sample.aop.domain.Person;
import com.daelly.sample.aop.interceptor.AdviseMethodInterceptor;
import org.junit.jupiter.api.Test;
import org.springframework.aop.framework.ProxyFactory;

public class SimpleTests {


    @Test
    public void test() {
        ProxyFactory proxyFactory = new ProxyFactory();
        proxyFactory.setTarget(new Person());
        proxyFactory.addAdvice(new AdviseMethodInterceptor());
        Object proxy = proxyFactory.getProxy();
        Person proxyPerson = (Person) proxy;
        proxyPerson.eat("Hamburger");
    }

    @Test
    public void test1() {

    }
}
