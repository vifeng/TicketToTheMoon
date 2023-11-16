package com.vf.tickettothemoon_BackEnd.domain.service.mappers;

import java.util.List;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import com.vf.tickettothemoon_BackEnd.domain.dto.Ticket_ReservationDTO;
import com.vf.tickettothemoon_BackEnd.domain.dto.Ticket_ReservationIdDTO;
import com.vf.tickettothemoon_BackEnd.domain.model.Ticket_Reservation;
import com.vf.tickettothemoon_BackEnd.domain.model.Ticket_ReservationId;

@Mapper
public interface Ticket_ReservationMapper {
        Ticket_ReservationMapper INSTANCE = Mappers.getMapper(Ticket_ReservationMapper.class);

        Ticket_ReservationDTO toTicket_ReservationDTO(Ticket_Reservation ticket_Reservation);

        Ticket_Reservation toTicket_Reservation(Ticket_ReservationDTO ticket_ReservationDTO);

        @IterableMapping(elementTargetType = Ticket_ReservationDTO.class)
        List<Ticket_ReservationDTO> toTicket_ReservationDTOs(
                        Iterable<Ticket_Reservation> ticket_Reservations);

        @IterableMapping(elementTargetType = Ticket_Reservation.class)
        List<Ticket_Reservation> toTicket_Reservations(
                        Iterable<Ticket_ReservationDTO> ticket_ReservationDTOs);

        //////////////////////////////
        // Méthodes de mappage pour ticket_reservationId
        //////////////////////////////

        // Utilisation de Ticket_ReservationIdMapper pour mapper Ticket_ReservationId
        Ticket_ReservationIdMapper TICKET_RESERVATIONID_MAPPER =
                        Mappers.getMapper(Ticket_ReservationIdMapper.class);

        // Mappage for Ticket_ReservationId with TICKET_RESERVATIONID and its DTO
        default Ticket_ReservationIdDTO toTicket_ReservationIdDTO(
                        Ticket_ReservationId ticket_ReservationId) {
                if (ticket_ReservationId == null) {
                        return null;
                }
                return TICKET_RESERVATIONID_MAPPER.toTicket_ReservationIdDTO(ticket_ReservationId);
        }

        default Ticket_ReservationId toTicket_ReservationId(
                        Ticket_ReservationIdDTO ticket_ReservationIdDTO) {
                if (ticket_ReservationIdDTO == null) {
                        return null;
                }
                return TICKET_RESERVATIONID_MAPPER.toTicket_ReservationId(ticket_ReservationIdDTO);
        }

        // Mappage for Ticket_ReservationId with TICKET_RESERVATION and its DTO
        default Ticket_ReservationIdDTO toTicket_ReservationIdDTO(
                        Ticket_Reservation ticket_Reservation) {
                if (ticket_Reservation == null) {
                        return null;
                }
                return toTicket_ReservationIdDTO(ticket_Reservation.getId());
        }

        default Ticket_ReservationId toTicket_ReservationId(
                        Ticket_ReservationDTO ticket_ReservationDTO) {
                if (ticket_ReservationDTO == null) {
                        return null;
                }
                return toTicket_ReservationId(ticket_ReservationDTO.getId());
        }

}
