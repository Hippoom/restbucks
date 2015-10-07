package com.restbucks.ordering.rest;

import com.github.hippoom.resthelper.annotation.Command;
import com.restbucks.commandhandling.gateway.CommandGateway;
import com.restbucks.ordering.commands.MakePaymentCommand;
import com.restbucks.ordering.domain.Payment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.ExposesResourceFor;
import org.springframework.hateoas.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.PUT;

@ExposesResourceFor(Payment.class)
@RestController
@RequestMapping("/payment")
public class PaymentResource {

    @Autowired
    private CommandGateway commandGateway;

    @Autowired
    private PaymentResourceProcessor paymentResourceProcessor;

    @RequestMapping(value = "/{orderId}", method = PUT)
    @ResponseStatus(HttpStatus.CREATED)
    public Resource<Payment> handle(@Command MakePaymentCommand command) {

        Payment payment = commandGateway.send(command);

        return paymentResourceProcessor.process(new Resource<>(payment));
    }

    @RequestMapping(value = "/{orderId}", method = GET)
    public Resource<Payment> get(@PathVariable String orderId) {
        return null;
    }
}
