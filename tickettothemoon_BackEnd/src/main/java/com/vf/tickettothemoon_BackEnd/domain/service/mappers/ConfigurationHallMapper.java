package com.vf.tickettothemoon_BackEnd.domain.service.mappers;

import java.util.List;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import com.vf.tickettothemoon_BackEnd.domain.dto.ConfigurationHallDTO;
import com.vf.tickettothemoon_BackEnd.domain.model.ConfigurationHall;

@Mapper
public interface ConfigurationHallMapper {

        ConfigurationHallMapper INSTANCE = Mappers.getMapper(ConfigurationHallMapper.class);

        ConfigurationHallDTO toConfigurationHallDTO(ConfigurationHall configurationHall);

        ConfigurationHall toConfigurationHall(ConfigurationHallDTO configurationHallDTO);

        @IterableMapping(elementTargetType = ConfigurationHallDTO.class)
        List<ConfigurationHallDTO> toConfigurationHallDTOs(
                        Iterable<ConfigurationHall> configurationHalls);

        @IterableMapping(elementTargetType = ConfigurationHall.class)
        List<ConfigurationHall> toConfigurationHalls(
                        Iterable<ConfigurationHallDTO> configurationHallDTOs);

}