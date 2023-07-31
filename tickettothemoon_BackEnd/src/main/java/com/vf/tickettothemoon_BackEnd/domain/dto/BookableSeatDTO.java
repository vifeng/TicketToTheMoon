package com.vf.tickettothemoon_BackEnd.domain.dto;

import org.springframework.data.annotation.Id;

public record BookableSeatDTO(@Id Long id, double price, String name, double taxes,
                SessionEventDTO sessionEvents, SeatDTO seat, EventOrderDTO eventOrder) {


}
