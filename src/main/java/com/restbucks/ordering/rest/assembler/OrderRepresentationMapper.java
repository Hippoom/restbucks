package com.restbucks.ordering.rest.assembler;

import com.restbucks.ordering.domain.Order;
import com.restbucks.ordering.rest.representation.OrderRepresentation;
import org.modelmapper.ModelMapper;

import static org.modelmapper.config.Configuration.AccessLevel.PRIVATE;

public class OrderRepresentationMapper {
    public OrderRepresentation from(Order entity) {
        return newModelMapper().map(entity, OrderRepresentation.class);
    }

    private ModelMapper newModelMapper() {
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration()
                .setFieldMatchingEnabled(true)
                .setFieldAccessLevel(PRIVATE);
        return mapper;
    }


}