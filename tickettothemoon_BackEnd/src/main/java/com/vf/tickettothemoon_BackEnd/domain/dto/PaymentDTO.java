package com.vf.tickettothemoon_BackEnd.domain.dto;

import java.time.LocalDateTime;

public record PaymentDTO(Long id, LocalDateTime paymentDateTime,
        Ticket_ReservationDTO ticket_Reservation,
        PaymentStatus_categoryDTO paymentStatus_category) {

}
