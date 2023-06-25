package com.daelly.sample.exception.handle.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ExceptionHandleAdvice {

    @ExceptionHandler(Throwable.class)
    public String handleException(Throwable t) {
        log.error("handleException caught:", t);

        return t.getMessage();
    }
}
