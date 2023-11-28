package com.vf.tickettothemoon_BackEnd.domain.dao;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.vf.tickettothemoon_BackEnd.domain.model.Ticket_Reservation;
import com.vf.tickettothemoon_BackEnd.domain.model.Ticket_ReservationKey;

public interface Ticket_ReservationRepository
                extends JpaRepository<Ticket_Reservation, Ticket_ReservationKey> {

        Optional<Ticket_Reservation> findById(Ticket_ReservationKey id);

        Long findByIdSeatId(Long seatId);

        Long findByIdSessionEventId(Long sessionEventId);

        boolean findByIsBooked(boolean isBooked);

}
