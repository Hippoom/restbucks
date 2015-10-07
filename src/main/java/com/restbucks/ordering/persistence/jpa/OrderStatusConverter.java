package com.restbucks.ordering.persistence.jpa;

import com.restbucks.ordering.domain.Order;

import javax.persistence.AttributeConverter;

public class OrderStatusConverter implements AttributeConverter<Order.Status, String> {
    @Override
    public String convertToDatabaseColumn(Order.Status attribute) {
        return attribute.getValue();
    }

    @Override
    public Order.Status convertToEntityAttribute(String dbData) {
        return Order.Status.of(dbData);
    }
}
