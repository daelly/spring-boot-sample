package com.daelly.sample.annotation;

import com.daelly.sample.bean.Color;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * ImportSelector的用法：<br/>
 * 1、自定义的注解，并在其中定义参数<br/>
 * 2、实现一个ImportSelector，使用1步骤的注解参数，生成需要import的类<br/>
 * 3、在1步骤的注解中@Import步骤2的ImportSelector<br/>
 * 4、使用步骤1定义的注解，并使用对应参数注解到Spring的类中，实现动态Import
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(ColorImportSelector.class)
public @interface RegisterColor {

    /**
     * 这里简单演示，直接返回需要import的类，应用中可以定义更丰富的参数，实现更丰富的功能
     *
     */
    Class<? extends Color>[] colorUsed() default {};
}
