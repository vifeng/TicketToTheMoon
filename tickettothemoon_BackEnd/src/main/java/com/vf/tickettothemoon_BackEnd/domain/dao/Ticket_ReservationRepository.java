package com.vf.tickettothemoon_BackEnd.domain.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import com.vf.tickettothemoon_BackEnd.domain.model.Ticket_Reservation;

public interface Ticket_ReservationRepository extends JpaRepository<Ticket_Reservation, Long> {
}
