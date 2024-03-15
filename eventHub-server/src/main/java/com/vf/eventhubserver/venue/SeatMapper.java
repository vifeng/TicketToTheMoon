package com.vf.eventhubserver.venue;

import java.util.List;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SeatMapper {

        SeatDTO toDTO(Seat seat);

        Seat toEntity(SeatDTO seatDTO);

        @IterableMapping(elementTargetType = SeatDTO.class)
        List<SeatDTO> toDTOs(Iterable<Seat> seats);

        @IterableMapping(elementTargetType = Seat.class)
        List<Seat> toEntities(Iterable<SeatDTO> seatDTOs);

}
