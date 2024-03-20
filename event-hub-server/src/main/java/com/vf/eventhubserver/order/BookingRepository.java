package com.vf.eventhubserver.order;

import com.vf.eventhubserver.persona.Customer;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingRepository extends JpaRepository<Booking, Long> {

  Optional<Booking> findByCustomerAndReservations(
      Customer customer, TicketReservation ticketReservation);
}
