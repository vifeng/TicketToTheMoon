package com.vf.tickettothemoon_BackEnd.domain.dto;

import org.springframework.data.annotation.Id;

public record SeatDTO(@Id Long id, int number, int rowSeat, boolean reserved, boolean seated,
        CategoryDTO category, AreaDTO area) {
}
