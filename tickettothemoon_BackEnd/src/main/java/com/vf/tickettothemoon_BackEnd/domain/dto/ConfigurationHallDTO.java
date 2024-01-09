package com.vf.tickettothemoon_BackEnd.domain.dto;

public record ConfigurationHallDTO(Long id, String name, HallDTO hall,
        int capacityOfConfiguration) {

    public static class Builder {
        private Long id;
        private String name;
        private HallDTO hall;
        private int capacityOfConfiguration;

        public Builder() {}

        public Builder(ConfigurationHallDTO configurationHallDTO) {
            this.id = configurationHallDTO.id();
            this.name = configurationHallDTO.name();
            this.hall = configurationHallDTO.hall();
            this.capacityOfConfiguration = configurationHallDTO.capacityOfConfiguration();
        }

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder hall(HallDTO hall) {
            this.hall = hall;
            return this;
        }

        public Builder capacityOfConfiguration(int capacityOfConfiguration) {
            this.capacityOfConfiguration = capacityOfConfiguration;
            return this;
        }

        public ConfigurationHallDTO build() {
            return new ConfigurationHallDTO(id, name, hall, capacityOfConfiguration);
        }
    }
}
