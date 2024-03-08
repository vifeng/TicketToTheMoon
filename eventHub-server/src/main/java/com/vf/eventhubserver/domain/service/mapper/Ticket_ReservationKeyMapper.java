package com.vf.eventhubserver.domain.service.mapper;

import java.util.List;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import com.vf.eventhubserver.domain.dto.Ticket_ReservationKeyDTO;
import com.vf.eventhubserver.domain.model.Ticket_ReservationKey;

@Mapper(componentModel = "spring")
public interface Ticket_ReservationKeyMapper {

        Ticket_ReservationKeyDTO toDTO(Ticket_ReservationKey ticket_ReservationKey);

        Ticket_ReservationKey toEntity(Ticket_ReservationKeyDTO ticket_ReservationKeyDTO);

        @IterableMapping(elementTargetType = Ticket_ReservationKeyDTO.class)
        List<Ticket_ReservationKeyDTO> toDTOs(
                        Iterable<Ticket_ReservationKey> ticket_ReservationKeys);

        @IterableMapping(elementTargetType = Ticket_ReservationKey.class)
        List<Ticket_ReservationKey> toEntities(
                        Iterable<Ticket_ReservationKeyDTO> ticket_ReservationKeyDTOs);

}
