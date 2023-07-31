package com.vf.tickettothemoon_BackEnd.domain.dto;

import org.springframework.data.annotation.Id;

public record ConfigurationHallDTO(@Id Long id, String name, int configuration_capacity,
        HallDTO hall) {

}
