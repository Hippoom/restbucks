package com.restbucks.ordering.commandhandling;

import com.restbucks.ordering.commands.MakePaymentCommand;
import com.restbucks.ordering.commands.PlaceOrderCommand;
import com.restbucks.ordering.domain.Payment;
import com.restbucks.ordering.domain.PaymentRepository;
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

public class MakePaymentCommandHandlerShould {

    @InjectMocks
    private MakePaymentCommandHandler subject = new MakePaymentCommandHandler();

    @Mock
    private PaymentRepository paymentRepository;

    private final ArgumentCaptor<Payment> payment = ArgumentCaptor.forClass(Payment.class);

    @Before
    public void injects() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void createPayment_whenMakePaymentCommand() {
        Payment payment = subject.handle(new MakePaymentCommand("1", 1.3));

        assertThat(payment.getId(), is("1"));
        assertThat(payment.getAmount(), is(1.3));

        verify(paymentRepository).store(this.payment.capture());

        assertThat(this.payment.getValue(), is(sameInstance(payment)));
    }

}