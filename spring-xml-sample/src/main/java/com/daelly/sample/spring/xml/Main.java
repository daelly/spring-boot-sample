package com.daelly.sample.spring.xml;

import com.daelly.sample.spring.xml.bean.CustomBean;
import com.daelly.sample.spring.xml.service.CartService;
import com.daelly.sample.spring.xml.service.UserService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Main {

    public static void main(String[] args) {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:application-context.xml");
//        CustomBean bean1 = applicationContext.getBean("bean1", CustomBean.class);
//        System.out.println(bean1.getName());

        UserService userService = applicationContext.getBean("userService", UserService.class);
        userService.add();

        CartService cartService = applicationContext.getBean("cartService", CartService.class);
        cartService.remove();
    }
}
