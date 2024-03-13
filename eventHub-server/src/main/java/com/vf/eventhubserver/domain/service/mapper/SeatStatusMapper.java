package com.vf.eventhubserver.domain.service.mapper;

import java.util.List;

import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;

import com.vf.eventhubserver.domain.dto.SeatStatusDTO;
import com.vf.eventhubserver.domain.model.SeatStatus;

@Mapper(componentModel = "spring")
public interface SeatStatusMapper {

    SeatStatusDTO toDTO(SeatStatus seatStatus);

    SeatStatus toEntity(SeatStatusDTO seatStatusDTO);

    @IterableMapping(elementTargetType = SeatStatusDTO.class)
    List<SeatStatusDTO> toDTOs(Iterable<SeatStatus> seatStatuses);

    @IterableMapping(elementTargetType = SeatStatus.class)
    List<SeatStatus> toEntities(Iterable<SeatStatusDTO> seatStatusDTOs);

}
