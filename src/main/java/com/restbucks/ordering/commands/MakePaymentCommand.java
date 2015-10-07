package com.restbucks.ordering.commands;

import com.github.hippoom.resthelper.annotation.PathVar;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@Getter
@NoArgsConstructor
public class MakePaymentCommand {
    @PathVar("orderId")
    private String orderId;
    private double amount;

    public MakePaymentCommand(String orderId, double amount) {
        this.orderId = orderId;
        this.amount = amount;
    }
}
