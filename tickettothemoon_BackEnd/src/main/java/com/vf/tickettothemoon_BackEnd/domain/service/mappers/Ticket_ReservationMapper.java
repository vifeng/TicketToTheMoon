package com.vf.tickettothemoon_BackEnd.domain.service.mappers;

import java.util.List;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import com.vf.tickettothemoon_BackEnd.domain.dto.Ticket_ReservationDTO;
import com.vf.tickettothemoon_BackEnd.domain.dto.Ticket_ReservationKeyDTO;
import com.vf.tickettothemoon_BackEnd.domain.model.Ticket_Reservation;
import com.vf.tickettothemoon_BackEnd.domain.model.Ticket_ReservationKey;

@Mapper
public interface Ticket_ReservationMapper {
        Ticket_ReservationMapper INSTANCE = Mappers.getMapper(Ticket_ReservationMapper.class);

        @Mapping(target = "seat", source = "ticket_Reservation.seat")
        @Mapping(target = "sessionEvent", source = "ticket_Reservation.sessionEvent")
        Ticket_ReservationDTO toTicket_ReservationDTO(Ticket_Reservation ticket_Reservation);

        @InheritInverseConfiguration
        Ticket_Reservation toTicket_Reservation(Ticket_ReservationDTO ticket_ReservationDTO);

        @IterableMapping(elementTargetType = Ticket_ReservationDTO.class)
        List<Ticket_ReservationDTO> toTicket_ReservationDTOs(
                        Iterable<Ticket_Reservation> ticket_Reservations);

        @IterableMapping(elementTargetType = Ticket_Reservation.class)
        List<Ticket_Reservation> toTicket_Reservations(
                        Iterable<Ticket_ReservationDTO> ticket_ReservationDTOs);

        Ticket_ReservationKey map(Ticket_ReservationKeyDTO ticket_ReservationKeyDTO);
}
