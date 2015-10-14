package com.restbucks.ordering.commands;

import com.github.hippoom.resthelper.annotation.PathVar;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MarkOrderInPreparationCommand {
    @PathVar("trackingId")
    private String trackingId;

    public MarkOrderInPreparationCommand(String trackingId) {
        this.trackingId = trackingId;
    }
}
