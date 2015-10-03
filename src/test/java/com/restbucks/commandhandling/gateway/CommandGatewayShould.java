package com.restbucks.commandhandling.gateway;

import com.restbucks.ordering.Application;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@ActiveProfiles("test")
public class CommandGatewayShould {

    private CommandGateway subject = new CommandGateway();

    @Autowired
    private Handler handler;

    @Before
    public void setUp() throws Exception {
        subject.register(handler);
    }

    @Test
    public void dispatchTheCommand_givenTheCommandHasRegister() {
        String send = subject.send(new Handler.CommandHasHandler());

        assertThat(send, is(Handler.FIXED_RETURNING));
    }



}