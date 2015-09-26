package com.restbucks.ordering.feature;

import com.jayway.restassured.RestAssured;
import com.restbucks.ordering.profile.ProfileConfiguration;
import com.restbucks.ordering.rest.TestUtils;
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


        String command = readFileAsString("classpath:feature/order.json");


        given().contentType(JSON).content(command)
                .when()
                .post("/order")
                .then().log().everything()
                .assertThat()
                .statusCode(SC_CREATED);

    }
}
