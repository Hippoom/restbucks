package com.restbucks.ordering.commandgateway;

import org.springframework.stereotype.Component;

@Component
public class CommandGateway {
    public <T> T send(Object command) {
        return null;
    }
}
