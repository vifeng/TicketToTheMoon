package com.vf.eventhubserver.domain.dto;

public record SeatDTO(Long id, boolean isSeated, int seatNo, char rowNo,
                CategorySpatialDTO categorySpatial, CategoryTariffDTO categoryTariff,
                SeatStatusDTO seatStatus, ConfigurationHallDTO configurationHall) {}
