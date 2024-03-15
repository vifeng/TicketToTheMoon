package com.vf.eventhubserver.show;

import java.util.List;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SessionEventMapper {

        SessionEventDTO toDTO(SessionEvent sessionEvent);

        SessionEvent toEntity(SessionEventDTO sessionEventDTO);

        @IterableMapping(elementTargetType = SessionEventDTO.class)
        List<SessionEventDTO> toDTOs(Iterable<SessionEvent> sessionEvents);

        @IterableMapping(elementTargetType = SessionEvent.class)
        List<SessionEvent> toEntities(Iterable<SessionEventDTO> sessionEventDTOs);

}
