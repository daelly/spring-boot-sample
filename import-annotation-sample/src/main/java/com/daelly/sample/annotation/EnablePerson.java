package com.daelly.sample.annotation;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.ANNOTATION_TYPE})
@Documented
@Import(PersonRegistrar.class)
public @interface EnablePerson {

    String name();

    int age();

    boolean gender();
}
