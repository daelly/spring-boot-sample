package com.daelly.sample.spring.event;

import org.springframework.context.PayloadApplicationEvent;

public abstract class BaseEvent<T> extends PayloadApplicationEvent<T> {

    public abstract int eventId();

    public BaseEvent(Object source, T payload) {
        super(source, payload);
    }
}
