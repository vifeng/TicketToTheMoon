package com.vf.eventhubserver.domain.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vf.eventhubserver.domain.model.Seat;

public interface SeatRepository extends JpaRepository<Seat, Long> {}
