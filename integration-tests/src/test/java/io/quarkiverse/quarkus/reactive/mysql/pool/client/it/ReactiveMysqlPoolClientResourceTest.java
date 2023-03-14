package io.quarkiverse.quarkus.reactive.mysql.pool.client.it;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;

import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
public class ReactiveMysqlPoolClientResourceTest {

    @Test
    public void testHelloEndpoint() {
        given()
                .when().get("/reactive-mysql-pool-client")
                .then()
                .statusCode(200)
                .body(is("Hello reactive-mysql-pool-client"));
    }
}
