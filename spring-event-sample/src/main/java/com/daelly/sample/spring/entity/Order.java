package com.daelly.sample.spring.entity;

import lombok.Data;

@Data
public class Order {

    protected long orderId;

    protected long price;

    public Order() {
    }

    public Order(long orderId, long price) {
        this.orderId = orderId;
        this.price = price;
    }
}
