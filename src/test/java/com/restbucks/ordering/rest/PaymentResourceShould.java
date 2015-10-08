package com.restbucks.ordering.rest;

import com.jayway.jsonpath.JsonPath;
import com.restbucks.commandhandling.gateway.CommandGateway;
import com.restbucks.ordering.Application;
import com.restbucks.ordering.commands.MakePaymentCommand;
import com.restbucks.ordering.domain.Order;
import com.restbucks.ordering.domain.OrderFixture;
import com.restbucks.ordering.domain.Payment;
import com.restbucks.ordering.domain.PaymentFixture;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultHandler;
import org.springframework.test.web.servlet.result.PrintingResultHandler;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.CollectionUtils;
import org.springframework.web.context.WebApplicationContext;

import java.io.BufferedReader;
import java.io.IOException;

import static com.restbucks.ordering.rest.TestUtils.readFileAsString;
import static java.lang.String.format;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest("server.port:0")//random port used
@DirtiesContext
@ActiveProfiles("test")
public class PaymentResourceShould {
    @Value("${local.server.port}")
    private int port;

    @Autowired
    private WebApplicationContext wac;

    @InjectMocks
    @Autowired
    private PaymentResource subject;

    @Mock
    private CommandGateway commandGateway;

    private final ArgumentCaptor<MakePaymentCommand> makePaymentCommand = ArgumentCaptor.forClass(MakePaymentCommand.class);
    private MockMvc mvc;

    @Before
    public void setUp() throws Exception {
        mvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }

    @Before
    public void injectMocks() {
        MockitoAnnotations.initMocks(this);
        reset(commandGateway);
    }

    @Test
    public void convertRequestToMakePaymentCommand_whenMakePayment() throws Exception {

        String command = readFileAsString("classpath:rest/make-payment.json");

        Order order = new OrderFixture().build();
        Payment payment = new PaymentFixture(order).build();

        when(commandGateway.send(makePaymentCommand.capture())).thenReturn(payment);

        mvc.perform(
                put("/payment/" + order.getTrackingId())
                        .content(command).contentType(MediaType.APPLICATION_JSON))
                .andDo(CustomMockMvcResultHandlers.print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(payment.getId())))
                .andExpect(jsonPath("$.amount", is(2.4)))
                .andExpect(jsonPath("$._links.self.href",
                        is(format("http://localhost/payment/%s", order.getTrackingId()))));


        assertThat(makePaymentCommand.getValue().getOrderId(),
                equalTo(order.getTrackingId()));
        assertThat(makePaymentCommand.getValue().getAmount(),
                equalTo(JsonPath.read(command, "$.amount")));
    }

    public static class CustomMockMvcResultHandlers {

        public static ResultHandler print() {
            return new ConsolePrintingResultHandler();
        }


        /**
         * An {@link PrintingResultHandler} that writes to the "standard" output stream
         */
        private static class ConsolePrintingResultHandler extends PrintingResultHandler {

            public ConsolePrintingResultHandler() {
                super(new ResultValuePrinter() {

                    @Override
                    public void printHeading(String heading) {
                        System.out.println();
                        System.out.println(String.format("%20s:", heading));
                    }

                    @Override
                    public void printValue(String label, Object value) {
                        if (value != null && value.getClass().isArray()) {
                            value = CollectionUtils.arrayToList(value);
                        }
                        System.out.println(String.format("%20s = %s", label, value));
                    }


                });


            }

            @Override
            protected void printRequest(MockHttpServletRequest request) throws Exception {
                super.printRequest(request);
                getPrinter().printValue("Body", getContentAsString(request));
            }

            private String getContentAsString(MockHttpServletRequest request) throws IOException {
                BufferedReader reader = request.getReader();

                StringBuilder builder = new StringBuilder();
                String aux;

                while ((aux = reader.readLine()) != null) {
                    builder.append(aux);
                }

                return builder.toString();
            }
        }
    }
}
