package com.vf.eventhubserver.domain.dao;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
import com.vf.eventhubserver.domain.model.Ticket_Reservation;
import com.vf.eventhubserver.domain.model.Ticket_ReservationKey;

public interface Ticket_ReservationRepository
                extends JpaRepository<Ticket_Reservation, Ticket_ReservationKey> {

        @NonNull
        Optional<Ticket_Reservation> findById(@NonNull Ticket_ReservationKey id);

}