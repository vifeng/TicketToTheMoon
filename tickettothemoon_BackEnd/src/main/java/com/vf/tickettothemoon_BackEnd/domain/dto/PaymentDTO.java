package com.vf.tickettothemoon_BackEnd.domain.dto;

import java.time.LocalDateTime;

public record PaymentDTO(Long id, LocalDateTime paymentDateTime, BookingDTO booking,
                PaymentStatus_categoryDTO paymentStatus_category) {

}
