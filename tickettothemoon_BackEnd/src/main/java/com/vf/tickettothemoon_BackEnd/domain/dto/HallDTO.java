package com.vf.tickettothemoon_BackEnd.domain.dto;

import org.springframework.data.annotation.Id;

public record HallDTO(@Id Long id, String name, int capacity, VenueDTO venue) {

}
