package com.restbucks.ordering.commands;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
public class PlaceOrderCommand {
    private String customer;
    private String location;
    private List<Item> items = new ArrayList<>();

    public PlaceOrderCommand(String location, List<Item> items) {
        this.location = location;
        this.items = items;
    }

    @NoArgsConstructor
    @Getter
    public static class Item {
        private String name;
        private int quantity;
        private String milk;
        private String size;

        public Item(String name, int quantity, String milk, String size) {
            this.name = name;
            this.quantity = quantity;
            this.milk = milk;
            this.size = size;
        }
    }

}
