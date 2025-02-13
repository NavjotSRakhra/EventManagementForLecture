package io.github.navjotsrakhra.eventmanagementforlecture.controller;

import io.github.navjotsrakhra.eventmanagementforlecture.dto.request.EventRequestDto;
import io.github.navjotsrakhra.eventmanagementforlecture.dto.response.EventResponseDto;
import io.github.navjotsrakhra.eventmanagementforlecture.service.EventService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/event")
public class EventController {


    private static final Logger log = LoggerFactory.getLogger(EventController.class);
    private final EventService eventService;

    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @GetMapping
    public ResponseEntity<List<EventResponseDto>> getAllEvents() {
        long startTimeNanoSeconds = System.nanoTime();
        ResponseEntity<List<EventResponseDto>> response = this.eventService.getAllEvents();
        long endTimeNanoSeconds = System.nanoTime();
        long elapsedTimeMilliseconds = Math.round((endTimeNanoSeconds - startTimeNanoSeconds) / 1_000_000.0);
        log.info("getAllEvents took {} ms", elapsedTimeMilliseconds);
        return response;
    }

    @GetMapping("/{id}")
    public ResponseEntity<EventResponseDto> getEventById(@PathVariable Long id) {
        long startTimeNanoSeconds = System.nanoTime();
        ResponseEntity<EventResponseDto> response = this.eventService.getEventById(id);
        long endTimeNanoSeconds = System.nanoTime();
        long elapsedTimeMilliseconds = Math.round((endTimeNanoSeconds - startTimeNanoSeconds) / 1_000_000.0);
        log.info("getEventById took {} ms", elapsedTimeMilliseconds);
        return response;
    }

    @PostMapping
    public ResponseEntity<EventResponseDto> addEvent(@RequestBody @Valid EventRequestDto event) {
        long startTimeNanoSeconds = System.nanoTime();
        ResponseEntity<EventResponseDto> response = this.eventService.addEvent(event);
        long endTimeNanoSeconds = System.nanoTime();
        long elapsedTimeMilliseconds = Math.round((endTimeNanoSeconds - startTimeNanoSeconds) / 1_000_000.0);
        log.info("addEvent took {} ms", elapsedTimeMilliseconds);
        return response;
    }

    @PutMapping("/{id}")
    public ResponseEntity<EventResponseDto> updateEvent(@PathVariable Long id, @RequestBody @Valid EventRequestDto event) {
        long startTimeNanoSeconds = System.nanoTime();
        ResponseEntity<EventResponseDto> response = this.eventService.updateEvent(id, event);
        long endTimeNanoSeconds = System.nanoTime();
        long elapsedTimeMilliseconds = Math.round((endTimeNanoSeconds - startTimeNanoSeconds) / 1_000_000.0);
        log.info("updateEvent took {} ms", elapsedTimeMilliseconds);
        return response;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<EventResponseDto> deleteEvent(@PathVariable Long id) {
        long startTimeNanoSeconds = System.nanoTime();
        ResponseEntity<EventResponseDto> response = this.eventService.deleteEventById(id);
        long endTimeNanoSeconds = System.nanoTime();
        long elapsedTimeMilliseconds = Math.round((endTimeNanoSeconds - startTimeNanoSeconds) / 1_000_000.0);
        log.info("deleteEvent took {} ms", elapsedTimeMilliseconds);
        return response;
    }
}
