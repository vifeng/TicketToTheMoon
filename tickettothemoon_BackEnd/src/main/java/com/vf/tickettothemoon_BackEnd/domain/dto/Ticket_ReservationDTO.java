package com.vf.tickettothemoon_BackEnd.domain.dto;

import java.sql.Timestamp;
import java.time.LocalDateTime;

public record Ticket_ReservationDTO(Ticket_ReservationIdDTO id,
                Timestamp reservation_creationTimestamp, LocalDateTime reservation_expiryDateTime,
                LocalDateTime expiryTime, double total_price_ht, CustomerDTO customer,
                SessionEventDTO sessionEvent, SeatDTO seat) {
        public Ticket_ReservationIdDTO getId() {
                return id;
        }

}
