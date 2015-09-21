package com.restbucks.ordering.domain;

public interface OrderRepository {
    String nextTrackingId();

    void store(Order order);

    Order findByTrackingId(String trackingId);
}
