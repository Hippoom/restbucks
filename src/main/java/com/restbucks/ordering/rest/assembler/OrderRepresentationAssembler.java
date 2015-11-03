package com.restbucks.ordering.rest.assembler;

import com.restbucks.ordering.domain.Order;
import com.restbucks.ordering.rest.OrderResource;
import com.restbucks.ordering.rest.PaymentResource;
import com.restbucks.ordering.rest.representation.OrderRepresentation;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;

import static com.restbucks.ordering.domain.Order.Status.PAID;
import static com.restbucks.ordering.domain.Order.Status.PAYMENT_EXPECTED;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@Component
public class OrderRepresentationAssembler extends ResourceAssemblerSupport<Order, OrderRepresentation> {
    private final OrderRepresentationMapper orderRepresentationMapper = new OrderRepresentationMapper();

    public OrderRepresentationAssembler() {
        super(OrderResource.class, OrderRepresentation.class);
    }

    public OrderRepresentation assemble(Order model) {
        return toResource(model);
    }

    @Override
    public OrderRepresentation toResource(Order entity) {
        OrderRepresentation representation = orderRepresentationMapper.from(entity);

        if (entity.is(PAYMENT_EXPECTED)) {
            representation.add(
                linkTo(methodOn(PaymentResource.class).get(entity.getTrackingId())).withRel("payment"));
        }
        if (entity.is(PAID)) {
            representation.add(
                    linkTo(methodOn(OrderResource.class).prepare(entity.getTrackingId())).withRel("order-in-preparation"));
        }
        representation.add(
                linkTo(methodOn(OrderResource.class).get(entity.getTrackingId())).withSelfRel());
        return representation;
    }

}
