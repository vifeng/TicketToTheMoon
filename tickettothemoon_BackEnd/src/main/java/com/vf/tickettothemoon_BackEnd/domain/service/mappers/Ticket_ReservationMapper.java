package com.vf.tickettothemoon_BackEnd.domain.service.mappers;

import java.util.List;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import com.vf.tickettothemoon_BackEnd.domain.dto.SeatDTO;
import com.vf.tickettothemoon_BackEnd.domain.dto.Ticket_ReservationDTO;
import com.vf.tickettothemoon_BackEnd.domain.model.Seat;
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

    //////////////////////////////
    // Méthodes de mappage pour Seats
    //////////////////////////////

    // Utilisation de SeatMapper pour mapper Seats
    SeatMapper SEAT_MAPPER = Mappers.getMapper(SeatMapper.class);

    // Mappage for Seats and its DTO
    default SeatDTO toSeatDTO(Seat seat) {
        if (seat == null) {
            return null;
        }
        return SEAT_MAPPER.toSeatDTO(seat);
    }

    default Seat toSeat(SeatDTO seatDTO) {
        if (seatDTO == null) {
            return null;
        }
        return SEAT_MAPPER.toSeat(seatDTO);
    }

    @IterableMapping(elementTargetType = SeatDTO.class)
    default List<SeatDTO> toSeatDTOs(Iterable<Seat> seats) {
        if (seats == null) {
            return null;
        }
        return SEAT_MAPPER.toSeatDTOs(seats);
    };

    @IterableMapping(elementTargetType = Seat.class)
    default List<Seat> toSeats(Iterable<SeatDTO> seatDTOs) {
        if (seatDTOs == null) {
            return null;
        }
        return SEAT_MAPPER.toSeats(seatDTOs);
    };


    //////////////////////////////
    // Méthodes de mappage pour Ticket_Reservation with Seats and its DTO
    //////////////////////////////

    default Ticket_ReservationDTO toTicket_ReservationDTO(Seat seat) {
        if (seat == null) {
            return null;
        }
        return toTicket_ReservationDTO(seat.getTicket_Reservation());
    }
}
