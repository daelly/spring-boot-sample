package com.daelly.sample.spring.event;

import com.daelly.sample.spring.entity.Order;

public class Object2Event extends BaseEvent<Order> {

    public Object2Event(Object source) {
        super(source, new Order(222, 22222));
    }

    @Override
    public int eventId() {
        return 8888;
    }
}
