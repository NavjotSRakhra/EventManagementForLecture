package io.github.navjotsrakhra.eventmanagementforlecture.repository;

import io.github.navjotsrakhra.eventmanagementforlecture.jpa.Event;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventRepository extends ListCrudRepository<Event, Long> {
    boolean existsById(long id);
}
