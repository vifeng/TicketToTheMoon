package com.vf.tickettothemoon_BackEnd.domain.dto;

import java.time.LocalDateTime;
import org.springframework.data.annotation.Id;

public record SessionEventDTO(@Id Long id, LocalDateTime date, int eventHour, int duration,
                EventDTO event) {

}
