package com.daelly.sample.annotation;

import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;

import java.util.Arrays;
import java.util.Map;

public class ColorImportSelector implements ImportSelector {
    @Override
    public String[] selectImports(AnnotationMetadata importingClassMetadata) {
        // 获取注解的属性
        Map<String, Object> annotationAttrs = importingClassMetadata.getAnnotationAttributes(RegisterColor.class.getName(), true);
        String[] colorUsed = (String[]) annotationAttrs.get("colorUsed");
        System.out.println(Arrays.toString(colorUsed));

        // 返回需要import的类名
        return colorUsed;
    }
}
