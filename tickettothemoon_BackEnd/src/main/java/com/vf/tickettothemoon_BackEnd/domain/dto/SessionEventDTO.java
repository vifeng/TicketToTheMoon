package com.vf.tickettothemoon_BackEnd.domain.dto;

import java.time.LocalDateTime;

public record SessionEventDTO(Long id, LocalDateTime dateHourStartSessionEvent,
        int durationInMinutes, EventDTO event) {
}
