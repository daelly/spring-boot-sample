package com.daelly.sample.spring.boot;

import com.daelly.sample.spring.boot.bean.Bean3;
import com.daelly.sample.spring.boot.bean.Bean4;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class SimpleBootApplicationTests {

    @Autowired
    private Bean3 firstBean3;

    @Autowired
    private Bean4 bean4;

    @Test
    public void test1() {
        firstBean3.trying();
    }

    @Test
    public void test2() {
        bean4.printJet();
    }
}
