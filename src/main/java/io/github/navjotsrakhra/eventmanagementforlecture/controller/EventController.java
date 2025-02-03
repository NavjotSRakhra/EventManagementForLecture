package io.github.navjotsrakhra.eventmanagementforlecture.controller;

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
    public ResponseEntity<List<Event>> getAllEvents() {
        return this.eventService.getAllEvents();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Event> getEventById(@PathVariable Long id) {
        return this.eventService.getEventById(id);
    }

    @PostMapping
    public ResponseEntity<Event> addEvent(@RequestBody Event event) {
        return this.eventService.addEvent(event);
    }

    @PutMapping
    public ResponseEntity<Event> updateEvent(@RequestBody Event event) {
        return this.eventService.updateEvent(event);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Event> deleteEvent(@PathVariable Long id) {
        return this.eventService.deleteEventById(id);
    }
}
