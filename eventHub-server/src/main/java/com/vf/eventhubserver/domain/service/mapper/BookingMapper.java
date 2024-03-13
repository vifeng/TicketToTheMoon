package com.vf.eventhubserver.domain.service.mapper;

import java.util.Set;

import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;

import com.vf.eventhubserver.domain.dto.BookingDTO;
import com.vf.eventhubserver.domain.model.Booking;

@Mapper(componentModel = "spring")
public interface BookingMapper {

    BookingDTO toDTO(Booking booking);

    Booking toEntity(BookingDTO bookingDTO);

    @IterableMapping(elementTargetType = BookingDTO.class)
    Set<BookingDTO> toDTOs(Iterable<Booking> bookings);

    @IterableMapping(elementTargetType = Booking.class)
    Set<Booking> toEntities(Iterable<BookingDTO> bookingDTOs);

}
