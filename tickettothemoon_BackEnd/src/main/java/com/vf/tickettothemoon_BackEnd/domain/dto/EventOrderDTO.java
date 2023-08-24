package com.vf.tickettothemoon_BackEnd.domain.dto;

import java.time.LocalDate;

public record EventOrderDTO(Long id, LocalDate dateOrder, LocalDate dateSessionEvent,
        boolean isPaid, int fees, double totalHT, double totalTTC, ClientDTO client) {
}
