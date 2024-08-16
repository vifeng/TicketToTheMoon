package com.vf.eventhubserver.venue;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SeatRepository extends JpaRepository<Seat, Long> {

  List<Seat> findByConfigurationHallId(Long configurationHallId);
}
