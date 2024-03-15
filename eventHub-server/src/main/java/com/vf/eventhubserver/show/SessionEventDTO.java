package com.vf.eventhubserver.show;

import java.time.LocalDateTime;
import com.vf.eventhubserver.venue.ConfigurationHallDTO;

public record SessionEventDTO(Long id, LocalDateTime dateAndTimeStartSessionEvent,
        int durationInMinutes, EventDTO event, ConfigurationHallDTO configurationHall) {
}
