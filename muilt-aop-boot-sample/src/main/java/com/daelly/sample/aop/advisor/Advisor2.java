package com.daelly.sample.aop.advisor;

import com.daelly.sample.aop.service.TestService;
import lombok.extern.slf4j.Slf4j;
import org.aopalliance.aop.Advice;
import org.aopalliance.intercept.MethodInterceptor;
import org.springframework.aop.support.StaticMethodMatcherPointcutAdvisor;

import java.lang.reflect.Method;

@Slf4j
public class Advisor2 extends StaticMethodMatcherPointcutAdvisor {

    @Override
    public boolean matches(Method method, Class<?> targetClass) {
        return targetClass.isAssignableFrom(TestService.class);
    }

    @Override
    public Advice getAdvice() {
        return (MethodInterceptor) invocation -> {
            log.info("Advisor2 execute");
            return invocation.proceed();
        };
    }

    @Override
    public int getOrder() {
        return 2;
    }
}
