package com.restbucks.ordering.commandhandling;

import com.restbucks.ordering.commands.MakePaymentCommand;
import com.restbucks.ordering.commands.PlaceOrderCommand;
import com.restbucks.ordering.domain.*;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.sameInstance;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class MakePaymentCommandHandlerShould {

    @InjectMocks
    private MakePaymentCommandHandler subject = new MakePaymentCommandHandler();

    @Mock
    private PaymentRepository paymentRepository;

    @Mock
    private OrderRepository orderRepository;

    private final ArgumentCaptor<Payment> payment = ArgumentCaptor.forClass(Payment.class);

    @Before
    public void injects() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void createPayment_whenMakePaymentCommand() {

        String orderId = "1";
        Order order = new OrderFixture(orderId).build();

        when(orderRepository.findByTrackingId(orderId)).thenReturn(order);

        Payment payment = subject.handle(new MakePaymentCommand(orderId, 1.3));

        assertThat(payment.getId(), is(orderId));
        assertThat(payment.getAmount(), is(1.3));

        verify(paymentRepository).store(this.payment.capture());

        assertThat(this.payment.getValue(), is(sameInstance(payment)));

        assertThat(order.getStatus(), is(Order.Status.PAID));
    }

}