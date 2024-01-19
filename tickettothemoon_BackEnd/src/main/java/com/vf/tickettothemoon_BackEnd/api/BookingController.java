package com.vf.tickettothemoon_BackEnd.api;

import java.util.Set;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.vf.tickettothemoon_BackEnd.domain.dto.BookingDTO;
import com.vf.tickettothemoon_BackEnd.domain.dto.Ticket_ReservationKeyDTO;
import com.vf.tickettothemoon_BackEnd.domain.service.BookingService;
import com.vf.tickettothemoon_BackEnd.exception.FinderException;
import com.vf.tickettothemoon_BackEnd.exception.NullException;



@CrossOrigin
@RestController
@RequestMapping("/api/bookings")
@Validated
public class BookingController {

    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @GetMapping
    public ResponseEntity<Set<BookingDTO>> getAllBookings() throws FinderException {
        return ResponseEntity.ok(bookingService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookingDTO> getBookingById(@PathVariable Long id) throws FinderException {
        return ResponseEntity.ok(bookingService.findById(id));
    }

    @PostMapping("/customer/{customer_id}/reservationKey")
    public ResponseEntity<BookingDTO> createBookingForCustomerId(@PathVariable Long customer_id,
            @RequestBody Ticket_ReservationKeyDTO reservationKeyDTO)
            throws FinderException, IllegalArgumentException, NullException {
        BookingDTO bookingDTO = bookingService.createBooking(customer_id, reservationKeyDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(bookingDTO);
    }

    @PostMapping("/{booking_id}/reservationKey")
    public ResponseEntity<BookingDTO> addReservationToBooking(@PathVariable Long booking_id,
            @RequestBody Ticket_ReservationKeyDTO reservationKeyDTO) throws FinderException {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(bookingService.addReservation(booking_id, reservationKeyDTO));

    }

    @DeleteMapping("/{booking_id}/reservationKey")
    public ResponseEntity<BookingDTO> deleteReservationFromBooking(@PathVariable Long booking_id,
            @RequestBody Ticket_ReservationKeyDTO reservationKeyDTO) throws FinderException {
        BookingDTO bookingDTO = bookingService.deleteReservation(booking_id, reservationKeyDTO);
        if (bookingDTO == null) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(bookingDTO);
    }

}
