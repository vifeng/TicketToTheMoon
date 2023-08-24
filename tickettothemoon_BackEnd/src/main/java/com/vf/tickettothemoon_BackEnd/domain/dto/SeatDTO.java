package com.vf.tickettothemoon_BackEnd.domain.dto;

import com.vf.tickettothemoon_BackEnd.domain.model.CategorySpatial;
import com.vf.tickettothemoon_BackEnd.domain.model.CategoryTariff;

public record SeatDTO(Long id, boolean seated, int numberOfTheSeat, char rowOfTheSeat,
                CategorySpatial category, CategoryTariff categoryTariff, boolean booked) {
}
