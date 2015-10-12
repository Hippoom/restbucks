package com.restbucks.ordering.rest.assembler;

import com.restbucks.ordering.Application;
import com.restbucks.ordering.domain.Order;
import com.restbucks.ordering.domain.OrderFixture;
import com.restbucks.ordering.rest.representation.OrderRepresentation;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@ActiveProfiles("test")
public class OrderRepresentationAssemblerShould {

    @Autowired
    private OrderRepresentationAssembler subject;

    private MockHttpServletRequest request = new MockHttpServletRequest();

    @Test
    public void addPrepareLink_givenOrderIsPaid() {
        Order order = new OrderFixture().build();
        order.paidWith(order.getCost());

        OrderRepresentation representation = subject.toResource(order);

        assertThat(representation.getLink("self").getHref(), is("http://localhost/order/" + order.getTrackingId()));
        assertThat(representation.getLinks().size(), is(1));
    }
}