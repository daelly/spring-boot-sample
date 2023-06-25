package com.daelly.sample.aop.interceptor;

import com.daelly.sample.aop.execution.ExecuteCallback;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.aop.ProxyMethodInvocation;
import org.springframework.aop.interceptor.ExposeBeanNameAdvisors;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

import java.util.Date;

public class AdviseMethodInterceptor implements MethodInterceptor, BeanFactoryAware {

    private ThreadPoolTaskScheduler scheduler;

    private BeanFactory _beanFactory;

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        System.out.println("bean=======----------------------------------------------------------------" + invocation.getThis());
        Object result = null;
        if (invocation instanceof ProxyMethodInvocation) {
            ProxyMethodInvocation proxyMethodInvocation = (ProxyMethodInvocation) invocation;
            String beanName = (String) proxyMethodInvocation.getUserAttribute(ExposeBeanNameAdvisors.class.getName() + ".BEAN_NAME");
            System.out.println("------------------------------------------------------------------beanName:" + beanName);

        }
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

            final ExecuteCallback callback = _beanFactory.getBean(ExecuteCallback.class);
            scheduler.schedule(() -> {
                callback.execute((String) invocation.getArguments()[0]);
            }, DateUtils.addSeconds(new Date(), 5));
        } catch (Exception e) {
            System.out.println("invocation exception:" + e);
        }
        return result;
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this._beanFactory = beanFactory;
    }

    public void setScheduler(ThreadPoolTaskScheduler scheduler) {
        this.scheduler = scheduler;
    }
}
