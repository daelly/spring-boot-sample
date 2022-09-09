package com.daelly.sample.aop;

import com.daelly.sample.aop.service.HelloService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class AopBootApplicationTests {

    @Autowired
    private HelloService helloService;

    @Test
    public void testHello() {
        System.out.println(helloService.hello("daelly"));

        System.out.println(helloService.printExecutionBean());
    }
}
