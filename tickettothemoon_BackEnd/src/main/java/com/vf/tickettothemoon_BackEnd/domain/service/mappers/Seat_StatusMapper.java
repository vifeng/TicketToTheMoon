package com.vf.tickettothemoon_BackEnd.domain.service.mappers;

import java.util.List;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import com.vf.tickettothemoon_BackEnd.domain.dto.Seat_StatusDTO;
import com.vf.tickettothemoon_BackEnd.domain.model.Seat_Status;

@Mapper(componentModel = "spring")
public interface Seat_StatusMapper {

    Seat_StatusDTO toDTO(Seat_Status seat_Status);

    Seat_Status toEntity(Seat_StatusDTO seat_StatusDTO);

    @IterableMapping(elementTargetType = Seat_StatusDTO.class)
    List<Seat_StatusDTO> toDTOs(Iterable<Seat_Status> seat_Statuss);

    @IterableMapping(elementTargetType = Seat_Status.class)
    List<Seat_Status> toEntities(Iterable<Seat_StatusDTO> seat_StatusDTOs);

}
