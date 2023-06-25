package com.daelly.sample.aop;

import com.daelly.sample.aop.configurer.SampleComponent;
import com.daelly.sample.aop.service.HelloService;
import com.daelly.sample.aop.service.TestService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class AopBootApplicationTests {

    @Autowired
    private HelloService helloService;

    @Autowired
    private TestService testService;

    @Autowired
    private SampleComponent sampleComponent;

    @Test
    public void testHello() throws Exception {
        System.out.println(helloService.hello("daelly"));

//        System.out.println(helloService.printExecutionBean());

        System.out.println(testService.test("ripper"));

        Thread.sleep(20000);
    }

    @Test
    public void testConfigurer() {
        System.out.println(sampleComponent.getName());
        System.out.println(sampleComponent.getAge());
    }
}
