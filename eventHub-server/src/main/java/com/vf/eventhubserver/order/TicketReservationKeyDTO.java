package com.vf.eventhubserver.order;

import com.vf.eventhubserver.show.SessionEventDTO;
import com.vf.eventhubserver.venue.SeatDTO;

public record TicketReservationKeyDTO(SeatDTO seatId, SessionEventDTO sessionEventId) {
}
