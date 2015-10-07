package com.restbucks.ordering.commandhandling;

import com.restbucks.commandhandling.annotation.CommandHandler;
import com.restbucks.ordering.commands.MakePaymentCommand;
import com.restbucks.ordering.domain.Payment;
import com.restbucks.ordering.domain.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class MakePaymentCommandHandler {

    @Autowired
    private PaymentRepository paymentRepository;

    @Transactional
    @CommandHandler
    public Payment handle(MakePaymentCommand command) {
        Payment payment = new Payment(command.getOrderId(), command.getAmount());
        paymentRepository.store(payment);
        return payment;
    }


}
