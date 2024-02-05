package com.daelly.sample.spring.boot.bean;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;
import java.util.Set;

@Data
@ConfigurationProperties("custom.jet")
public class JetConfig {

    protected int weight;

    protected int length;

    protected String name;

    protected Set<Integer> sets;

    protected Taobo taobo;

    @Data
    public static class Taobo {
        protected int price;

        protected int count;

        protected int point;

        protected List<String> sales;
    }
}
