package com.vf.eventhubserver.domain.dao;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.vf.eventhubserver.domain.model.PaymentStatus_category;

public interface PaymentStatus_categoryRepository
                extends JpaRepository<PaymentStatus_category, Long> {

        Optional<PaymentStatus_category> findByPaymentStatusName(String name);

}
