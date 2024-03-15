package com.vf.eventhubserver.venue;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HallRepository extends JpaRepository<Hall, Long> {

    public List<Hall> findAllHallsByVenueId(Long venueId);

    public List<Hall> findAllByOrderByVenueIdAsc();
}
