package com.vf.tickettothemoon_BackEnd.domain.service.mappers;

import java.util.List;
import org.mapstruct.AfterMapping;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import com.vf.tickettothemoon_BackEnd.domain.dto.ConfigurationHallDTO;
import com.vf.tickettothemoon_BackEnd.domain.model.ConfigurationHall;

@Mapper(componentModel = "spring")
public interface ConfigurationHallMapper {

        ConfigurationHallDTO toConfigurationHallDTO(ConfigurationHall configurationHall);

        @AfterMapping
        default void checkConstructorAfterToDto(ConfigurationHall entity,
                        @MappingTarget ConfigurationHallDTO dto) {
                dto.checkConstructor();
        }

        ConfigurationHall toConfigurationHall(ConfigurationHallDTO configurationHallDTO);

        @AfterMapping
        default void checkConstructorAfterToEntity(ConfigurationHallDTO dto,
                        @MappingTarget ConfigurationHall entity) {
                entity.checkConstructor();
        }


        @IterableMapping(elementTargetType = ConfigurationHallDTO.class)
        List<ConfigurationHallDTO> toConfigurationHallDTOs(
                        Iterable<ConfigurationHall> configurationHalls);

        @IterableMapping(elementTargetType = ConfigurationHall.class)
        List<ConfigurationHall> toConfigurationHalls(
                        Iterable<ConfigurationHallDTO> configurationHallDTOs);



}
