package com.daelly.sample.aop.configurer;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public abstract class AbstractSampleComponentConfiguration {

    Supplier<String> name;

    Supplier<Integer> age;

    @Autowired
    public void setConfigurer(ObjectProvider<SampleComponentConfigurer> configurers) {
        List<SampleComponentConfigurer> configurerList = configurers.stream().collect(Collectors.toList());

        Supplier<SampleComponentConfigurer> supplier = () -> {
            if (CollectionUtils.isEmpty(configurerList)) {
                return null;
            }

            if (configurerList.size() > 1) {
                throw new IllegalStateException("只允许指定一个 SampleComponentConfigurer");
            }

            return configurerList.get(0);
        };

        this.name = adapt(supplier, SampleComponentConfigurer::getName);
        this.age = adapt(supplier, SampleComponentConfigurer::getAge);
    }

    private <T> Supplier<T> adapt(Supplier<SampleComponentConfigurer> supplier, Function<SampleComponentConfigurer, T> function) {
        return () -> {
            SampleComponentConfigurer sampleComponentConfigurer = supplier.get();
            return sampleComponentConfigurer == null ? null : function.apply(sampleComponentConfigurer);
        };
    }
}
