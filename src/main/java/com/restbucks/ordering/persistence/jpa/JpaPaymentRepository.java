package com.restbucks.ordering.persistence.jpa;

import com.restbucks.ordering.domain.Order;
import com.restbucks.ordering.domain.Payment;
import com.restbucks.ordering.domain.PaymentRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Component
public class JpaPaymentRepository implements PaymentRepository {
    @PersistenceContext
    private EntityManager entityManager;


    @Transactional
    @Override
    public void store(Payment payment) {
        entityManager.persist(payment);
    }

    @Override
    public Payment findById(String id) {
        return entityManager.find(Payment.class, id);
    }
}
