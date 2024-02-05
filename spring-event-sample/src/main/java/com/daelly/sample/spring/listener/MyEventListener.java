package com.daelly.sample.spring.listener;

import com.daelly.sample.spring.event.BaseEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class MyEventListener implements ApplicationListener<BaseEvent<Object>> {

    @Override
    public void onApplicationEvent(BaseEvent<Object> event) {
        log.info("myEventListener receive event with id: {}", event.eventId());
    }
}
