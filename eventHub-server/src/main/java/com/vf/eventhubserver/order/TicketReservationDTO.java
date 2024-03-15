package com.vf.eventhubserver.order;

public record TicketReservationDTO(TicketReservationKeyDTO ticketReservationKey, boolean isBooked) {
}
