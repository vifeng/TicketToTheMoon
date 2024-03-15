package com.vf.eventhubserver.venue;

import java.util.List;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SeatStatusMapper {

    SeatStatusDTO toDTO(SeatStatus seatStatus);

    SeatStatus toEntity(SeatStatusDTO seatStatusDTO);

    @IterableMapping(elementTargetType = SeatStatusDTO.class)
    List<SeatStatusDTO> toDTOs(Iterable<SeatStatus> seatStatuses);

    @IterableMapping(elementTargetType = SeatStatus.class)
    List<SeatStatus> toEntities(Iterable<SeatStatusDTO> seatStatusDTOs);

}
