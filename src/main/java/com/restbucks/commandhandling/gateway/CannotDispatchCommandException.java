package com.restbucks.commandhandling.gateway;

import static java.lang.String.format;

public class CannotDispatchCommandException extends RuntimeException {
    public CannotDispatchCommandException(Object command, ReflectiveOperationException e) {
        super(format("Cannot dispatch command %s, caused by %s", command, e.getMessage()), e);
    }
}
