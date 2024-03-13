package com.vf.eventhubserver.domain.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;

import com.vf.eventhubserver.domain.model.TicketReservation;
import com.vf.eventhubserver.domain.model.TicketReservationKey;

public interface TicketReservationRepository
                extends JpaRepository<TicketReservation, TicketReservationKey> {

        @NonNull
        Optional<TicketReservation> findById(@NonNull TicketReservationKey id);

}
