package com.restbucks.ordering.commandhandling;

import com.restbucks.commandhandling.gateway.CommandGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

@Configuration
public class CommandHandlingConfig {

    @Autowired
    private OrderingCommandHandler orderingCommandHandler;

    @Autowired
    private MakePaymentCommandHandler makePaymentCommandHandler;

    @Autowired
    private CommandGateway commandGateway;

    @PostConstruct
    public void register() {
        commandGateway.register(orderingCommandHandler);
        commandGateway.register(makePaymentCommandHandler);
    }

}
