package com.vf.eventhubserver.show;

import java.time.LocalDate;

public record EventDTO(
    Long id,
    String name,
    String description,
    LocalDate dateStart,
    LocalDate dateEnd,
    String closedDay) {}
