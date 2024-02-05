package com.daelly.sample.springbeandefinition;

import com.daelly.sample.springbeandefinition.bean.*;
import com.daelly.sample.springbeandefinition.configuration.AppConfig;
import com.daelly.sample.springbeandefinition.processor.FruitTypeSetterPostProcessor;
import com.daelly.sample.springbeandefinition.processor.OrangeRegisterPostProcessor;
import com.daelly.sample.springbeandefinition.processor.TintLabelSetterFactoryPostProcessor;
import com.daelly.sample.springbeandefinition.processor.TintLabelSetterPostProcessor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Arrays;
import java.util.Map;

/**
 * <a href="https://zhuanlan.zhihu.com/p/651334331">BeanDefinitionRegistry</a>
 */
public class CommonTests {

    /**
     * Simplest usage
     */
    @Test
    public void test1() {
        DefaultListableBeanFactory registry = new DefaultListableBeanFactory();
        BeanDefinition definition = new RootBeanDefinition(MyBean.class);
        registry.registerBeanDefinition("myBean", definition);

        MyBean myBean = registry.getBean("myBean", MyBean.class);

        myBean.doSomething();
    }

    @Test
    public void test2() {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        DefaultListableBeanFactory factory = (DefaultListableBeanFactory) context.getBeanFactory();
        BeanDefinition childBeanDefinition = factory.getBeanDefinition("childBean");
        System.out.println("Child bean definition before merge:" + childBeanDefinition);

        BeanDefinition mergedBeanDefinition = factory.getMergedBeanDefinition("childBean");
        System.out.println("Merged bean definition:" + mergedBeanDefinition);
    }

    @Test
    public void test3() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(TintLabelSetterFactoryPostProcessor.class, Blue.class, Yellow.class);
        Blue blue = context.getBean(Blue.class);
        System.out.println(blue);
    }

    @Test
    public void test4() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(TintLabelSetterPostProcessor.class, Blue.class, Yellow.class);
        Yellow yellow = context.getBean(Yellow.class);
        System.out.println(yellow);
    }

    @Test
    public void test5() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(OrangeRegisterPostProcessor.class, Apple.class);
        Apple apple = context.getBean(Apple.class);
        System.out.println(apple);
        Orange orange = context.getBean(Orange.class);
        System.out.println(orange);
    }

    @Test
    public void test6() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        BeanDefinition definition = context.getBeanFactory().getBeanDefinition("person");

        // 这里会返回null，因为person bean是通过方法定义的，很多情况下这个字段都拿不到真正的bean的class
        // 这个字段只是给你创建自己的bean的时候，传进去的参数
        System.out.println("Bean Class Name: " + definition.getBeanClassName());

        System.out.println("Bean Class Name from bean itself: " + context.getBeanFactory().getBean("person").getClass());

        System.out.println("Scope: " + definition.getScope());
        System.out.println("Is Primary: " + definition.isPrimary());
        System.out.println("Description: " + definition.getDescription());

        System.out.println("Is Lazy init: " + definition.isLazyInit());
        System.out.println("Init method: " + definition.getInitMethodName());
        System.out.println("Destroy method: " + definition.getDestroyMethodName());

        System.out.println("Parent bean name: " + definition.getParentName());
        System.out.println("Depends on: " + Arrays.toString(definition.getDependsOn()));

        System.out.println("Constructor argument values: " + definition.getConstructorArgumentValues());
        System.out.println("Property values: " + definition.getPropertyValues());

        context.close();
    }

    @Test
    public void test7() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        Map<String, CounterBean> beans = context.getBeansOfType(CounterBean.class);
        System.out.println("beans = " + beans);
        for (int i = 0; i < 10; i++) {
            CounterBean counterBean = context.getBean(CounterBean.class);
            System.out.println("[" + counterBean + "]'s counter: " + counterBean.getCounter());
        }

        beans = context.getBeansOfType(CounterBean.class);
        System.out.println("beans = " + beans);

        context.close();
    }

    @Test
    public void test8() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        Map<String, CounterBean01> beans = context.getBeansOfType(CounterBean01.class);
        System.out.println("beans = " + beans);

        for (int i = 0; i < 10; i++) {
            CounterBean01 counterBean01 = context.getBean("counterBean01", CounterBean01.class);
            System.out.println("[" + counterBean01 + "]'s counter: " + counterBean01.getCounter());
        }

        context.close();
    }

    @Test
    public void test9 () {
        BeanDefinition definition = new RootBeanDefinition(Book.class);

        definition.setAttribute("myAttr", "a value");

        if (definition.hasAttribute("myAttr")) {
            System.out.println("myAttr = " + definition.getAttribute("myAttr"));

            definition.removeAttribute("myAttr");
            System.out.println("myAttr = " + definition.getAttribute("myAttr"));
        }

        System.out.println("has title attr: " + definition.hasAttribute("title"));
    }
}
