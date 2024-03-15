package com.vf.eventhubserver.order;

import java.sql.Timestamp;
import java.util.Set;
import com.vf.eventhubserver.persona.Customer;

public record BookingDTO(Long id, Timestamp bookingCreationTimestamp, Customer customer,
        Set<TicketReservation> reservations) {

}
