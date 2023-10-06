package com.vf.tickettothemoon_BackEnd.domain.service.mappers;

import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.vf.tickettothemoon_BackEnd.domain.dto.Ticket_ReservationIdDTO;
import com.vf.tickettothemoon_BackEnd.domain.model.Ticket_ReservationId;

@Mapper
public interface Ticket_ReservationIdMapper {
    Ticket_ReservationIdMapper INSTANCE = Mappers.getMapper(Ticket_ReservationIdMapper.class);

    Ticket_ReservationIdDTO toTicket_ReservationIdDTO(Ticket_ReservationId ticket_ReservationId);

    Ticket_ReservationId toTicket_ReservationId(Ticket_ReservationIdDTO ticket_ReservationIdDTO);

    @IterableMapping(elementTargetType = Ticket_ReservationIdDTO.class)
    Iterable<Ticket_ReservationIdDTO> toTicket_ReservationIdDTOs(
            Iterable<Ticket_ReservationId> ticket_ReservationIds);

    @IterableMapping(elementTargetType = Ticket_ReservationId.class)
    Iterable<Ticket_ReservationId> toTicket_ReservationIds(
            Iterable<Ticket_ReservationIdDTO> ticket_ReservationIdDTOs);
}
