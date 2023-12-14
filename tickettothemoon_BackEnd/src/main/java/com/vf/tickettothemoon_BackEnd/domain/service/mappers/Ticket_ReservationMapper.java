package com.vf.tickettothemoon_BackEnd.domain.service.mappers;

import java.util.List;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import com.vf.tickettothemoon_BackEnd.domain.dto.Ticket_ReservationDTO;
import com.vf.tickettothemoon_BackEnd.domain.dto.Ticket_ReservationKeyDTO;
import com.vf.tickettothemoon_BackEnd.domain.model.Ticket_Reservation;
import com.vf.tickettothemoon_BackEnd.domain.model.Ticket_ReservationKey;

@Mapper(componentModel = "spring")
public interface Ticket_ReservationMapper {

        @Mapping(target = "seat", source = "ticket_Reservation.seat")
        @Mapping(target = "sessionEvent", source = "ticket_Reservation.sessionEvent")
        @Mapping(source = "isBooked", target = "isBooked")
        Ticket_ReservationDTO toTicket_ReservationDTO(Ticket_Reservation ticket_Reservation);

        @InheritInverseConfiguration
        Ticket_Reservation toTicket_Reservation(Ticket_ReservationDTO ticket_ReservationDTO);

        @IterableMapping(elementTargetType = Ticket_ReservationDTO.class)
        List<Ticket_ReservationDTO> toTicket_ReservationDTOs(
                        Iterable<Ticket_Reservation> ticket_Reservations);

        @IterableMapping(elementTargetType = Ticket_Reservation.class)
        List<Ticket_Reservation> toTicket_Reservations(
                        Iterable<Ticket_ReservationDTO> ticket_ReservationDTOs);


        // Composite key mappers

        @Mapping(target = "seatId", source = "ticket_ReservationKeyDTO.seatId")
        @Mapping(target = "sessionEventId", source = "ticket_ReservationKeyDTO.sessionEventId")
        Ticket_ReservationKey map(Ticket_ReservationKeyDTO ticket_ReservationKeyDTO);

        Ticket_ReservationKeyDTO map(Ticket_ReservationKey ticket_ReservationKey);

}
