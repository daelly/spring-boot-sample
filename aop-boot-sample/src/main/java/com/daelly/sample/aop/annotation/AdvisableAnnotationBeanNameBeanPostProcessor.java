package com.daelly.sample.aop.annotation;

import com.daelly.sample.aop.service.HelloService;
import com.daelly.sample.aop.service.TestService;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.aop.framework.autoproxy.AbstractBeanFactoryAwareAdvisingPostProcessor;
import org.springframework.aop.interceptor.ExposeBeanNameAdvisors;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.aop.support.StaticMethodMatcherPointcut;
import org.springframework.aop.support.annotation.AnnotationMatchingPointcut;
import org.springframework.core.Ordered;

import java.lang.reflect.Method;

public class AdvisableAnnotationBeanNameBeanPostProcessor extends AbstractBeanFactoryAwareAdvisingPostProcessor {

//    public AdvisableAnnotationBeanNameBeanPostProcessor() {
//        DefaultPointcutAdvisor _advisor = new DefaultPointcutAdvisor();
//        _advisor.setPointcut(new StaticMethodMatcherPointcut() {
//            @Override
//            public boolean matches(Method method, Class<?> targetClass) {
//                return targetClass.isAssignableFrom(HelloService.class);
//            }
//        });
//        _advisor.setAdvice(new MethodInterceptor() {
//            @Override
//            public Object invoke(MethodInvocation invocation) throws Throwable {
//                System.out.println("AdvisableAnnotationBeanNameBeanPostProcessor process--------------");
//                return invocation.proceed();
//            }
//        });
//
//        this.advisor = _advisor;
//    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) {
        this.beforeExistingAdvisors = true;
        DefaultPointcutAdvisor _advisor = (DefaultPointcutAdvisor) ExposeBeanNameAdvisors.createAdvisorWithoutIntroduction(beanName);
        _advisor.setPointcut(new AnnotationMatchingPointcut(null, Advisable.class));
//        _advisor.setOrder(Ordered.HIGHEST_PRECEDENCE);
        this.advisor = _advisor;
        return super.postProcessAfterInitialization(bean, beanName);
    }
}
