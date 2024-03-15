package com.vf.eventhubserver.order;

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
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import com.vf.eventhubserver.exception.FinderException;
import com.vf.eventhubserver.exception.NullException;

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

    /**
     * Creates a booking for a customer
     * 
     * @param customerId
     * @param reservationKeyDTO
     * @return
     * @throws FinderException
     * @throws IllegalArgumentException
     * @throws NullException
     */
    @PostMapping("/customer/{customerId}/reservationKey")
    public ResponseEntity<BookingDTO> createBookingForCustomerId(@PathVariable Long customerId,
            @RequestBody TicketReservationKeyDTO reservationKeyDTO)
            throws FinderException, IllegalArgumentException, NullException {
        BookingDTO bookingDTO = bookingService.createBooking(customerId, reservationKeyDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(bookingDTO);
    }

    /**
     * Adds a Ticket_Reservation to a booking
     * 
     * @param bookingId
     * @param reservationKeyDTO
     * @return
     * @throws FinderException
     */
    @PostMapping("/{bookingId}/reservationKey")
    public ResponseEntity<BookingDTO> addReservationToBooking(@PathVariable Long bookingId,
            @RequestBody TicketReservationKeyDTO reservationKeyDTO) throws FinderException {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(bookingService.addReservation(bookingId, reservationKeyDTO));

    }

    /**
     * Deletes a Ticket_Reservation from a booking. the last reservation deletes the booking.
     * 
     * @param bookingId
     * @param reservationKeyDTO
     * @return a BookingDTO object with status code 200 if other reservations are found, or a 204 No
     *         Content response if no reservations exist after the delete.
     * @throws FinderException
     */
    @DeleteMapping("/{bookingId}/reservationKey")
    public ResponseEntity<BookingDTO> deleteReservationFromBooking(@PathVariable Long bookingId,
            @RequestBody TicketReservationKeyDTO reservationKeyDTO) throws FinderException {
        BookingDTO bookingDTO = bookingService.deleteReservation(bookingId, reservationKeyDTO);
        return ResponseEntity.ok(bookingDTO);
    }

    /**
     * Deletes a booking by id
     * 
     * @param id
     * @return a 204 No Content response
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> deleteBookingById(@PathVariable Long id) {
        bookingService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}
