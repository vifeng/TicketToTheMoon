package com.vf.tickettothemoon_BackEnd.domain.service.mappers;

import java.util.List;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import com.vf.tickettothemoon_BackEnd.domain.dto.ConfigurationHallDTO;
import com.vf.tickettothemoon_BackEnd.domain.dto.HallDTO;
import com.vf.tickettothemoon_BackEnd.domain.model.ConfigurationHall;
import com.vf.tickettothemoon_BackEnd.domain.model.Hall;

@Mapper(componentModel = "spring")
public interface ConfigurationHallMapper {

        @Mapping(target = "hall", source = "hall", qualifiedByName = "toHallDTO")
        default ConfigurationHallDTO toConfigurationHallDTO(ConfigurationHall configurationHall) {
                return new ConfigurationHallDTO.Builder().id(configurationHall.getId())
                                .name(configurationHall.getName())
                                .hall(mapHallToDTO(configurationHall.getHall()))
                                .capacityOfConfiguration(
                                                configurationHall.getCapacityOfConfiguration())
                                .build();
        }

        @Mapping(target = "hall", source = "hall", qualifiedByName = "toHall")
        default ConfigurationHall toConfigurationHall(ConfigurationHallDTO configurationHallDTO) {
                return new ConfigurationHall.Builder().id(configurationHallDTO.id())
                                .name(configurationHallDTO.name())
                                .hall(mapHall(configurationHallDTO.hall()))
                                .capacityOfConfiguration(
                                                configurationHallDTO.capacityOfConfiguration())
                                .build();
        };

        @IterableMapping(elementTargetType = ConfigurationHallDTO.class)
        List<ConfigurationHallDTO> toConfigurationHallDTOs(
                        Iterable<ConfigurationHall> configurationHalls);

        @IterableMapping(elementTargetType = ConfigurationHall.class)
        List<ConfigurationHall> toConfigurationHalls(
                        Iterable<ConfigurationHallDTO> configurationHallDTOs);


        @Named("toHallDTO")
        default HallDTO mapHallToDTO(Hall hall) {
                HallMapper hallMapper = new HallMapperImpl();
                return hallMapper.toHallDTO(hall);

        }

        @Named("toHall")
        default Hall mapHall(HallDTO hallDTO) {
                HallMapper hallMapper = new HallMapperImpl();
                return hallMapper.toHall(hallDTO);

        }
}
