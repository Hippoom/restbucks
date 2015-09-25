package com.restbucks.ordering.rest.representation;

import org.springframework.hateoas.Link;

public class OrderRepresentationFixture {
    private OrderRepresentation representation = new OrderRepresentation();

    public OrderRepresentationFixture() {
        enrichDefaultValues();
        representation.items.add(defaultItem());
        representation.add(new Link("http://localhost:8080/order/" + representation.trackingId));
    }

    private void enrichDefaultValues() {
        representation.trackingId = "1234";
        representation.location = "takeAway";
        representation.cost = 2.0;
        representation.status = "pending";
    }

    private OrderRepresentation.Item defaultItem() {
        OrderRepresentation.Item item = new OrderRepresentation.Item();
        item.name = "cappuccino";
        item.quantity = 1;
        item.milk = "semi";
        item.size = "large";
        return item;
    }

    public OrderRepresentation build() {
        return representation;
    }
}
