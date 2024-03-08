package com.vf.eventhubserver.domain.service.mapper;

import java.util.List;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import com.vf.eventhubserver.domain.dto.TicketReservationKeyDTO;
import com.vf.eventhubserver.domain.model.TicketReservationKey;

@Mapper(componentModel = "spring")
public interface TicketReservationKeyMapper {

        TicketReservationKeyDTO toDTO(TicketReservationKey ticket_ReservationKey);

        TicketReservationKey toEntity(TicketReservationKeyDTO ticket_ReservationKeyDTO);

        @IterableMapping(elementTargetType = TicketReservationKeyDTO.class)
        List<TicketReservationKeyDTO> toDTOs(Iterable<TicketReservationKey> ticket_ReservationKeys);

        @IterableMapping(elementTargetType = TicketReservationKey.class)
        List<TicketReservationKey> toEntities(
                        Iterable<TicketReservationKeyDTO> ticket_ReservationKeyDTOs);

}
