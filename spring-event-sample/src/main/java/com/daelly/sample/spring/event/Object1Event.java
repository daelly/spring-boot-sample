package com.daelly.sample.spring.event;

import com.daelly.sample.spring.entity.Order;

public class Object1Event extends BaseEvent<Order> {

    public Object1Event(Object source) {
        super(source, new Order(111, 1111));
    }

    @Override
    public int eventId() {
        return 9999;
    }
}
