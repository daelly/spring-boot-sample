package com.daelly.sample.aop.method;

public interface HandlerMethod {

    default Object[] decode(Object... args) {
        return args;
    }

    Object invoke(Object... args) throws Exception;
}
