package com.restbucks.ordering.rest.assembler;

import com.restbucks.ordering.domain.Order;
import com.restbucks.ordering.rest.representation.OrderRepresentation;
import org.modelmapper.ModelMapper;
import org.modelmapper.config.Configuration;
import org.springframework.stereotype.Component;

import static org.modelmapper.config.Configuration.AccessLevel.PRIVATE;

@Component
public class OrderRepresentationAssembler {
    public OrderRepresentation assemble(Order model) {
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration()
                .setFieldMatchingEnabled(true)
                .setFieldAccessLevel(PRIVATE);
        return mapper.map(model, OrderRepresentation.class);
    }
}
