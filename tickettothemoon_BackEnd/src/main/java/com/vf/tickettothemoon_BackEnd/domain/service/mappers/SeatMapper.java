package com.vf.tickettothemoon_BackEnd.domain.service.mappers;

import java.util.List;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import com.vf.tickettothemoon_BackEnd.domain.dto.SeatDTO;
import com.vf.tickettothemoon_BackEnd.domain.model.Seat;


@Mapper(componentModel = "spring")
public interface SeatMapper {

        SeatDTO toDTO(Seat seat);

        Seat toEntity(SeatDTO seatDTO);

        @IterableMapping(elementTargetType = SeatDTO.class)
        List<SeatDTO> toDTOs(Iterable<Seat> seats);

        @IterableMapping(elementTargetType = Seat.class)
        List<Seat> toEntities(Iterable<SeatDTO> seatDTOs);

}
