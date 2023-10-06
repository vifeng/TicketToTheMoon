package com.vf.tickettothemoon_BackEnd.domain.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vf.tickettothemoon_BackEnd.domain.model.Booking;

public interface BookingRepository extends JpaRepository<Booking, Long> {

}
