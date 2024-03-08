package com.vf.eventhubserver.domain.service.mapper;

import java.util.List;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import com.vf.eventhubserver.domain.dto.SeatStatusDTO;
import com.vf.eventhubserver.domain.model.SeatStatus;

@Mapper(componentModel = "spring")
public interface SeatStatusMapper {

    SeatStatusDTO toDTO(SeatStatus seat_Status);

    SeatStatus toEntity(SeatStatusDTO seat_StatusDTO);

    @IterableMapping(elementTargetType = SeatStatusDTO.class)
    List<SeatStatusDTO> toDTOs(Iterable<SeatStatus> seat_Statuss);

    @IterableMapping(elementTargetType = SeatStatus.class)
    List<SeatStatus> toEntities(Iterable<SeatStatusDTO> seat_StatusDTOs);

}
