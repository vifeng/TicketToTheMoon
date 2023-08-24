package com.vf.tickettothemoon_BackEnd.domain.service.mappers;

import java.util.List;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import com.vf.tickettothemoon_BackEnd.domain.dto.SessionEventDTO;
import com.vf.tickettothemoon_BackEnd.domain.model.SessionEvent;

@Mapper
public interface SessionEventMapper {
    SessionEventMapper INSTANCE = Mappers.getMapper(SessionEventMapper.class);

    SessionEventDTO toSessionEventDTO(SessionEvent sessionEvent);

    SessionEvent toSessionEvent(SessionEventDTO sessionEventDTO);

    @IterableMapping(elementTargetType = SessionEventDTO.class)
    List<SessionEventDTO> toSessionEventDTOs(Iterable<SessionEvent> sessionEvents);

    @IterableMapping(elementTargetType = SessionEvent.class)
    List<SessionEvent> toSessionEvents(Iterable<SessionEventDTO> sessionEventDTOs);
}
