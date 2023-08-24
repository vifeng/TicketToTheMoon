package com.vf.tickettothemoon_BackEnd.domain.service.mappers;

import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import com.vf.tickettothemoon_BackEnd.domain.dto.SeatDTO;
import com.vf.tickettothemoon_BackEnd.domain.model.Seat;

@Mapper
public interface SeatMapper {

    SeatMapper INSTANCE = org.mapstruct.factory.Mappers.getMapper(SeatMapper.class);

    SeatDTO toSeatDTO(Seat seat);

    Seat toSeat(SeatDTO seatDTO);

    @IterableMapping(elementTargetType = SeatDTO.class)
    Iterable<SeatDTO> toSeatDTOs(Iterable<Seat> seats);

    @IterableMapping(elementTargetType = Seat.class)
    Iterable<Seat> toSeats(Iterable<SeatDTO> seatDTOs);
}
