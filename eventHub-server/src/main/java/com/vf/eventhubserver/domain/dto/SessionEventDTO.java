package com.vf.eventhubserver.domain.dto;

import java.time.LocalDateTime;

public record SessionEventDTO(Long id, LocalDateTime dateAndTimeStartSessionEvent,
        int durationInMinutes, EventDTO event, ConfigurationHallDTO configurationHall) {
}
