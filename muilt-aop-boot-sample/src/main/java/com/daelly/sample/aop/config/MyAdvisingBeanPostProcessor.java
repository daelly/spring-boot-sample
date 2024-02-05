package com.daelly.sample.aop.config;

import org.springframework.aop.Advisor;
import org.springframework.aop.framework.AbstractAdvisingBeanPostProcessor;

public class MyAdvisingBeanPostProcessor extends AbstractAdvisingBeanPostProcessor {

    public void setAdvisor(Advisor advisor) {
        this.advisor = advisor;
    }
}
