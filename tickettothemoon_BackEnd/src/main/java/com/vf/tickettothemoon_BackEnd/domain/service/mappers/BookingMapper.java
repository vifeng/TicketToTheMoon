package com.vf.tickettothemoon_BackEnd.domain.service.mappers;

import java.util.Set;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import com.vf.tickettothemoon_BackEnd.domain.dto.BookingDTO;
import com.vf.tickettothemoon_BackEnd.domain.model.Booking;

@Mapper(componentModel = "spring")
public interface BookingMapper {


    BookingDTO toBookingDTO(Booking booking);

    Booking toBooking(BookingDTO bookingDTO);

    @IterableMapping(elementTargetType = BookingDTO.class)
    Set<BookingDTO> toBookingDTOs(Iterable<Booking> bookings);

    @IterableMapping(elementTargetType = Booking.class)
    Set<Booking> toBookings(Iterable<BookingDTO> bookingDTOs);

}
