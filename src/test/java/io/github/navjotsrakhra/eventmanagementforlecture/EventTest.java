package io.github.navjotsrakhra.eventmanagementforlecture;

import io.github.navjotsrakhra.eventmanagementforlecture.jpa.Event;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class EventTest {

    @LocalServerPort
    private int port;

    private static Event event;

    @BeforeAll
    static void setUp() {
        event = new Event();
        event.setId(353L);
        event.setTitle("Test");
        event.setDescription("Test");
    }

    @BeforeEach
    public void setup() {
        RestAssured.port = port;
    }

    @Test
    public void getEventsTest() {
        given()
                .when().get("api/event")
                .then()
                .assertThat().statusCode(200)
                .assertThat().body("find { it.id == 353 }.title", equalTo(event.getTitle()))
                .assertThat().body("find { it.id == 353 }.description", equalTo(event.getDescription()));
    }

    @Test
    public void getEventTest() {
        given()
                .when().get("api/event/" + event.getId())
                .then()
                .assertThat().statusCode(200)
                .assertThat().body("title", equalTo(event.getTitle()))
                .assertThat().body("description", equalTo(event.getDescription()));
    }

    @Test
    public void updateEventTest() {
        event.setTitle("Test2");
        given()
                .contentType("application/json")
                .body(event)
                .when().put("api/event/" + event.getId())
                .then()
                .assertThat().statusCode(200)
                .assertThat().body("title", equalTo("Test2"))
                .assertThat().body("description", equalTo(event.getDescription()));
        event.setTitle("Test");
        given()
                .contentType("application/json")
                .body(event)
                .when().put("api/event/" + event.getId())
                .then()
                .assertThat().statusCode(200)
                .assertThat().body("title", equalTo("Test"))
                .assertThat().body("description", equalTo(event.getDescription()));

    }
}
