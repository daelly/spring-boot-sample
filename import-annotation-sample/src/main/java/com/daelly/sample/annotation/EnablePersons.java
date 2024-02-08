package com.daelly.sample.annotation;

import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface EnablePersons {

    EnablePerson[] value();
}
