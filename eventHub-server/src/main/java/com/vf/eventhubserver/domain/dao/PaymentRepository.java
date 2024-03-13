package com.vf.eventhubserver.domain.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vf.eventhubserver.domain.model.Payment;

public interface PaymentRepository extends JpaRepository<Payment, Long> {

    Optional<Payment> findByBookingId(Long id);
}
