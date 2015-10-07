package com.restbucks.ordering.rest.assembler;

import com.restbucks.ordering.domain.Order;
import com.restbucks.ordering.rest.representation.OrderRepresentation;
import org.modelmapper.AbstractConverter;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;

import static org.modelmapper.config.Configuration.AccessLevel.PRIVATE;

public class OrderRepresentationMapper {
    public OrderRepresentation from(Order entity) {
        ModelMapper mapper = newModelMapper();
        mapper.addMappings(new PropertyMap<Order, OrderRepresentation>() {
            @Override
            protected void configure() {

                Converter<Order.Status, String> converter = new AbstractConverter<Order.Status, String>() {
                    protected String convert(Order.Status source) {
                        return source.getValue();
                    }
                };

                using(converter).map(source.getStatus(), destination.status);
            }
        });
        return mapper.map(entity, OrderRepresentation.class);
    }

    private ModelMapper newModelMapper() {
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration()
                .setFieldMatchingEnabled(true)
                .setFieldAccessLevel(PRIVATE);
        return mapper;
    }


}