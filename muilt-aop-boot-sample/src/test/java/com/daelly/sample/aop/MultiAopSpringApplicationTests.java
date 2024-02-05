package com.daelly.sample.aop;

import com.daelly.sample.aop.service.TestService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class MultiAopSpringApplicationTests {

    @Autowired
    private TestService testService;


    @Test
    public void test1() {
        testService.greet("Daelly");
    }
}
