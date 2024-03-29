package com.vf.eventhubserver.venue;

import jakarta.validation.Valid;
import java.util.List;
import org.mapstruct.AfterMapping;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ConfigurationHallMapper {

  ConfigurationHallDTO toDTO(ConfigurationHall configurationHall);

  @AfterMapping
  default void checkConstructorAfterToDto(
      ConfigurationHall entity, @Valid @MappingTarget ConfigurationHallDTO dto) {
    dto.checkConstructor();
  }

  ConfigurationHall toEntity(ConfigurationHallDTO configurationHallDTO);

  @AfterMapping
  default void checkConstructorAfterToEntity(
      ConfigurationHallDTO dto, @Valid @MappingTarget ConfigurationHall entity) {
    entity.checkConstructor();
  }

  @IterableMapping(elementTargetType = ConfigurationHallDTO.class)
  List<ConfigurationHallDTO> toDTOs(Iterable<ConfigurationHall> configurationHalls);

  @IterableMapping(elementTargetType = ConfigurationHall.class)
  List<ConfigurationHall> toEntities(Iterable<ConfigurationHallDTO> configurationHallDTOs);
}
