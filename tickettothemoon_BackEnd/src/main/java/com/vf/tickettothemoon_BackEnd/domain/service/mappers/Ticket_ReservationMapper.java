package com.vf.tickettothemoon_BackEnd.domain.service.mappers;

import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.vf.tickettothemoon_BackEnd.domain.dto.Ticket_ReservationDTO;
import com.vf.tickettothemoon_BackEnd.domain.model.Ticket_Reservation;

@Mapper
public interface Ticket_ReservationMapper {
    Ticket_ReservationMapper INSTANCE = Mappers.getMapper(Ticket_ReservationMapper.class);

    Ticket_ReservationDTO toPriceDTO(Ticket_Reservation ticket_Reservation);

    Ticket_Reservation toPrice(Ticket_ReservationDTO ticket_ReservationDTO);

    @IterableMapping(elementTargetType = Ticket_ReservationDTO.class)
    Iterable<Ticket_ReservationDTO> toTicket_ReservationDTOs(
            Iterable<Ticket_Reservation> ticket_Reservations);

    @IterableMapping(elementTargetType = Ticket_Reservation.class)
    Iterable<Ticket_Reservation> toTicket_Reservations(
            Iterable<Ticket_ReservationDTO> ticket_ReservationDTOs);

}
