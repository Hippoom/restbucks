package com.restbucks.ordering.commandhandling;

import com.restbucks.ordering.commands.TakeReceiptCommand;
import com.restbucks.ordering.domain.Order;
import com.restbucks.ordering.domain.OrderFixture;
import com.restbucks.ordering.domain.OrderRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;


public class TakeReceiptCommandHandlerShould {

    @InjectMocks
    private TakeReceiptCommandHandler subject = new TakeReceiptCommandHandler();

    @Mock
    private OrderRepository orderRepository;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void markOrderTaken() {
        Order order = new OrderFixture().paid().build();

        when(orderRepository.findByTrackingId(order.getTrackingId())).
                thenReturn(order);

        subject.handle(new TakeReceiptCommand(order.getTrackingId()));

        assertThat(order.getStatus(), is(Order.Status.TAKEN));
    }
}
