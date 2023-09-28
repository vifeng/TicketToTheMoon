package com.vf.tickettothemoon_BackEnd.domain.dto;

import com.vf.tickettothemoon_BackEnd.domain.model.TarificationDTO;
public record CategoryTariffDTO(Long id, String name,
                TarificationDTO tarification) {

}
