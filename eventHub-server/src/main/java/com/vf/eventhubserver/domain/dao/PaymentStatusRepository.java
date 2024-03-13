package com.vf.eventhubserver.domain.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vf.eventhubserver.domain.model.PaymentStatus;

public interface PaymentStatusRepository extends JpaRepository<PaymentStatus, Long> {

        Optional<PaymentStatus> findByPaymentStatusName(String name);

}
