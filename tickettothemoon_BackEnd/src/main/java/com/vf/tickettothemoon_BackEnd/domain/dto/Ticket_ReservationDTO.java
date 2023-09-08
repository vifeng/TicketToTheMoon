package com.vf.tickettothemoon_BackEnd.domain.dto;

import java.time.LocalDate;

public record Ticket_ReservationDTO(Long id, LocalDate dateOrder, LocalDate dateSessionEvent,
        boolean isPaid, int fees, double totalHT, double totalTTC, CustomerDTO customer) {
}
