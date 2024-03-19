package com.vf.eventhubserver.order;

import com.vf.eventhubserver.persona.Customer;
import java.sql.Timestamp;
import java.util.Set;

public record BookingDTO(
    Long id,
    Timestamp bookingCreationTimestamp,
    Customer customer,
    Set<TicketReservation> reservations) {}
