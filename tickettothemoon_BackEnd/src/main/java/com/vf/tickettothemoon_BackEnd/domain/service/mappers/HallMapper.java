package com.vf.tickettothemoon_BackEnd.domain.service.mappers;

import java.util.List;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import com.vf.tickettothemoon_BackEnd.domain.dto.HallDTO;
import com.vf.tickettothemoon_BackEnd.domain.model.Hall;

@Mapper(componentModel = "spring")
public interface HallMapper {


    HallDTO toHallDTO(Hall hall);

    Hall toHall(HallDTO hallDTO);

    @IterableMapping(elementTargetType = HallDTO.class)
    List<HallDTO> toHallDTOs(Iterable<Hall> halls);

    @IterableMapping(elementTargetType = Hall.class)
    List<Hall> toHalls(Iterable<HallDTO> hallDTOs);

}
