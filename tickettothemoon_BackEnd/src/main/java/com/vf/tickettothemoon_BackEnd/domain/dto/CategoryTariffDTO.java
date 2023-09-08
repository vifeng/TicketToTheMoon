package com.vf.tickettothemoon_BackEnd.domain.dto;

import com.vf.tickettothemoon_BackEnd.domain.model.Price;

public record CategoryTariffDTO(Long id, String name, ConfigurationHallDTO configurationHall,
        Price price) {

}
