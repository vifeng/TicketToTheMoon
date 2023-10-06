package com.vf.tickettothemoon_BackEnd.domain.dto;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Set;

public record BookingDTO(Long id, Timestamp booking_creationTimestamp, LocalDateTime booking_expiryDateTime,
                LocalDateTime expiryTime, double total_price_ht, CustomerDTO customer,
                Set<Ticket_ReservationDTO> reservations) {

}
