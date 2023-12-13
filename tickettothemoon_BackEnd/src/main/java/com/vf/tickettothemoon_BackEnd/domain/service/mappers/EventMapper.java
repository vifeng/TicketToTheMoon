package com.vf.tickettothemoon_BackEnd.domain.service.mappers;

import java.util.List;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import com.vf.tickettothemoon_BackEnd.domain.dto.EventDTO;
import com.vf.tickettothemoon_BackEnd.domain.model.Event;

@Mapper(componentModel = "spring")
public interface EventMapper {

    EventDTO toEventDTO(Event event);

    Event toEvent(EventDTO eventDTO);

    @IterableMapping(elementTargetType = EventDTO.class)
    List<EventDTO> toEventDTOs(Iterable<Event> events);

    @IterableMapping(elementTargetType = Event.class)
    List<Event> toEvents(Iterable<EventDTO> eventDTOs);

}
