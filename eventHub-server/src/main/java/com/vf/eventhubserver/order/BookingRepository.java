package com.vf.eventhubserver.order;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.vf.eventhubserver.persona.Customer;

public interface BookingRepository extends JpaRepository<Booking, Long> {

    Optional<Booking> findByCustomerAndReservations(Customer customer,
            TicketReservation ticketReservation);

}
