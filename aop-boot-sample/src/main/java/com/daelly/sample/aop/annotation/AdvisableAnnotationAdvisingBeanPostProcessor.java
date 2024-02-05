package com.daelly.sample.aop.annotation;

import com.daelly.sample.aop.aop.MyAdvisor;
import com.daelly.sample.aop.execution.ExecuteCallback;
import com.daelly.sample.aop.interceptor.AdviseMethodInterceptor;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.aop.framework.AopProxyUtils;
import org.springframework.aop.framework.autoproxy.AbstractBeanFactoryAwareAdvisingPostProcessor;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.lang.Nullable;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Method;
import java.util.Arrays;

public class AdvisableAnnotationAdvisingBeanPostProcessor extends AbstractBeanFactoryAwareAdvisingPostProcessor {

    @Nullable
    private DefaultListableBeanFactory _beanFactory;

    private ThreadPoolTaskScheduler scheduler;

    @Override
    public void setBeanFactory(BeanFactory beanFactory) {
        this._beanFactory = (beanFactory instanceof DefaultListableBeanFactory ?
                (DefaultListableBeanFactory) beanFactory : null);
        super.setBeanFactory(beanFactory);

        AdviseMethodInterceptor methodInterceptor = new AdviseMethodInterceptor();
        methodInterceptor.setBeanFactory(beanFactory);
        methodInterceptor.setScheduler(scheduler);
        MyAdvisor myAdvisor = new MyAdvisor();
        myAdvisor.setAdvice(methodInterceptor);
        this.advisor = myAdvisor;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) {
        registerExecutionBean(bean, beanName);
        return super.postProcessAfterInitialization(bean, beanName);
    }

    private void registerExecutionBean(Object bean, String beanName) {
//        Class<?> clazz = bean.getClass();
        Class<?> clazz = AopProxyUtils.ultimateTargetClass(bean);
        Advisable clazzAnnotation = AnnotationUtils.findAnnotation(clazz, Advisable.class);

        Method[] methods = ReflectionUtils.getDeclaredMethods(clazz);
        if (clazzAnnotation == null) {
            methods = Arrays.stream(methods).filter(method -> AnnotationUtils.findAnnotation(method, Advisable.class) != null).toArray(Method[]::new);
        }

        if (ArrayUtils.isEmpty(methods)) {
            return;
        }

        System.out.println("bean=======----------------------------------------------------------------" + bean);
        for (Method method : methods) {
            doRegisterExecutionBean(bean, beanName, method);
        }
    }

    private void doRegisterExecutionBean(Object bean, String beanName, Method method) {
        Method proxyMethod = ReflectionUtils.findMethod(bean.getClass(), method.getName(), method.getParameterTypes());
        final String _beanName = beanName + RandomStringUtils.random(6, true, true);
        BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition(ExecuteCallback.class)
                .addConstructorArgValue(bean)
                .addConstructorArgValue(proxyMethod)
                .addPropertyValue("beanName", _beanName);


        _beanFactory.registerBeanDefinition(_beanName, builder.getBeanDefinition());
    }

    public void setScheduler(ThreadPoolTaskScheduler scheduler) {
        this.scheduler = scheduler;
    }
}
