package com.vf.tickettothemoon_BackEnd.domain.dto;

public record HallDTO(Long id, String name, int capacityOfHall, VenueDTO venue) {
}
