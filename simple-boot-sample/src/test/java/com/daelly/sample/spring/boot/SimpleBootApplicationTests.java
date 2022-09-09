package com.daelly.sample.spring.boot;

import com.daelly.sample.spring.boot.bean.Bean3;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class SimpleBootApplicationTests {

    @Autowired
    private Bean3 firstBean3;

    @Test
    public void test() {
        firstBean3.trying();
    }
}
