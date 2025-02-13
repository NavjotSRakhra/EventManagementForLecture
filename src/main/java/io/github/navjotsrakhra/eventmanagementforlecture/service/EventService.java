package io.github.navjotsrakhra.eventmanagementforlecture.service;

import io.github.navjotsrakhra.eventmanagementforlecture.dto.EventMapper;
import io.github.navjotsrakhra.eventmanagementforlecture.dto.request.EventRequestDto;
import io.github.navjotsrakhra.eventmanagementforlecture.dto.response.EventResponseDto;
import io.github.navjotsrakhra.eventmanagementforlecture.jpa.Event;
import io.github.navjotsrakhra.eventmanagementforlecture.repository.EventRepository;
import jakarta.transaction.Transactional;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
public class EventService {

    private final EventRepository eventRepository;
    private final EventMapper eventMapper;

    public EventService(EventRepository eventRepository, EventMapper eventMapper) {
        this.eventRepository = eventRepository;
        this.eventMapper = eventMapper;
    }

    @Transactional
    public ResponseEntity<List<EventResponseDto>> getAllEvents() {
        return ResponseEntity.ok(
                eventRepository.findAll()
                        .stream().sorted(Comparator.comparing(Event::getId))
                        .map(eventMapper::toEventResponseDto)
                        .toList()
        );
    }

    @Transactional
    public ResponseEntity<EventResponseDto> getEventById(Long id) {
        Optional<Event> event = eventRepository.findById(id);
        return event
                .map(eventMapper::toEventResponseDto)
                .map(ResponseEntity::ok)
                .orElseGet(
                        () -> ResponseEntity.notFound().build()
                );
    }

    @Transactional
    public ResponseEntity<EventResponseDto> addEvent(EventRequestDto event) {
        Event jpaEvent = eventMapper.toEventJpa(event);
        EventResponseDto response = eventMapper.toEventResponseDto(this.eventRepository.save(jpaEvent));
        return ResponseEntity
                .ok(response);
    }

    @Transactional
    public ResponseEntity<EventResponseDto> updateEvent(Long id, EventRequestDto event) {
        if (eventRepository.existsById(id)) {
            Event eventToUpdate = eventMapper.toEventJpa(event);
            eventToUpdate.setId(id);
            return ResponseEntity
                    .ok(
                            eventMapper.toEventResponseDto(
                                    this.eventRepository.save(
                                            eventToUpdate
                                    )
                            )
                    );
        }
        return ResponseEntity
                .notFound()
                .build();
    }

    @Transactional
    public ResponseEntity<EventResponseDto> deleteEventById(Long id) {
        Optional<Event> event = eventRepository.findById(id);

        event.ifPresent(
                eventRepository::delete
        );
        return event.map(value -> ResponseEntity
                        .ok(
                                eventMapper.toEventResponseDto(value)
                        ))
                .orElseGet(
                        () -> ResponseEntity
                                .notFound()
                                .build()
                );

    }
}
