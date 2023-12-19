package com.vf.tickettothemoon_BackEnd.domain.service.mappers;

import java.util.List;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import com.vf.tickettothemoon_BackEnd.domain.dto.SeatDTO;
import com.vf.tickettothemoon_BackEnd.domain.model.Seat;


@Mapper(componentModel = "spring")
public interface SeatMapper {

        SeatDTO toSeatDTO(Seat seat);

        Seat toSeat(SeatDTO seatDTO);

        @IterableMapping(elementTargetType = SeatDTO.class)
        List<SeatDTO> toSeatDTOs(Iterable<Seat> seats);

        @IterableMapping(elementTargetType = Seat.class)
        List<Seat> toSeats(Iterable<SeatDTO> seatDTOs);

}
