package com.restbucks.ordering.domain;

import static java.lang.String.format;

public class NoSuchProductException extends RuntimeException {
    public NoSuchProductException(String itemName, String size, Throwable cause) {
        super(format("Cannot find product with name[%s] and size[%s]", itemName, size), cause);
    }
}
