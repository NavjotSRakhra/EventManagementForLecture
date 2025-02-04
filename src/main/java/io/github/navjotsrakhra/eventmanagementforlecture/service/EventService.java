package io.github.navjotsrakhra.eventmanagementforlecture.service;

import io.github.navjotsrakhra.eventmanagementforlecture.jpa.Event;
import io.github.navjotsrakhra.eventmanagementforlecture.repository.EventRepository;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EventService {
    private final EventRepository eventRepository;

    public EventService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    public ResponseEntity<List<Event>> getAllEvents() {
        return ResponseEntity.ok(
                eventRepository.findAll()
        );
    }

    public ResponseEntity<Event> getEventById(Long id) {
        Optional<Event> event = eventRepository.findById(id);
        return event.map(ResponseEntity::ok)
                .orElseGet(
                        () -> ResponseEntity.notFound().build()
                );
    }

    public ResponseEntity<Event> addEvent(@Valid Event event) {
        event = this.eventRepository.save(event);
        return ResponseEntity.ok(event);
    }

    public ResponseEntity<Event> updateEvent(@Valid Event event) {
        event = this.eventRepository.save(event);
        return ResponseEntity.ok(event);
    }

    public ResponseEntity<Event> deleteEventById(Long id) {
        Optional<Event> event = eventRepository.findById(id);
        if (event.isPresent()) {
            this.eventRepository.delete(event.get());
            return ResponseEntity.ok(event.get());
        }
        return ResponseEntity.notFound().build();
    }
}
