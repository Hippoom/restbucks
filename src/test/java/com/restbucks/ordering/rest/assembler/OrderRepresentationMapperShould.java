package com.restbucks.ordering.rest.assembler;

import com.restbucks.ordering.domain.Order;
import com.restbucks.ordering.domain.OrderFixture;
import com.restbucks.ordering.rest.OrderingResource;
import com.restbucks.ordering.rest.representation.OrderRepresentation;
import org.junit.Test;

import java.util.stream.IntStream;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class OrderRepresentationMapperShould {

    private OrderRepresentationMapper subject =
            new OrderRepresentationMapper();

    @Test
    public void convertOrderToNoneLinkOrderRepresentation() {
        Order model = new OrderFixture().build();

        OrderRepresentation representation = subject.from(model);

        assertThat(representation.trackingId, is(model.getTrackingId()));
        assertThat(representation.customer, is(model.getCustomer()));
        assertThat(representation.location, is(model.getLocation()));
        assertThat(representation.cost, is(model.getCost()));
        assertThat(representation.status, is(model.getStatus().getValue()));
        assertThat(representation.items.size(), is(model.getItems().size()));
        IntStream.range(0, representation.items.size())
                .forEach(idx -> {
                    assertThat(representation.items.get(idx).name,
                            is(model.getItems().get(idx).getName())
                    );
                    assertThat(representation.items.get(idx).quantity,
                            is(model.getItems().get(idx).getQuantity())
                    );
                    assertThat(representation.items.get(idx).milk,
                            is(model.getItems().get(idx).getMilk())
                    );
                    assertThat(representation.items.get(idx).size,
                            is(model.getItems().get(idx).getSize())
                    );
                });
    }

}