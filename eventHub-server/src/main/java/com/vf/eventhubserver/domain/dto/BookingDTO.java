package com.vf.eventhubserver.domain.dto;

import java.sql.Timestamp;
import java.util.Set;
import com.vf.eventhubserver.domain.model.Customer;
import com.vf.eventhubserver.domain.model.Ticket_Reservation;

public record BookingDTO(Long id, Timestamp booking_creationTimestamp, Customer customer,
                Set<Ticket_Reservation> reservations) {

}