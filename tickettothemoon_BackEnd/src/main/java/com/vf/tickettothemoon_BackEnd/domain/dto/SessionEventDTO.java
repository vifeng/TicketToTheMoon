package com.vf.tickettothemoon_BackEnd.domain.dto;

import java.time.LocalDateTime;
import java.util.List;

public record SessionEventDTO(Long id, LocalDateTime dateAndTimeStartSessionEvent,
        int durationInMinutes, EventDTO event, ConfigurationHallDTO configurationHall,
        List<SeatDTO> seats) {
}
