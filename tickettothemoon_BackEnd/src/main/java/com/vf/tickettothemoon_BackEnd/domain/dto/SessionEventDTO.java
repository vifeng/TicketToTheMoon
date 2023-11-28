package com.vf.tickettothemoon_BackEnd.domain.dto;

import java.time.LocalDateTime;
import java.util.Set;

public record SessionEventDTO(Long id, LocalDateTime dateAndTimeStartSessionEvent,
                int durationInMinutes, EventDTO event, ConfigurationHallDTO configurationHall) {
        public Set<Ticket_ReservationDTO> getTicketReservations() {
                return this.getTicketReservations();
        }

        public Set<Ticket_ReservationDTO> setTicketReservations(
                        Set<Ticket_ReservationDTO> ticketReservationsDTOs) {
                return this.setTicketReservations(ticketReservationsDTOs);
        }
}
