package com.daelly.sample.spring.xml.aspect;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UserServiceAspect implements MethodInterceptor {

    private static final Logger log = LoggerFactory.getLogger(UserServiceAspect.class);

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        log.info("invoke before user add....");
        return invocation.proceed();
    }
}
