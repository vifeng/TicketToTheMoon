package com.vf.eventhubserver.domain.service.mappers;

import java.util.List;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import com.vf.eventhubserver.domain.dto.SessionEventDTO;
import com.vf.eventhubserver.domain.model.SessionEvent;

@Mapper(componentModel = "spring")
public interface SessionEventMapper {

        SessionEventDTO toDTO(SessionEvent sessionEvent);

        SessionEvent toEntity(SessionEventDTO sessionEventDTO);

        @IterableMapping(elementTargetType = SessionEventDTO.class)
        List<SessionEventDTO> toDTOs(Iterable<SessionEvent> sessionEvents);

        @IterableMapping(elementTargetType = SessionEvent.class)
        List<SessionEvent> toEntities(Iterable<SessionEventDTO> sessionEventDTOs);

}
