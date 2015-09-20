package com.restbucks.ordering.commands;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class PlaceOrderCommand {
    private String location;
    private List<Item> items = new ArrayList<>();

    @Getter
    public static class Item {
        private String name;
        private int quantity;
        private String milk;
        private String size;
    }

}
