package com.vf.tickettothemoon_BackEnd.domain.dao;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.vf.tickettothemoon_BackEnd.domain.model.Hall;

public interface HallRepository extends JpaRepository<Hall, Long> {

    public List<Hall> findAllHallsByVenueId(Long venueId);

    // find all halls and order by venue id
    public List<Hall> findAllByOrderByVenueIdAsc();
}
