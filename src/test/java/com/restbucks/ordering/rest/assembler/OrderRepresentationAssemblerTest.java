package com.restbucks.ordering.rest.assembler;

import com.restbucks.ordering.domain.Order;
import com.restbucks.ordering.domain.OrderFixture;
import com.restbucks.ordering.rest.representation.OrderRepresentation;
import org.junit.Test;

import java.util.stream.IntStream;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class OrderRepresentationAssemblerTest {

    private OrderRepresentationAssembler subject = new OrderRepresentationAssembler();

    @Test
    public void whenAssembleRepresentation() {
        Order model = new OrderFixture().build();

        OrderRepresentation representation = subject.assemble(model);

        assertThat(representation.trackingId, is(model.getTrackingId()));
        assertThat(representation.location, is(model.getLocation()));
        assertThat(representation.cost, is(model.getCost()));
        assertThat(representation.status, is(model.getStatus()));
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