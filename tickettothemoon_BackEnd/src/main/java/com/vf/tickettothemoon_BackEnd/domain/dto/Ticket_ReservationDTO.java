package com.vf.tickettothemoon_BackEnd.domain.dto;

public record Ticket_ReservationDTO(SessionEventDTO sessionEvent, SeatDTO seat, boolean isBooked) {
    public Ticket_ReservationKeyDTO getId() {
        return this.getId();
    }
}
