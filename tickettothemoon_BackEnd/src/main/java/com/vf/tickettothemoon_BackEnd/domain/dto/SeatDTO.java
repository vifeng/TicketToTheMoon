package com.vf.tickettothemoon_BackEnd.domain.dto;

import java.util.List;

public record SeatDTO(Long id, boolean isSeated, int seatNo, char rowNo,
                CategorySpatialDTO categorySpatial, CategoryTariffDTO categoryTariff,
                ConfigurationHallDTO configurationHall, List<SessionEventDTO> sessionEvents) {

}
