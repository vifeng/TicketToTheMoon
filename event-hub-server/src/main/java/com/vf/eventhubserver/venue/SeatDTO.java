package com.vf.eventhubserver.venue;

import com.vf.eventhubserver.tarification.CategoryTariffDTO;

public record SeatDTO(
    Long id,
    boolean isSeated,
    int seatNo,
    char rowNo,
    CategorySpatialDTO categorySpatial,
    CategoryTariffDTO categoryTariff,
    SeatStatusDTO seatStatus,
    ConfigurationHallDTO configurationHall) {}
