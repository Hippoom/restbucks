package com.restbucks.ordering.commandhandling;

import com.restbucks.commandhandling.annotation.CommandHandler;
import com.restbucks.ordering.commands.TakeReceiptCommand;
import com.restbucks.ordering.domain.Order;
import com.restbucks.ordering.domain.OrderRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Component
@Transactional
public class TakeReceiptCommandHandler {
    @Resource
    private OrderRepository orderRepository;

    @CommandHandler
    public void handle(TakeReceiptCommand command) {
        Order order = orderRepository.findByTrackingId(command.getTrackingId());
        order.markTaken();
    }
}
