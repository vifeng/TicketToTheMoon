package com.vf.tickettothemoon_BackEnd.domain.dto;

import java.util.Set;

public record SeatDTO(Long id, boolean isSeated, int seatNo, char rowNo,
                CategorySpatialDTO categorySpatial, CategoryTariffDTO categoryTariff,
                Seat_StatusDTO seat_Status, ConfigurationHallDTO configurationHall) {
        public Set<Ticket_ReservationDTO> getTicketReservations() {
                return this.getTicketReservations();
        }

        public Set<Ticket_ReservationDTO> setTicketReservations(
                        Set<Ticket_ReservationDTO> ticketReservationsDTOs) {
                return this.setTicketReservations(ticketReservationsDTOs);
        }
}
