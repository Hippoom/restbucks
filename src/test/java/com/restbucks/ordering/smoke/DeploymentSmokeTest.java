package com.restbucks.ordering.smoke;

import com.jayway.jsonpath.JsonPath;
import com.jayway.restassured.RestAssured;
import com.jayway.restassured.response.Response;
import com.restbucks.ordering.profile.ProfileConfiguration;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import static com.jayway.awaitility.Awaitility.await;
import static com.jayway.restassured.RestAssured.when;
import static java.util.concurrent.TimeUnit.SECONDS;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(
        loader = AnnotationConfigContextLoader.class,
        classes = {
                ProfileConfiguration.class
        }
)
public class DeploymentSmokeTest {
    @Autowired
    private ProfileConfiguration profile;

    @Before
    public void config_rest_assured() {
        RestAssured.baseURI = profile.getApplicationBaseUri();
        RestAssured.port = profile.getApplicationPort();
    }

    @Test
    public void itShouldShowDeploymentMeta_whenIVisitTheSiteRoot_givenNewDeploymentIsDone() {


        await().atMost(10, SECONDS).until(() -> {
            Response response = when().get("/meta.json")
                    .then()
                    .log().everything()
                            //.statusCode(SC_OK)
                    .extract().response();

            String expectedVersion = profile.getApplicationVersion();
            String actualVersion = JsonPath.read(response.asString(), "$.version");

            assertThat(actualVersion).isEqualTo(expectedVersion);
        });
    }
}
