package com.daelly.sample.spring.boot.bean;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Bean4 {

    protected final JetConfig jetConfig;

    public Bean4(JetConfig jetConfig) {
        this.jetConfig = jetConfig;
    }

    public void printJet() {
        log.info("jetConfig is:{}", jetConfig);
    }
}
