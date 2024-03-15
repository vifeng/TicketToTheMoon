package com.vf.eventhubserver.venue;

public record HallDTO(Long id, String name, int capacityOfHall, VenueDTO venue) {
}
