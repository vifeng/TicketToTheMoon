package com.vf.tickettothemoon_BackEnd.domain.dto;

import java.util.Date;
import java.util.Set;
import org.springframework.data.annotation.Id;
import com.vf.tickettothemoon_BackEnd.myUtil.Day;

public record EventDTO(@Id Long id, String name, Date dateStart, Date dateEnd, Set<Day> closedDay,
        ConfigurationHallDTO configurationHall) {

}
