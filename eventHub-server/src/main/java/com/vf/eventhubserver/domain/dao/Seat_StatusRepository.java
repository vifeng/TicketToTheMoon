package com.vf.eventhubserver.domain.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import com.vf.eventhubserver.domain.model.Seat_Status;

public interface Seat_StatusRepository extends JpaRepository<Seat_Status, Long> {
    public Seat_Status findByName(String name);
}
