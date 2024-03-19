package com.vf.eventhubserver.show;

import com.vf.eventhubserver.venue.ConfigurationHallDTO;
import java.time.LocalDateTime;

public record SessionEventDTO(
    Long id,
    LocalDateTime dateAndTimeStartSessionEvent,
    int durationInMinutes,
    EventDTO event,
    ConfigurationHallDTO configurationHall) {}
