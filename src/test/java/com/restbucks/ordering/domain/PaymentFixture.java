package com.restbucks.ordering.domain;

public class PaymentFixture {
    private Payment payment;

    public PaymentFixture(Order order) {
        this.payment = new Payment(order.getTrackingId(), 2.4);
    }

    public Payment build() {
        return payment;
    }
}
