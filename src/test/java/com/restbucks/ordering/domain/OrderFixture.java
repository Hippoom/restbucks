package com.restbucks.ordering.domain;

public class OrderFixture {

    private final Order order;

    public OrderFixture(String trackingId) {
        order = new Order(trackingId);
    }

    public OrderFixture() {
        this("1234");
    }

    public OrderFixture paid() {
        order.paidWith(order.getCost());
        return this;
    }

    public Order build() {
        order.customerIs("Doe");
        order.locationIs("takeAway");
        order.append(new Order.Item());
        return order;
    }

}
