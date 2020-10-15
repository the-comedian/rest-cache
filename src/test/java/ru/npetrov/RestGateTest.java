package ru.npetrov;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;
import ru.npetrov.methods.CacheType;
import ru.npetrov.rest.InitCacheRequest;

import javax.ws.rs.core.MediaType;

import java.util.HashMap;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;

@QuarkusTest
public class RestGateTest {

    /**
     * sha-256 value of "hello" string
     */
    private static final String HELLO_HASH = "2cf24dba5fb0a30e26e83b2ac5b9e29e1b161e5c1fa7425e73043362938b9824";

    @Test
    public void testHelloEndpoint() {
        given()
                .when().get("/rest")
                .then()
                .statusCode(200)
                .body(is("hello"));
    }

    @Test
    public void testGetResultEndpoint() {
        given()
                .when().get("/rest/getResult/hello")
                .then()
                .statusCode(200)
                .body(is(HELLO_HASH));
    }

    @Test
    public void testGetCacheInfoEndpoint() {
        given()
                .when().get("/rest/getCacheInfo")
                .then()
                .statusCode(200)
                .body(notNullValue());
    }

    @Test
    public void testInitCacheEndpoint() {
        InitCacheRequest request = new InitCacheRequest();
        request.setCapacity(10);
        request.setType(CacheType.LFU);
        given()
                .when()
                .contentType(MediaType.APPLICATION_JSON)
                .body(request)
                .post("/rest/initCache")
                .then()
                .statusCode(200)
                .body(notNullValue());
    }

    @Test
    public void testInitInvalidCacheEndpoint() {
        given()
                .when()
                .contentType(MediaType.APPLICATION_JSON)
                .body(new HashMap<>())
                .post("/rest/initCache")
                .then()
                .statusCode(500)
                .body(notNullValue());
    }

}