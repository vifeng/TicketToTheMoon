package com.vf.eventhubserver.show;

import java.util.List;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface EventMapper {

  EventDTO toDTO(Event event);

  Event toEntity(EventDTO eventDTO);

  @IterableMapping(elementTargetType = EventDTO.class)
  List<EventDTO> toDTOs(Iterable<Event> events);

  @IterableMapping(elementTargetType = Event.class)
  List<Event> toEntities(Iterable<EventDTO> eventDTOs);
}
