package com.daelly.sample.aop.annotation;

import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.core.type.filter.TypeFilter;

import java.io.IOException;

public class AdvisableTypeFilter implements TypeFilter {

    @Override
    public boolean match(MetadataReader metadataReader, MetadataReaderFactory metadataReaderFactory) throws IOException {
        if (metadataReader.getAnnotationMetadata().isAnnotated(Advisable.class.getName())) {
            return true;
        }

        if (metadataReader.getAnnotationMetadata().hasAnnotatedMethods(Advisable.class.getName())) {
            return true;
        }

        return false;
    }
}
