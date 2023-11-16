package com.vf.tickettothemoon_BackEnd.domain.dto;

public record SeatDTO(Long id, boolean isSeated, int seatNo, char rowNo,
        CategorySpatialDTO categorySpatial, CategoryTariffDTO categoryTariff,
        Seat_StatusDTO seat_Status, ConfigurationHallDTO configurationHall) {

}
