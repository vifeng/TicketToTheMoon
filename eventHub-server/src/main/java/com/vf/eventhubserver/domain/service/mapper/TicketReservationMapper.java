package com.vf.eventhubserver.domain.service.mapper;

import java.util.Set;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import com.vf.eventhubserver.domain.dto.TicketReservationDTO;
import com.vf.eventhubserver.domain.dto.TicketReservationKeyDTO;
import com.vf.eventhubserver.domain.model.TicketReservation;
import com.vf.eventhubserver.domain.model.TicketReservationKey;

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

        // Composite key mappers
        // TOCHECK : not sure if this is needed
        TicketReservationKeyDTO map(TicketReservationKey ticketReservationKey);

        TicketReservationKey map(TicketReservationKeyDTO ticketReservationKeyDTO);



}
