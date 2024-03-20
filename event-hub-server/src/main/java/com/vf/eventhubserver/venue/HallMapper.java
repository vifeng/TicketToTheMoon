package com.vf.eventhubserver.venue;

import java.util.List;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface HallMapper {

  HallDTO toDTO(Hall hall);

  Hall toEntity(HallDTO hallDTO);

  @IterableMapping(elementTargetType = HallDTO.class)
  List<HallDTO> toDTOs(Iterable<Hall> halls);

  @IterableMapping(elementTargetType = Hall.class)
  List<Hall> toEntities(Iterable<HallDTO> hallDTOs);
}
