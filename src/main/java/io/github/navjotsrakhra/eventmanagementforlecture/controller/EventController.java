package io.github.navjotsrakhra.eventmanagementforlecture.controller;

import io.github.navjotsrakhra.eventmanagementforlecture.dto.request.EventRequestDto;
import io.github.navjotsrakhra.eventmanagementforlecture.dto.response.EventResponseDto;
import io.github.navjotsrakhra.eventmanagementforlecture.jpa.Event;
import io.github.navjotsrakhra.eventmanagementforlecture.service.EventService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/event")
public class EventController {
    private final EventService eventService;

    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @GetMapping
    public ResponseEntity<List<EventResponseDto>> getAllEvents() {
        return this.eventService.getAllEvents();
    }

    @GetMapping("/{id}")
    public ResponseEntity<EventResponseDto> getEventById(@PathVariable Long id) {
        return this.eventService.getEventById(id);
    }

    @PostMapping
    public ResponseEntity<EventResponseDto> addEvent(@RequestBody EventRequestDto event) {
        return this.eventService.addEvent(event);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EventResponseDto> updateEvent(@PathVariable Long id, @RequestBody EventRequestDto event) {
        return this.eventService.updateEvent(id, event);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<EventResponseDto> deleteEvent(@PathVariable Long id) {
        return this.eventService.deleteEventById(id);
    }
}
