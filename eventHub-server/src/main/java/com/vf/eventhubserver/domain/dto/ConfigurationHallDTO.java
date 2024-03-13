package com.vf.eventhubserver.domain.dto;

public record ConfigurationHallDTO(Long id, String name, HallDTO hall,
                int capacityOfConfiguration) {

        public ConfigurationHallDTO checkConstructor() {
                return this.checkConstructor();
        }
}
