package com.restbucks.ordering.feature;

import com.jayway.restassured.RestAssured;
import com.restbucks.ordering.profile.ProfileConfiguration;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import java.io.IOException;

import static com.jayway.restassured.RestAssured.given;
import static com.jayway.restassured.http.ContentType.JSON;
import static com.jayway.restassured.path.json.JsonPath.from;
import static com.restbucks.ordering.rest.TestUtils.readFileAsString;
import static org.apache.http.HttpStatus.SC_CREATED;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(
        loader = AnnotationConfigContextLoader.class,
        classes = {
                ProfileConfiguration.class
        }
)
public class CashierShould {
    @Autowired
    private ProfileConfiguration profile;

    @Before
    public void config_rest_assured() {
        RestAssured.baseURI = profile.getApplicationBaseUri();
        RestAssured.port = profile.getApplicationPort();
    }

    @Test
    public void acceptOrder() throws IOException {


        String placeOrderCommand = readFileAsString("classpath:feature/order.json");

        // @formatter:off
        String orderRepresentation = given()
            .contentType(JSON).content(placeOrderCommand)
        .when()
            .post("/order")
        .then()
            .log().everything()
            .assertThat().statusCode(SC_CREATED)
            .extract().body().asString();
        // @formatter:on

        double orderAmount = from(orderRepresentation).getDouble("cost");
        String paymentLinkHref = from(orderRepresentation).get("_links.payment.href");

        // @formatter:off
        given()
            .contentType(JSON).content("{\"amount\":" + orderAmount + "}")
        .when()
            .put(paymentLinkHref)
        .then()
            .log().everything()
            .assertThat().statusCode(SC_CREATED);

        // @formatter:on
    }
}
