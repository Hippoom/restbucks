package com.restbucks.ordering.persistence.jpa;

import com.restbucks.ordering.domain.Order;
import com.restbucks.ordering.domain.OrderRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.UUID;

@Component
public class JpaOrderRepository implements OrderRepository {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public String nextTrackingId() {
        return UUID.randomUUID().toString();
    }

    @Transactional
    @Override
    public void store(Order order) {
        entityManager.persist(order);
    }

    @Override
    public Order findByTrackingId(String trackingId) {
        return entityManager.find(Order.class, trackingId);
    }
}
