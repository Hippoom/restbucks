package com.restbucks.ordering.domain;

public interface PaymentRepository {
    void store(Payment payment);

    Payment findById(String id);
}
