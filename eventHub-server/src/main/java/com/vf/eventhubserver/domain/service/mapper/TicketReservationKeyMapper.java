package com.vf.eventhubserver.domain.service.mapper;

import java.util.List;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import com.vf.eventhubserver.domain.dto.TicketReservationKeyDTO;
import com.vf.eventhubserver.domain.model.TicketReservationKey;

@Mapper(componentModel = "spring")
public interface TicketReservationKeyMapper {

        TicketReservationKeyDTO toDTO(TicketReservationKey ticketReservationKey);

        TicketReservationKey toEntity(TicketReservationKeyDTO ticketReservationKeyDTO);

        @IterableMapping(elementTargetType = TicketReservationKeyDTO.class)
        List<TicketReservationKeyDTO> toDTOs(Iterable<TicketReservationKey> ticketReservationKeys);

        @IterableMapping(elementTargetType = TicketReservationKey.class)
        List<TicketReservationKey> toEntities(
                        Iterable<TicketReservationKeyDTO> ticketReservationKeyDTOs);

}
