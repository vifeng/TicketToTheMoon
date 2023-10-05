package com.vf.tickettothemoon_BackEnd.domain.service.mappers;

import java.util.List;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import com.vf.tickettothemoon_BackEnd.domain.dto.SeatDTO;
import com.vf.tickettothemoon_BackEnd.domain.model.Seat;


@Mapper
public interface SeatMapper {

    SeatMapper INSTANCE = Mappers.getMapper(SeatMapper.class);


    SeatDTO toSeatDTO(Seat seat);

    Seat toSeat(SeatDTO seatDTO);

    @IterableMapping(elementTargetType = SeatDTO.class)
    List<SeatDTO> toSeatDTOs(Iterable<Seat> seats);

    @IterableMapping(elementTargetType = Seat.class)
    List<Seat> toSeats(Iterable<SeatDTO> seatDTOs);



}
