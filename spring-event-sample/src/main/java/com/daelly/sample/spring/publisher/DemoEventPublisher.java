package com.daelly.sample.spring.publisher;

import com.daelly.sample.spring.event.Object2Event;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class DemoEventPublisher implements ApplicationEventPublisherAware {

    private ApplicationEventPublisher publisher;

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.publisher = applicationEventPublisher;
    }

    @PostConstruct
    public void sendEvent() {
        publisher.publishEvent(new Object2Event(this));
    }
}
