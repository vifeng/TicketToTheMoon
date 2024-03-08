package com.vf.eventhubserver.domain.dto;

public record HallDTO(Long id, String name, int capacityOfHall, VenueDTO venue) {
}
