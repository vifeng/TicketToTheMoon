package com.vf.tickettothemoon_BackEnd.domain.dto;

import org.springframework.data.annotation.Id;

public record AreaDTO(@Id Long id, String name, int remaining_capacity,
                ConfigurationHallDTO configurationHall) {

}
