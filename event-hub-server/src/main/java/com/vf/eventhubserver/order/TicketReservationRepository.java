package com.vf.eventhubserver.order;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;

public interface TicketReservationRepository
    extends JpaRepository<TicketReservation, TicketReservationKey> {

  @NonNull
  Optional<TicketReservation> findById(@NonNull TicketReservationKey id);
}
