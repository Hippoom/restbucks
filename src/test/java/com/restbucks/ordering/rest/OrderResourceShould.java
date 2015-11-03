package com.restbucks.ordering.rest;

import com.jayway.jsonpath.JsonPath;
import com.jayway.restassured.RestAssured;
import com.restbucks.commandhandling.gateway.CommandGateway;
import com.restbucks.ordering.Application;
import com.restbucks.ordering.commands.MarkOrderInPreparationCommand;
import com.restbucks.ordering.commands.PlaceOrderCommand;
import com.restbucks.ordering.domain.Order;
import com.restbucks.ordering.domain.OrderFixture;
import com.restbucks.ordering.domain.OrderRepository;
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
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import static com.jayway.restassured.RestAssured.given;
import static com.jayway.restassured.http.ContentType.JSON;
import static java.lang.String.format;
import static org.apache.http.HttpStatus.SC_CREATED;
import static org.apache.http.HttpStatus.SC_OK;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest("server.port:0")//random port used
@DirtiesContext
@ActiveProfiles("test")
public class OrderResourceShould {

    @Value("${local.server.port}")
    private int port;

    @InjectMocks
    @Autowired
    private OrderResource subject;

    @Mock
    private CommandGateway commandGateway;

    @Mock
    private OrderRepository orderRepository;

    private final ArgumentCaptor<PlaceOrderCommand> placeOrderCommand = ArgumentCaptor.forClass(PlaceOrderCommand.class);
    private final ArgumentCaptor<MarkOrderInPreparationCommand> markOrderInPreparationCommand = ArgumentCaptor.forClass(MarkOrderInPreparationCommand.class);

    @Before
    public void config_rest_assured() {
        RestAssured.port = getPort();
    }

    @Before
    public void injectMocks() {
        MockitoAnnotations.initMocks(this);
        reset(commandGateway);
        reset(orderRepository);
    }

    @Test
    public void sendPlaceOrderCommand_whenPlaceOrder() throws Exception {

        String command = TestUtils.readFileAsString("classpath:rest/place-order.json");


        Order order = new OrderFixture().build();

        when(commandGateway.send(placeOrderCommand.capture())).thenReturn(order);

        String body = given().contentType(JSON).content(command)
                .when()
                .post("/order")
                .then().log().everything()
                .assertThat()
                .statusCode(SC_CREATED)
                .body("any { it.key == 'tracking_id' }", is(true))
                .body("any { it.key == 'customer' }", is(true))
                .body("any { it.key == 'location' }", is(true))
                .body("any { it.key == 'status' }", is(true))
                .body("any { it.key == 'cost' }", is(true))
                .body("items.any { it.containsKey('name') }", is(true))
                .body("items.any { it.containsKey('quantity') }", is(true))
                .body("items.any { it.containsKey('milk') }", is(true))
                .body("items.any { it.containsKey('size') }", is(true))
                .extract().response().asString();

        assertThat(JsonPath.read(body, "$._links.self.href"),
                is(format("http://localhost:%d/order/%s", getPort(), order.getTrackingId())));
        assertThat(JsonPath.read(body, "$._links.payment.href"),
                is(format("http://localhost:%d/payment/%s", getPort(), order.getTrackingId())));

        assertThat(placeOrderCommand.getValue().getCustomer(),
                equalTo(JsonPath.read(command, "$.customer")));
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

    @Test
    public void sendPrepareOrderCommand() {

        when(orderRepository.findByTrackingId("1234")).
                thenReturn(new OrderFixture("1234").build());

        // @formatter:off
        String body = given().
            contentType(JSON).
        when().
            put("/order-in-preparation/1234").
        then().
            log().everything()
            .assertThat().statusCode(SC_OK).
            extract().response().asString();
        // @formatter:on

        verify(commandGateway).send(markOrderInPreparationCommand.capture());

        assertThat(JsonPath.read(body, "$._links.self.href"),
                is(format("http://localhost:%d/order/1234", getPort())));


    }


    protected int getPort() {
        return port;
    }


}
