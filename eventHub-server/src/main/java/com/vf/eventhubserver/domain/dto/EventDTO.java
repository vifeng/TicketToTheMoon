package com.vf.eventhubserver.domain.dto;

import java.time.LocalDate;

public record EventDTO(Long id, String name, String description, LocalDate dateStart,
        LocalDate dateEnd, String closedDay) {}
