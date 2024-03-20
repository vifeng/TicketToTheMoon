package com.vf.eventhubserver.order;

import java.util.Set;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TicketReservationMapper {

  @Mapping(source = "ticketReservation.id", target = "ticketReservationKey")
  TicketReservationDTO toDTO(TicketReservation ticketReservation);

  @Mapping(source = "ticketReservationDTO.ticketReservationKey", target = "id")
  TicketReservation toEntity(TicketReservationDTO ticketReservationDTO);

  @IterableMapping(elementTargetType = TicketReservationDTO.class)
  Set<TicketReservationDTO> toDTOs(Iterable<TicketReservation> ticketReservations);

  @IterableMapping(elementTargetType = TicketReservation.class)
  Set<TicketReservation> toEntities(Iterable<TicketReservationDTO> ticketReservationDTOs);

  TicketReservationKeyDTO map(TicketReservationKey ticketReservationKey);

  TicketReservationKey map(TicketReservationKeyDTO ticketReservationKeyDTO);
}
