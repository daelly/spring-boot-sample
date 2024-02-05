package com.daelly.sample.springbeandefinition.configuration;

import com.daelly.sample.springbeandefinition.bean.CounterBean;
import com.daelly.sample.springbeandefinition.bean.CounterBean01;
import com.daelly.sample.springbeandefinition.bean.Person;
import org.springframework.context.annotation.*;

@Configuration
public class AppConfig {

    @Bean(name = "person", initMethod = "initMethod", destroyMethod = "destroyMethod")
    @Scope("singleton")
    @Lazy
    @Primary
    @Description("A bean for person")
    public Person person() {
        return new Person("daelly", 25);
    }

    @Bean(initMethod = "initMethod", destroyMethod = "destroyMethod")
    @Scope("prototype")
    @Description("A bean for person")
    public CounterBean counterBean() {
        return new CounterBean();
    }

    @Bean(name = "counterBean01", initMethod = "initMethod", destroyMethod = "destroyMethod")
    @Scope("prototype")
    @Description("A bean for person")
    public CounterBean01 counterBean01() {
        return new CounterBean01();
    }
}
