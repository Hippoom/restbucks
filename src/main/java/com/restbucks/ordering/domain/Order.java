package com.restbucks.ordering.domain;

import com.restbucks.ordering.persistence.jpa.OrderStatusConverter;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static java.util.Arrays.asList;

@Entity
@Table(name = "t_order")
@Getter
@NoArgsConstructor
public class Order {
    @Id
    @Column(name = "id")
    private String trackingId;
    @Version
    private int version;
    private String customer;
    private String location;
    @Convert(converter = OrderStatusConverter.class)
    private Status status;
    private double cost;

    @ElementCollection
    @CollectionTable(name = "t_order_item",
            joinColumns = {@JoinColumn(name = "order_id")})
    private List<Item> items = new ArrayList<>();

    public Order(String trackingId) {
        this.trackingId = trackingId;
        this.status = Status.PAYMENT_EXPECTED;
    }

    public void locationIs(String location) {
        this.location = location;
    }

    public void append(List<Item> items) {
        this.items.addAll(items);
        this.cost = this.items.stream()
                .map(i -> i.subtotal())
                .reduce(0.0, (accumulator, _item) -> accumulator + _item);
    }

    public void append(Item... items) {
        append(asList(items));
    }

    public void customerIs(String name) {
        this.customer = name;
    }

    public void paidWith(double amount) {
        this.status = Status.PAID;
    }

    public boolean is(Status status) {
        return getStatus() == status;
    }

    public void markInPreparation() {
        this.status = Status.PREPARING;
    }

    public void markPrepared() {
        this.status = Status.READY;
    }

    public void markTaken() {
        this.status = Status.TAKEN;
    }

    @NoArgsConstructor
    @EqualsAndHashCode
    @ToString
    @Getter
    @Embeddable
    public static class Item {
        private String name;
        private int quantity;
        private String milk;
        private String size;
        private double price;

        public Item(String name, int quantity, String milk, String size, double price) {
            this.name = name;
            this.quantity = quantity;
            this.milk = milk;
            this.size = size;
            this.price = price;
        }

        public double subtotal() {
            return getQuantity() * getPrice();
        }
    }

    @Getter
    public enum Status {
        UNKNOWN("unknown"),
        PAYMENT_EXPECTED("payment-expected"),
        PAID("paid"),
        PREPARING("preparing"),
        READY("ready"),
        TAKEN("taken");

        private String value;

        Status(String value) {
            this.value = value;
        }


        public static Status of(String value) {
            for (Status candidate : values()) {
                if (candidate.getValue().equals(value)) {
                    return candidate;
                }
            }
            return UNKNOWN;
        }
    }
}
