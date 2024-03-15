package com.vf.eventhubserver.venue;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SeatStatusRepository extends JpaRepository<SeatStatus, Long> {
    public SeatStatus findByName(String name);
}
