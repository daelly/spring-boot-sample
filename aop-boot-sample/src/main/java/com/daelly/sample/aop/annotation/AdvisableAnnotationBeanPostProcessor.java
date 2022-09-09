package com.daelly.sample.aop.annotation;

import com.daelly.sample.aop.execution.ExecuteCallback;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanClassLoaderAware;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ResourceLoader;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ReflectionUtils;
import org.springframework.util.StringUtils;

import java.lang.reflect.Method;
import java.util.*;

@Slf4j
public class AdvisableAnnotationBeanPostProcessor implements BeanDefinitionRegistryPostProcessor, EnvironmentAware, ResourceLoaderAware, BeanClassLoaderAware {

    private final Set<String> packagesToScan;

    private Environment environment;

    private ResourceLoader resourceLoader;

    private ClassLoader classLoader;

    private BeanDefinitionRegistry registry;

    public AdvisableAnnotationBeanPostProcessor(String... packagesToScan) {
        this(Arrays.asList(packagesToScan));
    }

    public AdvisableAnnotationBeanPostProcessor(Collection<String> packagesToScan) {
        this(new LinkedHashSet<>(packagesToScan));
    }

    public AdvisableAnnotationBeanPostProcessor(Set<String> packagesToScan) {
        this.packagesToScan = packagesToScan;
    }

    @Override
    public void setBeanClassLoader(ClassLoader classLoader) {
        this.classLoader = classLoader;
    }

    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
        log.info("AdvisableAnnotationBeanPostProcessor registry:{}", registry);
        this.registry = registry;
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        Set<String> resolvedPackagesToScan = resolvePackagesToScan(packagesToScan);
        if (!CollectionUtils.isEmpty(resolvedPackagesToScan)) {
            ClassPathScanningCandidateComponentProvider provider = new ClassPathScanningCandidateComponentProvider(false, environment);
            provider.setResourceLoader(resourceLoader);
            provider.addIncludeFilter(new AdvisableTypeFilter());

            for (String pts : resolvedPackagesToScan) {
                Set<BeanDefinition> beanDefinitions = provider.findCandidateComponents(pts);
                if (CollectionUtils.isEmpty(beanDefinitions)) {
                    continue;
                }

                for (BeanDefinition beanDefinition : beanDefinitions) {
                    log.info("RetryAnnotationBeanPostProcessor found bean:{}, source:{}", beanDefinition.getBeanClassName(), beanDefinition.getSource());
                    Class<?> clazz;
                    try {
                        clazz = classLoader.loadClass(beanDefinition.getBeanClassName());
                    } catch (ClassNotFoundException e) {
                        log.error("cannot load class:{}", beanDefinition.getBeanClassName(), e);
                        continue;
                    }

                    Advisable clazzAnnotation = AnnotationUtils.findAnnotation(clazz, Advisable.class);

                    Method[] methods = ReflectionUtils.getDeclaredMethods(clazz);
                    if (clazzAnnotation == null) {
                        methods = Arrays.stream(methods).filter(method -> AnnotationUtils.findAnnotation(method, Advisable.class) != null).toArray(Method[]::new);
                    }

                    if (ArrayUtils.isEmpty(methods)) {
                        continue;
                    }

                    for (Method method : methods) {
                        registerRetryableMethodBeans(beanFactory, clazz, method);
                    }
                }
            }
        }
    }

    @Override
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }

    @Override
    public void setResourceLoader(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    private Set<String> resolvePackagesToScan(Set<String> packagesToScan) {
        Set<String> resolvedPackagesToScan = new LinkedHashSet<String>(packagesToScan.size());
        for (String packageToScan : packagesToScan) {
            if (StringUtils.hasText(packageToScan)) {
                String resolvedPackageToScan = environment.resolvePlaceholders(packageToScan.trim());
                resolvedPackagesToScan.add(resolvedPackageToScan);
            }
        }
        return resolvedPackagesToScan;
    }

    private void registerRetryableMethodBeans(ConfigurableListableBeanFactory beanFactory, Class<?> clazz, Method method) {
        log.info("AdvisableAnnotationBeanPostProcessor method declaring class:{}", method.getDeclaringClass());
        Map<String, ?> beans = beanFactory.getBeansOfType(clazz);
        if (MapUtils.isEmpty(beans)) {
            return;
        }

        for (String beanName : beans.keySet()) {
            String executeBeanName = clazz.getName() + "#" + method.getName() + ":" + beanName;
            BeanDefinitionBuilder builder = BeanDefinitionBuilder.rootBeanDefinition(ExecuteCallback.class);
            builder.addConstructorArgValue(beans.get(beanName));
            builder.addConstructorArgValue(method);
            registry.registerBeanDefinition(executeBeanName, builder.getBeanDefinition());
        }
    }
}
