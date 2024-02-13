package com.vf.tickettothemoon_BackEnd.domain.dao;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.vf.tickettothemoon_BackEnd.domain.model.PaymentStatus_category;

public interface PaymentStatus_categoryRepository
                extends JpaRepository<PaymentStatus_category, Long> {

        Optional<PaymentStatus_category> findByPaymentStatusName(String name);

}
