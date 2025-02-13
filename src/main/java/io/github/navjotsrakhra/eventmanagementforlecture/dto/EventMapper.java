package io.github.navjotsrakhra.eventmanagementforlecture.dto;

import io.github.navjotsrakhra.eventmanagementforlecture.dto.request.EventRequestDto;
import io.github.navjotsrakhra.eventmanagementforlecture.dto.response.EventResponseDto;
import io.github.navjotsrakhra.eventmanagementforlecture.jpa.Event;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface EventMapper {

    Event toEventJpa(EventRequestDto eventRequestDto);

    EventResponseDto toEventResponseDto(Event event);
}
