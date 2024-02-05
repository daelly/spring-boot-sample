package com.daelly.sample.spring.boot;

import com.daelly.sample.spring.boot.initializer.TestApplicationContextInitializer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringExtendApplication {

    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(SpringExtendApplication.class);
        application.addInitializers(new TestApplicationContextInitializer());
        application.run(args);
    }
}
