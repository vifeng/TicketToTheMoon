package com.vf.tickettothemoon_BackEnd.domain.dao;


import org.springframework.data.repository.CrudRepository;
import com.vf.tickettothemoon_BackEnd.domain.model.Venue;

public interface VenueRepository extends CrudRepository<Venue, Long> {
}
