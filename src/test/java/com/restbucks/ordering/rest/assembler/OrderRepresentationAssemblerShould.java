package com.restbucks.ordering.rest.assembler;

import com.restbucks.ordering.Application;
import com.restbucks.ordering.domain.Order;
import com.restbucks.ordering.domain.OrderFixture;
import com.restbucks.ordering.rest.representation.OrderRepresentation;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@ActiveProfiles("test")
public class OrderRepresentationAssemblerShould {

    @Autowired
    private OrderRepresentationAssembler subject;

    @Test
    public void addPrepareLink_givenOrderIsPaid() {
        Order order = new OrderFixture().paid().build();

        OrderRepresentation representation = subject.toResource(order);

        assertThat(representation.getLink("self").getHref(), is("http://localhost/order/" + order.getTrackingId()));
        assertThat(representation.getLink("order-in-preparation").getHref(), is("http://localhost/order-in-preparation/" + order.getTrackingId()));
        assertThat(representation.getLinks().size(), is(2));
    }

    @Test
    public void addPrepareLink_givenOrderIsInPreparation() {
        Order order = new OrderFixture().inPreparation().build();

        OrderRepresentation representation = subject.toResource(order);

        assertThat(representation.getLink("self").getHref(), is("http://localhost/order/" + order.getTrackingId()));
        assertThat(representation.getLink("order-in-preparation").getHref(), is("http://localhost/order-in-preparation/" + order.getTrackingId()));
        assertThat(representation.getLinks().size(), is(2));
    }

    @Test
    public void addReceiptLink_givenOrderIsReady() {
        Order order = new OrderFixture().prepared().build();

        OrderRepresentation representation = subject.toResource(order);

        assertThat(representation.getLink("self").getHref(), is("http://localhost/order/" + order.getTrackingId()));
        assertThat(representation.getLink("receipt").getHref(), is("http://localhost/receipt/" + order.getTrackingId()));
        assertThat(representation.getLinks().size(), is(2));
    }
}