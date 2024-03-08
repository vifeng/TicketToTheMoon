package com.vf.eventhubserver.domain.service.mapper;

import java.util.Set;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import com.vf.eventhubserver.domain.dto.Ticket_ReservationDTO;
import com.vf.eventhubserver.domain.dto.Ticket_ReservationKeyDTO;
import com.vf.eventhubserver.domain.model.Ticket_Reservation;
import com.vf.eventhubserver.domain.model.Ticket_ReservationKey;

@Mapper(componentModel = "spring")
public interface Ticket_ReservationMapper {

        @Mapping(source = "ticket_Reservation.id", target = "ticket_ReservationKey")
        Ticket_ReservationDTO toDTO(Ticket_Reservation ticket_Reservation);

        @Mapping(source = "ticket_ReservationDTO.ticket_ReservationKey", target = "id")
        Ticket_Reservation toEntity(Ticket_ReservationDTO ticket_ReservationDTO);

        @IterableMapping(elementTargetType = Ticket_ReservationDTO.class)
        Set<Ticket_ReservationDTO> toDTOs(Iterable<Ticket_Reservation> ticket_Reservations);

        @IterableMapping(elementTargetType = Ticket_Reservation.class)
        Set<Ticket_Reservation> toEntities(Iterable<Ticket_ReservationDTO> ticket_ReservationDTOs);

        // Composite key mappers
        // TOCHECK : not sure if this is needed
        Ticket_ReservationKeyDTO map(Ticket_ReservationKey ticket_ReservationKey);

        Ticket_ReservationKey map(Ticket_ReservationKeyDTO ticket_ReservationKeyDTO);



}
