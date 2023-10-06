package com.vf.tickettothemoon_BackEnd.domain.dto;

public record Ticket_ReservationDTO(Ticket_ReservationIdDTO id, SessionEventDTO sessionEvent,
                SeatDTO seat, boolean isBooked) {
        public Ticket_ReservationIdDTO getId() {
                return id;
        }

}
