package com.vf.tickettothemoon_BackEnd.domain.dto;

import java.time.LocalDateTime;

public record Ticket_ReservationDTO(Long id, LocalDateTime dateOrder,
        LocalDateTime dateSessionEvent, boolean isPaid, int fees, double totalHT, double totalTTC,
        CustomerDTO customer) {
}
