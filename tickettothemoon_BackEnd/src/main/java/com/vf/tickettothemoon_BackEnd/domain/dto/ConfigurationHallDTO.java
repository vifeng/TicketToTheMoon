package com.vf.tickettothemoon_BackEnd.domain.dto;

public record ConfigurationHallDTO(Long id, String name, HallDTO hall,
                int capacityOfConfiguration) {

        public ConfigurationHallDTO checkConstructor() {
                return this.checkConstructor();
        }

}
