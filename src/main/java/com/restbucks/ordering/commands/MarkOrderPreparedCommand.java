package com.restbucks.ordering.commands;

import com.github.hippoom.resthelper.annotation.PathVar;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MarkOrderPreparedCommand {
    @PathVar("trackingId")
    private String trackingId;

    public MarkOrderPreparedCommand(String trackingId) {
        this.trackingId = trackingId;
    }
}
