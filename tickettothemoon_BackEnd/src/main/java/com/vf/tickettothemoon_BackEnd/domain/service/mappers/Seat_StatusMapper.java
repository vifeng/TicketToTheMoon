package com.vf.tickettothemoon_BackEnd.domain.service.mappers;

import java.util.List;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import com.vf.tickettothemoon_BackEnd.domain.dto.Seat_StatusDTO;
import com.vf.tickettothemoon_BackEnd.domain.model.Seat_Status;

@Mapper
public interface Seat_StatusMapper {

    Seat_StatusMapper INSTANCE = Mappers.getMapper(Seat_StatusMapper.class);

    Seat_StatusDTO toSeat_StatusDTO(Seat_Status seat_Status);

    Seat_Status toSeat_Status(Seat_StatusDTO seat_StatusDTO);

    @IterableMapping(elementTargetType = Seat_StatusDTO.class)
    List<Seat_StatusDTO> toSeat_StatusDTOs(Iterable<Seat_Status> seat_Statuss);

    @IterableMapping(elementTargetType = Seat_Status.class)
    List<Seat_Status> toSeat_Statuss(Iterable<Seat_StatusDTO> seat_StatusDTOs);

}
