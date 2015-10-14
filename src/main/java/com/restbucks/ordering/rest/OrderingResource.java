package com.restbucks.ordering.rest;

import com.restbucks.commandhandling.gateway.CommandGateway;
import com.restbucks.ordering.commands.MarkOrderInPreparationCommand;
import com.restbucks.ordering.commands.PlaceOrderCommand;
import com.restbucks.ordering.domain.Order;
import com.restbucks.ordering.domain.OrderRepository;
import com.restbucks.ordering.rest.assembler.OrderRepresentationAssembler;
import com.restbucks.ordering.rest.representation.OrderRepresentation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import static org.springframework.web.bind.annotation.RequestMethod.*;

@RestController
public class OrderingResource {
    @Autowired
    private CommandGateway commandGateway;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private OrderRepresentationAssembler orderRepresentationAssembler;

    @RequestMapping(value = "/order", method = POST)
    @ResponseStatus(HttpStatus.CREATED)
    protected OrderRepresentation handle(@RequestBody PlaceOrderCommand command) {

        Order order = commandGateway.send(command);

        return orderRepresentationAssembler.assemble(order);
    }

    @RequestMapping(value = "/order/{trackingId}", method = GET)
    public OrderRepresentation get(@PathVariable String trackingId) {
        return orderRepresentationAssembler.assemble(orderRepository.findByTrackingId(trackingId));
    }

    @RequestMapping(value = "/order-in-preparation/{trackingId}", method = PUT)
    @ResponseStatus(HttpStatus.OK)
    public OrderRepresentation prepare(@PathVariable String trackingId) {

        commandGateway.send(new MarkOrderInPreparationCommand(trackingId));

        return get(trackingId);
    }


}
