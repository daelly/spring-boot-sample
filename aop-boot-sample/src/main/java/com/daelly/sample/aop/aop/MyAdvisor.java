package com.daelly.sample.aop.aop;

import com.daelly.sample.aop.annotation.Advisable;
import org.springframework.aop.ClassFilter;
import org.springframework.aop.MethodMatcher;
import org.springframework.aop.support.StaticMethodMatcherPointcutAdvisor;
import org.springframework.aop.support.annotation.AnnotationMethodMatcher;
import org.springframework.core.annotation.AnnotationUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

public class MyAdvisor extends StaticMethodMatcherPointcutAdvisor {

    private final ClassFilter classFilter;

    private final MethodMatcher methodMatcher;

    public MyAdvisor() {
        this.classFilter = new AnnotationCandidateClassFilter(Advisable.class);
        this.methodMatcher = new AnnotationMethodMatcher(Advisable.class);
    }

    @Override
    public boolean matches(Method method, Class<?> targetClass) {
        return classFilter.matches(targetClass) && methodMatcher.matches(method, targetClass);
    }

    private static class AnnotationCandidateClassFilter implements ClassFilter {

        private final Class<? extends Annotation> annotationType;

        AnnotationCandidateClassFilter(Class<? extends Annotation> annotationType) {
            this.annotationType = annotationType;
        }

        @Override
        public boolean matches(Class<?> clazz) {
            return AnnotationUtils.isCandidateClass(clazz, this.annotationType);
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof AnnotationCandidateClassFilter)) {
                return false;
            }
            AnnotationCandidateClassFilter that = (AnnotationCandidateClassFilter) obj;
            return this.annotationType.equals(that.annotationType);
        }

        @Override
        public int hashCode() {
            return this.annotationType.hashCode();
        }

        @Override
        public String toString() {
            return getClass().getName() + ": " + this.annotationType;
        }

    }
}
