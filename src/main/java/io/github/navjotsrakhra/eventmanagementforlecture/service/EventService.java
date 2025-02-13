package io.github.navjotsrakhra.eventmanagementforlecture.service;

import io.github.navjotsrakhra.eventmanagementforlecture.dto.EventMapper;
import io.github.navjotsrakhra.eventmanagementforlecture.dto.request.EventRequestDto;
import io.github.navjotsrakhra.eventmanagementforlecture.dto.response.EventResponseDto;
import io.github.navjotsrakhra.eventmanagementforlecture.jpa.Event;
import io.github.navjotsrakhra.eventmanagementforlecture.repository.EventRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class EventService {

    private final EventRepository eventRepository;
    private final EventMapper eventMapper;
    private final CacheManager cacheManager;

    public EventService(EventRepository eventRepository, EventMapper eventMapper, CacheManager cacheManager) {
        this.eventRepository = eventRepository;
        this.eventMapper = eventMapper;
        this.cacheManager = cacheManager;
    }

    @Transactional
    @Cacheable("allEvents")
    public ResponseEntity<List<EventResponseDto>> getAllEvents() {
        return ResponseEntity.ok(
                eventRepository.findAll()
                        .stream().sorted(Comparator.comparing(Event::getId))
                        .map(eventMapper::toEventResponseDto)
                        .toList()
        );
    }

    @Transactional
    @Cacheable(value = "event", condition = "#id != null")
    public ResponseEntity<EventResponseDto> getEventById(Long id) {
        Objects.requireNonNull(id, "ID must not be null");

        Optional<Event> event = eventRepository.findById(id);
        return event
                .map(eventMapper::toEventResponseDto)
                .map(ResponseEntity::ok)
                .orElseGet(
                        () -> ResponseEntity.notFound().build()
                );
    }

    @Transactional
    @CacheEvict(value = "allEvents", allEntries = true)
    public ResponseEntity<EventResponseDto> addEvent(EventRequestDto event) {
        Objects.requireNonNull(event, "Event must not be null");

        Event jpaEvent = eventMapper.toEventJpa(event);
        jpaEvent = eventRepository.save(jpaEvent);
        cacheManager.getCache("event").evict(jpaEvent.getId());
        EventResponseDto response = eventMapper.toEventResponseDto(jpaEvent);
        return ResponseEntity
                .ok(response);
    }

    @Transactional
    @CachePut(value = "event", key = "#id", condition = "#id != null")
    @CacheEvict(value = "allEvents", allEntries = true)
    public ResponseEntity<EventResponseDto> updateEvent(Long id, EventRequestDto event) {
        Objects.requireNonNull(id, "ID must not be null");
        Objects.requireNonNull(event, "Event must not be null");

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
    @Caching(
            evict = {
                    @CacheEvict(value = "event", key = "#id", condition = "#id != null"),
                    @CacheEvict(value = "allEvents", allEntries = true)
            }
    )
    public ResponseEntity<EventResponseDto> deleteEventById(Long id) {
        Objects.requireNonNull(id, " ID must not be null");

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
