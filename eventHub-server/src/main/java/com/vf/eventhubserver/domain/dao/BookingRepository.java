package com.vf.eventhubserver.domain.dao;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.vf.eventhubserver.domain.model.Booking;
import com.vf.eventhubserver.domain.model.Customer;
import com.vf.eventhubserver.domain.model.TicketReservation;

public interface BookingRepository extends JpaRepository<Booking, Long> {

    Optional<Booking> findByCustomerAndReservations(Customer customer,
            TicketReservation ticketReservation);

}
