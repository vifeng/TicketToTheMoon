package com.vf.eventhubserver.domain.dto;

public record TicketReservationDTO(TicketReservationKeyDTO ticket_ReservationKey,
        boolean isBooked) {

}
