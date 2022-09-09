package com.daelly.sample.aop.interceptor;

import com.daelly.sample.aop.execution.ExecuteCallback;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;

public class AdviseMethodInterceptor implements MethodInterceptor, BeanFactoryAware {

    private BeanFactory _beanFactory;

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        Object result = null;
        try {
            Object[] args = invocation.getArguments();
            for (int i = 0; i < args.length; i++) {
                System.out.println("method arguments:" + args[i]);
            }

            System.out.println("invocation subject:" + invocation.getThis());
            System.out.println("invocation method:" + invocation.getMethod());

            System.out.println("before execution");
            result = invocation.proceed();
            System.out.println("after execution");

            System.out.println("invocation result:" + result);

            ExecuteCallback bean = _beanFactory.getBean(ExecuteCallback.class);
            System.out.println(bean.toString());
        } catch (Exception e) {
            System.out.println("invocation exception:" + e);
        }
        return result;
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this._beanFactory = beanFactory;
    }
}
