package com.restbucks.commandhandling.gateway;

import com.restbucks.commandhandling.gateway.CommandGateway;
import com.restbucks.commandhandling.annotation.CommandHandler;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class CommandGatewayTest {

    private CommandGateway subject = new CommandGateway();

    private Handler handler = new Handler();

    @Before
    public void setUp() throws Exception {
        subject.register(handler);
    }

    @Test
    public void itShouldForwardTheCommand_givenTheCommandHasSubscriber() {
        String send = subject.send(new CommandHasHandler());

        assertThat(send, is(Handler.FIXED_RETURNING));
    }

    private class CommandHasHandler {
    }

    private class Handler {

        public static final String FIXED_RETURNING = "abcd";

        @CommandHandler
        public String handle(CommandHasHandler command) {
            return FIXED_RETURNING;
        }

    }
}