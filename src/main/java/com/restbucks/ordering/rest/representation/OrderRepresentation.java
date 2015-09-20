package com.restbucks.ordering.rest.representation;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

public class OrderRepresentation {
    @JsonProperty("tracking_id")
    public String trackingId;
    public String location;
    public double cost;
    public String status;
    public List<Item> items = new ArrayList<>();

    public static class Item {
        public String name;
        public int quantity;
        public String milk;
        public String size;
    }
}
