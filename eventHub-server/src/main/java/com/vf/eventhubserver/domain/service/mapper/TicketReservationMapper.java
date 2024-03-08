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

        @Mapping(source = "ticket_Reservation.id", target = "ticket_ReservationKey")
        TicketReservationDTO toDTO(TicketReservation ticket_Reservation);

        @Mapping(source = "ticket_ReservationDTO.ticket_ReservationKey", target = "id")
        TicketReservation toEntity(TicketReservationDTO ticket_ReservationDTO);

        @IterableMapping(elementTargetType = TicketReservationDTO.class)
        Set<TicketReservationDTO> toDTOs(Iterable<TicketReservation> ticket_Reservations);

        @IterableMapping(elementTargetType = TicketReservation.class)
        Set<TicketReservation> toEntities(Iterable<TicketReservationDTO> ticket_ReservationDTOs);

        // Composite key mappers
        // TOCHECK : not sure if this is needed
        TicketReservationKeyDTO map(TicketReservationKey ticket_ReservationKey);

        TicketReservationKey map(TicketReservationKeyDTO ticket_ReservationKeyDTO);



}
