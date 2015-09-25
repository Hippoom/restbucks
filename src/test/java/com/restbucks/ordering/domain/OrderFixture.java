package com.restbucks.ordering.domain;

public class OrderFixture {
    public Order build() {
        Order order = new Order("1234");
        order.append(new Order.Item());
        return order;
    }
}
