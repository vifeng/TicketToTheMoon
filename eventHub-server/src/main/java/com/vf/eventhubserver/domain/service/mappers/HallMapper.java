package com.vf.eventhubserver.domain.service.mappers;

import java.util.List;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import com.vf.eventhubserver.domain.dto.HallDTO;
import com.vf.eventhubserver.domain.model.Hall;

@Mapper(componentModel = "spring")
public interface HallMapper {


    HallDTO toDTO(Hall hall);

    Hall toEntity(HallDTO hallDTO);

    @IterableMapping(elementTargetType = HallDTO.class)
    List<HallDTO> toDTOs(Iterable<Hall> halls);

    @IterableMapping(elementTargetType = Hall.class)
    List<Hall> toEntities(Iterable<HallDTO> hallDTOs);

}
