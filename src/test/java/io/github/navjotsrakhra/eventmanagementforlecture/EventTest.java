package io.github.navjotsrakhra.eventmanagementforlecture;

import io.github.navjotsrakhra.eventmanagementforlecture.dto.EventMapper;
import io.github.navjotsrakhra.eventmanagementforlecture.dto.request.EventRequestDto;
import io.github.navjotsrakhra.eventmanagementforlecture.dto.response.EventResponseDto;
import io.github.navjotsrakhra.eventmanagementforlecture.jpa.Event;
import io.github.navjotsrakhra.eventmanagementforlecture.repository.EventRepository;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import java.util.Random;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class EventTest {

    @LocalServerPort
    private int port;

    private static Event event;

    private Random random = new Random();


    private final EventRepository eventRepository;
    private final EventMapper eventMapper;

    public EventTest(@Autowired EventRepository eventRepository, @Autowired EventMapper eventMapper) {
        this.eventRepository = eventRepository;
        this.eventMapper = eventMapper;
    }

    @BeforeEach
    public void setup() {
        RestAssured.port = port;
        for (int i = 0; i < 16; i++) {
            event = new Event();
            event.setId(null);
            event.setTitle(randomString());
            event.setDescription(randomString());
            event = eventRepository.save(event);
        }
    }

    String randomString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 16; i++) {
            sb.append(random.nextInt(26) + 'a');
        }
        return sb.toString();
    }

    @Test
    public void getEventsTest() {
        given()
                .when().get("api/event")
                .then()
                .assertThat().statusCode(200)
                .assertThat().body("find { it.id == " + event.getId() + " }.title", equalTo(event.getTitle()))
                .assertThat().body("find { it.id == " + event.getId() + " }.description", equalTo(event.getDescription()));
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
    public void getEventNotFoundTest() {
        given()
                .when().get("api/event/" + (event.getId() + 2000))
                .then()
                .assertThat().statusCode(404);
    }

    @Test
    public void updateEventTest() {
        Event eventNew = new Event();
        eventNew.setId(event.getId());
        eventNew.setTitle(event.getTitle() + "e");
        eventNew.setDescription(event.getDescription());
        given()
                .contentType("application/json")
                .body(eventNew)
                .when().put("api/event/" + event.getId())
                .then()
                .assertThat().statusCode(200)
                .assertThat().body("title", equalTo(event.getTitle() + "e"))
                .assertThat().body("description", equalTo(event.getDescription()));
    }

    @Test
    public void updateEventNotFoundTest() {
        given()
                .contentType("application/json")
                .body(event)
                .when().put("api/event/" + event.getId() + 2000)
                .then()
                .assertThat().statusCode(404);
    }

    @Test
    public void deleteEventTest() {
        given()
                .when().delete("api/event/" + event.getId())
                .then()
                .assertThat().statusCode(200)
                .assertThat().body("title", equalTo(event.getTitle()))
                .assertThat().body("description", equalTo(event.getDescription()));
    }

    @Test
    public void deleteEventNotFoundTest() {
        given()
                .when().delete("api/event/" + event.getId() + 2000)
                .then()
                .assertThat().statusCode(404);
    }

    @Test
    public void addEventTest() {
        given()
                .contentType("application/json")
                .body(event)
                .when().post("api/event")
                .then()
                .assertThat().statusCode(200)
                .assertThat().body("title", equalTo(event.getTitle()))
                .assertThat().body("description", equalTo(event.getDescription()));
    }
    @Test
    public void exceptionTest(){
        given()
                .when().get("api/error-test")
                .then()
                .assertThat().statusCode(500);
    }

    @Test
    public void testMapEvent() {
        final Event event = new Event();
        event.setTitle("Title");
        event.setDescription("Description");

        EventRequestDto eventRequestDto = new EventRequestDto(event.getTitle(), event.getDescription());

        Event mappedEvent = eventMapper.toEventJpa(eventRequestDto);

        assertEquals(event, mappedEvent);
    }

    @Test
    public void testMapEventResponse() {
        final Event event = new Event();
        event.setId(23L);
        event.setTitle("Title");
        event.setDescription("Description");

        EventResponseDto eventResponseDto = eventMapper.toEventResponseDto(event);

        assertEquals(event.getId(), eventResponseDto.id());
        assertEquals(event.getTitle(), eventResponseDto.title());
        assertEquals(event.getDescription(), eventResponseDto.description());
    }
}
