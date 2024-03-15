package com.vf.eventhubserver.order;

import java.util.List;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;

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
