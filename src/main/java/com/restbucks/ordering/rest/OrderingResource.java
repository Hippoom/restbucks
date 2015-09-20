package com.restbucks.ordering.rest;

import com.restbucks.ordering.commandgateway.CommandGateway;
import com.restbucks.ordering.commands.PlaceOrderCommand;
import com.restbucks.ordering.domain.Order;
import com.restbucks.ordering.rest.assembler.OrderRepresentationAssembler;
import com.restbucks.ordering.rest.representation.OrderRepresentation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
@RequestMapping("/orders")
public class OrderingResource {
    @Autowired
    private CommandGateway commandGateway;
    @Autowired
    private OrderRepresentationAssembler orderRepresentationAssembler;

    @RequestMapping(method = POST)
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    protected OrderRepresentation handle(@RequestBody PlaceOrderCommand command) {

        Order order = commandGateway.send(command);

        return orderRepresentationAssembler.assemble(order);
    }


}
