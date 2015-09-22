package com.restbucks.ordering.rest;

import com.google.common.base.Charsets;
import com.google.common.io.Resources;
import com.jayway.jsonpath.JsonPath;
import com.jayway.restassured.RestAssured;
import com.restbucks.commandhandling.gateway.CommandGateway;
import com.restbucks.ordering.Application;
import com.restbucks.ordering.commands.PlaceOrderCommand;
import com.restbucks.ordering.domain.Order;
import com.restbucks.ordering.domain.OrderFixture;
import com.restbucks.ordering.rest.assembler.OrderRepresentationAssembler;
import com.restbucks.ordering.rest.representation.OrderRepresentation;
import com.restbucks.ordering.rest.representation.OrderRepresentationFixture;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.hateoas.LinkDiscoverer;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.io.IOException;

import static com.jayway.restassured.RestAssured.given;
import static com.jayway.restassured.http.ContentType.JSON;
import static org.apache.http.HttpStatus.SC_CREATED;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.when;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest("server.port:0")//random port used
@DirtiesContext
public class OrderingResourceIntegrationTest implements ApplicationContextAware {

    @Value("${local.server.port}")
    private int port;

    @Autowired
    private LinkDiscoverer linkDiscoverer;

    @InjectMocks
    @Autowired
    private OrderingResource subject;

    @Mock
    private CommandGateway commandGateway;

    @Mock
    private OrderRepresentationAssembler orderRepresentationAssembler;
    private final ArgumentCaptor<PlaceOrderCommand> placeOrderCommand = ArgumentCaptor.forClass(PlaceOrderCommand.class);
    private ApplicationContext applicationContext;

    @Before
    public void config_rest_assured() {
        RestAssured.baseURI = getBaseUri();
        RestAssured.port = getPort();
    }

    @Before
    public void injectMocks() {
        MockitoAnnotations.initMocks(this);
        reset(commandGateway);
        reset(orderRepresentationAssembler);
    }

    @Test
    public void whenPlaceOrder_itShouldSendDeserializeRequestBodyToPlaceOrderCommandThenSerializeRepresentation() throws Exception {

        String command = readFileAsString("classpath:rest/place-order.json");


        Order order = new OrderFixture().build();
        OrderRepresentation representation = new OrderRepresentationFixture().build();

        when(commandGateway.send(placeOrderCommand.capture())).thenReturn(order);
        when(orderRepresentationAssembler.assemble(order)).thenReturn(representation);

        given().contentType(JSON).content(command)
                .when()
                .post("/order")
                .then().log().everything()
                .assertThat()
                .statusCode(SC_CREATED)
                .body("any { it.key == 'tracking_id' }", is(true))
                .body("any { it.key == 'location' }", is(true))
                .body("any { it.key == 'status' }", is(true))
                .body("any { it.key == 'cost' }", is(true))
                .body("items.any { it.containsKey('name') }", is(true))
                .body("items.any { it.containsKey('quantity') }", is(true))
                .body("items.any { it.containsKey('milk') }", is(true))
                .body("items.any { it.containsKey('size') }", is(true));

        assertThat(placeOrderCommand.getValue().getLocation(),
                equalTo(JsonPath.read(command, "$.location")));
        assertThat(placeOrderCommand.getValue().getItems().get(0).getName(),
                equalTo(JsonPath.read(command, "$.items[0].name")));
        assertThat(placeOrderCommand.getValue().getItems().get(0).getQuantity(),
                equalTo(JsonPath.read(command, "$.items[0].quantity")));
        assertThat(placeOrderCommand.getValue().getItems().get(0).getMilk(),
                equalTo(JsonPath.read(command, "$.items[0].milk")));
        assertThat(placeOrderCommand.getValue().getItems().get(0).getSize(),
                equalTo(JsonPath.read(command, "$.items[0].size")));
    }

    private String readFileAsString(String path) throws IOException {
        return Resources.toString(applicationContext.getResource(path).getURL(), Charsets.UTF_8);
    }

    private String getResourceUri(String path) {
        return getBaseUri() + ":" + getPort() + path;
    }

    protected String getBaseUri() {
        return RestAssured.DEFAULT_URI;
    }

    protected int getPort() {
        return port;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
