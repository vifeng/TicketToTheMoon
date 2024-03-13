package com.vf.eventhubserver.domain.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vf.eventhubserver.domain.model.Hall;

public interface HallRepository extends JpaRepository<Hall, Long> {

    public List<Hall> findAllHallsByVenueId(Long venueId);

    public List<Hall> findAllByOrderByVenueIdAsc();
}
