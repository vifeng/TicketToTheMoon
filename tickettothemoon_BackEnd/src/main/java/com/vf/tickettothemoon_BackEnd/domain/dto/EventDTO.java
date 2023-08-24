package com.vf.tickettothemoon_BackEnd.domain.dto;

import java.time.LocalDate;

public record EventDTO(Long id, String name, LocalDate dateStart, LocalDate dateEnd,
        String closedDay, ConfigurationHallDTO configurationHall) {


}
