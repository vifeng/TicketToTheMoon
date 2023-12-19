package com.vf.tickettothemoon_BackEnd.domain.service.mappers;

import java.util.List;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import com.vf.tickettothemoon_BackEnd.domain.dto.SessionEventDTO;
import com.vf.tickettothemoon_BackEnd.domain.model.SessionEvent;

@Mapper(componentModel = "spring")
public interface SessionEventMapper {

        SessionEventDTO toSessionEventDTO(SessionEvent sessionEvent);

        SessionEvent toSessionEvent(SessionEventDTO sessionEventDTO);

        @IterableMapping(elementTargetType = SessionEventDTO.class)
        List<SessionEventDTO> toSessionEventDTOs(Iterable<SessionEvent> sessionEvents);

        @IterableMapping(elementTargetType = SessionEvent.class)
        List<SessionEvent> toSessionEvents(Iterable<SessionEventDTO> sessionEventDTOs);

}
