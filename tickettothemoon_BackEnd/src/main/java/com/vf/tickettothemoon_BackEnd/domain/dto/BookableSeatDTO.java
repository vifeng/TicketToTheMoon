package com.vf.tickettothemoon_BackEnd.domain.dto;

public record BookableSeatDTO(Long id, double price, String name, double taxes,
                SessionEventDTO sessionEvent, SeatDTO seat, EventOrderDTO eventOrder) {


}
