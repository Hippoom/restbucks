package com.restbucks.ordering.commandhandling;

import com.restbucks.commandhandling.annotation.CommandHandler;
import com.restbucks.ordering.commands.MarkOrderInPreparationCommand;
import com.restbucks.ordering.commands.MarkOrderPreparedCommand;
import com.restbucks.ordering.domain.Order;
import com.restbucks.ordering.domain.OrderRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Transactional
@Component
public class PrepareOrderCommandHandler {

    @Resource
    private OrderRepository orderRepository;

    @CommandHandler
    public void handle(MarkOrderInPreparationCommand command) {
        Order order = orderRepository.findByTrackingId(command.getTrackingId());
        order.markInPreparation();
    }

    @CommandHandler
    public void handle(MarkOrderPreparedCommand command) {
        Order order = orderRepository.findByTrackingId(command.getTrackingId());
        order.markPrepared();
    }
}
