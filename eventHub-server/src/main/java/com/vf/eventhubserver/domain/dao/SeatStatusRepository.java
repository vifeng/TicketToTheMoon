package com.vf.eventhubserver.domain.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import com.vf.eventhubserver.domain.model.SeatStatus;

public interface SeatStatusRepository extends JpaRepository<SeatStatus, Long> {
    public SeatStatus findByName(String name);
}

