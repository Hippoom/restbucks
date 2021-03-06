package com.restbucks.ordering.commandhandling;

import com.restbucks.commandhandling.annotation.CommandHandler;
import com.restbucks.ordering.commands.PlaceOrderCommand;
import com.restbucks.ordering.domain.Order;
import com.restbucks.ordering.domain.OrderRepository;
import com.restbucks.ordering.domain.ProductCatalogService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.modelmapper.config.Configuration.AccessLevel.PRIVATE;

@Slf4j
@Component
public class PlaceOrderCommandHandler {
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private ProductCatalogService productCatalogService;

    @Transactional
    @CommandHandler
    public Order handle(PlaceOrderCommand command) {
        String trackingId = orderRepository.nextTrackingId();
        Order order = new Order(trackingId);
        order.customerIs(command.getCustomer());
        order.locationIs(command.getLocation());
        order.append(itemsFrom(command.getItems()));

        log.debug("Trying to store order {}", order.getTrackingId());
        orderRepository.store(order);
        return order;
    }

    private List<Order.Item> itemsFrom(List<PlaceOrderCommand.Item> items) {

        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration()
                .setFieldAccessLevel(PRIVATE)
                .setFieldMatchingEnabled(true);

        Converter<PlaceOrderCommand.Item, Double> getPrice = new AbstractConverter<PlaceOrderCommand.Item, Double>() {
            @Override
            protected Double convert(PlaceOrderCommand.Item item) {
                double price = productCatalogService.evaluate(item.getName(), item.getSize());
                log.debug("Get price from catalogService for [{}, {}]: {}", item.getName(), item.getSize(), price);
                return price;
            }
        };
        mapper.addMappings(new PropertyMap<PlaceOrderCommand.Item, Order.Item>() {
            @Override
            protected void configure() {
                using(getPrice).map(source, destination.getPrice());
            }
        });
        java.lang.reflect.Type orderItemType = new TypeToken<List<Order.Item>>() {
        }.getType();
        return mapper.map(items, orderItemType);
    }
}
