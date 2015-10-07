package com.restbucks.ordering.rest.representation;

import org.springframework.hateoas.ResourceSupport;

import java.util.ArrayList;
import java.util.List;

public class OrderRepresentation extends ResourceSupport {
    public String trackingId;
    public String customer;
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
