package com.vf.tickettothemoon_BackEnd.domain.dto;

import java.util.List;
import com.vf.tickettothemoon_BackEnd.domain.model.CategorySpatial;
import com.vf.tickettothemoon_BackEnd.domain.model.CategoryTariff;
import com.vf.tickettothemoon_BackEnd.domain.model.ConfigurationHall;
import com.vf.tickettothemoon_BackEnd.domain.model.Ticket_Reservation;

public record SeatDTO(Long id, boolean isSeated, int seatNo, char rowNo,
                CategorySpatial categorySpatial, CategoryTariff categoryTariff, boolean isBooked,
                ConfigurationHall configurationHall, List<Ticket_Reservation> ticket_Reservations) {
}
