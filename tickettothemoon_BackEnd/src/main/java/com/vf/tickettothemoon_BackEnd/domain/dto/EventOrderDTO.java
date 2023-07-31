package com.vf.tickettothemoon_BackEnd.domain.dto;

import java.time.LocalDateTime;
import java.util.Date;
import org.springframework.data.annotation.Id;

public record EventOrderDTO(@Id Long id, LocalDateTime dateOrder, Date dateSessionEvent,
                boolean isPaid, int fees, double totalHT, double totalTTC, ClientDTO client) {
}
