package com.restbucks.ordering.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "t_payment")
@Getter
@NoArgsConstructor
public class Payment {
    @Id
    private String id;
    @Version
    private int version;
    private double amount;
    @Column(name = "created_at")
    private LocalDateTime whenCreated;

    public Payment(String orderTrackingId, double amount) {
        this.id = orderTrackingId;
        this.amount = amount;
        this.whenCreated = LocalDateTime.now();
    }
}
