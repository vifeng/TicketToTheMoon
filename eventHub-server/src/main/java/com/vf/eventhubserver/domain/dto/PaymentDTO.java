package com.vf.eventhubserver.domain.dto;

import java.time.LocalDateTime;

public record PaymentDTO(Long id, LocalDateTime paymentDateTime, BookingDTO booking,
        PaymentStatusDTO paymentStatus) {

}
