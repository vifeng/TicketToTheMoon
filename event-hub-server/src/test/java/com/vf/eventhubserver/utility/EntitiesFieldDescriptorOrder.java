package com.vf.eventhubserver.utility;

import com.vf.eventhubserver.order.Booking;
import com.vf.eventhubserver.order.TicketReservation;
import com.vf.eventhubserver.order.TicketReservationKey;
import java.util.Map;
import org.springframework.restdocs.constraints.ConstraintDescriptions;
import org.springframework.restdocs.payload.FieldDescriptor;

public class EntitiesFieldDescriptorOrder implements EntitiesFieldDescriptor {
  ConstraintDescriptions descBooking = new ConstraintDescriptions(Booking.class);
  ConstraintDescriptions descTicketReservation =
      new ConstraintDescriptions(TicketReservation.class);
  ConstraintDescriptions descTicketReservationKey =
      new ConstraintDescriptions(TicketReservationKey.class);

  public FieldDescriptor[] generateBookingFields(boolean includeId) {
    Map<String, String> customDescriptions =
        Map.of(
            "id", "",
            "bookingCreationTimestamp", "",
            "totalPriceHt", "",
            "reservations", "An array of reservations made for the booking. ",
            "customer", "The customer who made the booking. ");
    return generateFields(
        includeId,
        "booking",
        descBooking,
        customDescriptions,
        new String[] {"bookingCreationTimestamp", "totalPriceHt", "reservations", "customer"});
  }

  public FieldDescriptor[] generateTicketReservationFields(boolean includeId) {
    Map<String, String> customDescriptions =
        Map.of(
            "id", "the ticket reservation ID is composed of the seatId and the sessionEventId. ",
            "isBooked", "The status of the ticket reservation. ");
    return generateFields(
        includeId,
        "ticket reservation",
        descTicketReservation,
        customDescriptions,
        new String[] {"isBooked"});
  }

  public FieldDescriptor[] generateTicketReservationKeyFields(boolean includeId) {
    Map<String, String> customDescriptions =
        Map.of(
            "seatId", "The seat ID of the ticket reservation. ",
            "sessionEventId", "The session event ID of the ticket reservation. ");
    return generateFields(
        includeId,
        "ticket reservation key",
        descTicketReservationKey,
        customDescriptions,
        new String[] {"seatId", "sessionEventId"});
  }
}
