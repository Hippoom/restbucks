package com.restbucks.ordering.commands;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class TakeReceiptCommand {
    private String trackingId;

    public TakeReceiptCommand(String trackingId) {
        this.trackingId = trackingId;
    }
}
