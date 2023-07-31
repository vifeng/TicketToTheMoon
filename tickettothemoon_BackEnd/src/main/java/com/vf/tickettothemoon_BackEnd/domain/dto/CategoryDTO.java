package com.vf.tickettothemoon_BackEnd.domain.dto;

import org.springframework.data.annotation.Id;

public record CategoryDTO(@Id Long id, String name, AreaDTO area) {

}
