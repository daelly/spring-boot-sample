package com.daelly.sample.aop.codec;

import org.springframework.core.MethodParameter;

public interface ParameterCodec {

    ParameterCodec DEFAULT_CODEC = new DefaultParameterCodec();

    String encode(MethodParameter[] parameters, Object[] args);


    Object[] decode(MethodParameter[] parameters, String content);
}
