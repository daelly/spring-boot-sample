package com.daelly.sample.aop.service;

import com.daelly.sample.aop.annotation.Advisable;
import com.daelly.sample.aop.execution.ExecuteCallback;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class HelloService implements BeanFactoryAware {

    private BeanFactory _beanFactory;

    @Advisable
    public String hello(String name) {
        log.info("current thread:{}", Thread.currentThread().getName());
        return "Hello, " + name;
    }

    public String printExecutionBean() {
        String beanInfo = this._beanFactory.getBean(ExecuteCallback.class).toString();
        log.info("executeCallback bean info:{}", beanInfo);
        return beanInfo;
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this._beanFactory = beanFactory;
    }
}
