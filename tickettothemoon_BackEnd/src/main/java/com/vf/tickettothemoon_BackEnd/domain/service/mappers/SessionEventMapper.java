package com.vf.tickettothemoon_BackEnd.domain.service.mappers;

import java.util.List;
import java.util.Set;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import com.vf.tickettothemoon_BackEnd.domain.dto.SessionEventDTO;
import com.vf.tickettothemoon_BackEnd.domain.dto.Ticket_ReservationDTO;
import com.vf.tickettothemoon_BackEnd.domain.dto.Ticket_ReservationKeyDTO;
import com.vf.tickettothemoon_BackEnd.domain.model.SessionEvent;
import com.vf.tickettothemoon_BackEnd.domain.model.Ticket_Reservation;
import com.vf.tickettothemoon_BackEnd.domain.model.Ticket_ReservationKey;

@Mapper(componentModel = "spring")
public interface SessionEventMapper {

    @Mapping(target = "ticketReservations", source = "sessionEvent.ticket_Reservations")
    SessionEventDTO toSessionEventDTO(SessionEvent sessionEvent);

    @InheritInverseConfiguration
    SessionEvent toSessionEvent(SessionEventDTO sessionEventDTO);

    @IterableMapping(elementTargetType = SessionEventDTO.class)
    List<SessionEventDTO> toSessionEventDTOs(Iterable<SessionEvent> sessionEvents);

    @IterableMapping(elementTargetType = SessionEvent.class)
    List<SessionEvent> toSessionEvents(Iterable<SessionEventDTO> sessionEventDTOs);

    //////////////////////////////
    // Ticket_Reservation relationship
    //////////////////////////////
    @Mapping(target = "seat", source = "ticket_Reservation.seat")
    @Mapping(target = "sessionEvent", source = "ticket_Reservation.sessionEvent")
    Ticket_ReservationDTO toTicket_ReservationDTO(Ticket_Reservation ticket_Reservation);

    @InheritInverseConfiguration
    Ticket_Reservation toTicket_Reservation(Ticket_ReservationDTO ticket_ReservationDTO);

    Set<Ticket_Reservation> mapTicket_Reservation(
            Set<Ticket_ReservationDTO> ticket_ReservationDTOs);

    Set<Ticket_ReservationDTO> mapTicket_ReservationDTO(
            Set<Ticket_Reservation> ticket_Reservations);

    // Ticket_ReservationKey
    Ticket_ReservationKey map(Ticket_ReservationKeyDTO ticket_ReservationKey);

    Ticket_ReservationKeyDTO map(Ticket_ReservationKey ticket_ReservationKey);


}
