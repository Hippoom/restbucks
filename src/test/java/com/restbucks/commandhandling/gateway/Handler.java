package com.restbucks.commandhandling.gateway;

import com.restbucks.commandhandling.annotation.CommandHandler;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class Handler {

    public static final String FIXED_RETURNING = "abcd";

    @Transactional
    @CommandHandler
    public String handle(CommandHasHandler command) {
        return FIXED_RETURNING;
    }

    public static class CommandHasHandler {
    }

}