package com.restbucks.ordering.commandhandling;

import com.restbucks.ordering.commands.PlaceOrderCommand;
import com.restbucks.ordering.domain.Order;
import com.restbucks.ordering.domain.OrderRepository;
import com.restbucks.ordering.domain.ProductCatalogService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static com.restbucks.ordering.domain.Order.Status.PAYMENT_EXPECTED;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


public class PlaceOrderCommandHandlerTest {

    @InjectMocks
    private PlaceOrderCommandHandler subject = new PlaceOrderCommandHandler();

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private ProductCatalogService productCatalogService;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void itShouldCreateNewOrder_whenHandlingPlaceOrderCommand() {
        List<PlaceOrderCommand.Item> items = new ArrayList<>();
        items.add(new PlaceOrderCommand.Item("cappuccino", 1, "semi", "large"));
        items.add(new PlaceOrderCommand.Item("latte", 2, "slim", "small"));
        PlaceOrderCommand command = new PlaceOrderCommand("Doe", "takeAway", items);

        when(orderRepository.nextTrackingId()).thenReturn("1");
        when(productCatalogService.evaluate("cappuccino", "large"))
                .thenReturn(2.0);
        when(productCatalogService.evaluate("latte", "small"))
                .thenReturn(1.5);

        Order order = subject.handle(command);

        verify(orderRepository).store(order);

        assertThat(order.getTrackingId(), is("1"));
        assertThat(order.getCustomer(), is(command.getCustomer()));
        assertThat(order.getLocation(), is(command.getLocation()));
        assertThat(order.getStatus(), is(PAYMENT_EXPECTED));
        assertThat(order.getCost(), is(5.0));
        assertThat(order.getItems(), contains(
                new Order.Item("cappuccino", 1, "semi", "large", 2.0),
                new Order.Item("latte", 2, "slim", "small", 1.5)));
    }
}
