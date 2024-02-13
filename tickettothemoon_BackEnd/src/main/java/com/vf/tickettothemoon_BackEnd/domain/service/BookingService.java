package com.vf.tickettothemoon_BackEnd.domain.service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;
import org.springframework.stereotype.Service;
import com.vf.tickettothemoon_BackEnd.domain.dao.BookingRepository;
import com.vf.tickettothemoon_BackEnd.domain.dao.CustomerRepository;
import com.vf.tickettothemoon_BackEnd.domain.dao.PaymentRepository;
import com.vf.tickettothemoon_BackEnd.domain.dao.Ticket_ReservationRepository;
import com.vf.tickettothemoon_BackEnd.domain.dto.BookingDTO;
import com.vf.tickettothemoon_BackEnd.domain.dto.Ticket_ReservationKeyDTO;
import com.vf.tickettothemoon_BackEnd.domain.model.Booking;
import com.vf.tickettothemoon_BackEnd.domain.model.Customer;
import com.vf.tickettothemoon_BackEnd.domain.model.Payment;
import com.vf.tickettothemoon_BackEnd.domain.model.TR_isBookedComparator;
import com.vf.tickettothemoon_BackEnd.domain.model.Ticket_Reservation;
import com.vf.tickettothemoon_BackEnd.domain.model.Ticket_ReservationKey;
import com.vf.tickettothemoon_BackEnd.domain.service.mappers.BookingMapper;
import com.vf.tickettothemoon_BackEnd.domain.service.mappers.Ticket_ReservationKeyMapper;
import com.vf.tickettothemoon_BackEnd.domain.service.mappers.Ticket_ReservationMapper;
import com.vf.tickettothemoon_BackEnd.exception.DuplicateKeyException;
import com.vf.tickettothemoon_BackEnd.exception.FinderException;
import com.vf.tickettothemoon_BackEnd.exception.RemoveException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

// REFACTOR : all services should depend on an abstract service that has the common methods.
@Service
@Transactional
public class BookingService {

    BookingRepository bookingRepository;
    BookingMapper bookingMapper;
    // TODISCUSS: this line actually throw a circular dependency error
    // so I should not use services in other services maybe
    // PaymentService paymentService;
    CustomerRepository customerRepository;
    Ticket_ReservationMapper ticket_ReservationMapper;
    Ticket_ReservationRepository ticket_ReservationRepository;
    Ticket_ReservationService ticket_ReservationService;
    Ticket_ReservationKeyMapper ticket_ReservationKeyMapper;
    PaymentRepository paymentRepository;

    public BookingService(BookingRepository bookingRepository, BookingMapper bookingMapper,
            CustomerRepository customerRepository,
            Ticket_ReservationMapper ticket_ReservationMapper,
            Ticket_ReservationRepository ticket_ReservationRepository,
            Ticket_ReservationService ticket_ReservationService,
            Ticket_ReservationKeyMapper ticket_ReservationKeyMapper,
            PaymentRepository paymentRepository) {
        this.bookingRepository = bookingRepository;
        this.bookingMapper = bookingMapper;
        this.customerRepository = customerRepository;
        this.ticket_ReservationMapper = ticket_ReservationMapper;
        this.ticket_ReservationRepository = ticket_ReservationRepository;
        this.ticket_ReservationService = ticket_ReservationService;
        this.ticket_ReservationKeyMapper = ticket_ReservationKeyMapper;
        this.paymentRepository = paymentRepository;
    }

    public Set<BookingDTO> findAll() throws FinderException {
        Iterable<Booking> bookings = bookingRepository.findAll();
        int size = ((Collection<Booking>) bookings).size();
        if (size == 0) {
            throw new FinderException("No Bookings in the database");
        }
        Set<BookingDTO> bookingDTOs = bookingMapper.toDTOs(bookings);
        return bookingDTOs;
    }

    public BookingDTO findById(Long id) throws FinderException {
        if (id == null || id == 0) {
            throw new IllegalArgumentException("No Booking id found");
        }
        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new FinderException("Booking with id {" + id + "} not found"));
        return bookingMapper.toDTO(booking);
    }

    public BookingDTO createBooking(Long customer_id, Ticket_ReservationKeyDTO reservationKeyDTO)
            throws FinderException, IllegalArgumentException {
        // TODISCUSS : how much precise should I be in the error message, that can be a lot of if{}
        // statements
        if (customer_id == null || customer_id == 0) {
            throw new IllegalArgumentException("No customer id argument");
        }
        if (reservationKeyDTO == null) {
            throw new IllegalArgumentException("No reservations argument");
        }

        Customer customer = customerRepository.findById(customer_id).orElseThrow(
                () -> new FinderException("Customer with id {" + customer_id + "} not found"));
        Ticket_ReservationKey reservationKey =
                ticket_ReservationKeyMapper.toEntity(reservationKeyDTO);
        Ticket_Reservation ticketReservation = ticket_ReservationRepository.findById(reservationKey)
                .orElseThrow(() -> new FinderException(
                        "Ticket_Reservation with id {" + reservationKeyDTO + "} not found"));

        // checks if the reservation is expired or the seat is available
        Timestamp booking_creationTimestamp = new Timestamp(System.currentTimeMillis());
        checkIfReservationIsSessionExpired(booking_creationTimestamp, ticketReservation);
        checkIfSeatStatusIsAvailable(ticketReservation);

        // TODO_LOW : treeset & comparator is not implemented in the mapper, search how to do it and
        // implement
        Set<Ticket_Reservation> reservationsByAvaibility =
                new TreeSet<Ticket_Reservation>(new TR_isBookedComparator());
        reservationsByAvaibility.add(ticketReservation);
        Optional<Booking> existingBooking =
                bookingRepository.findByCustomerAndReservations(customer, ticketReservation);
        if (existingBooking.isPresent()) {
            throw new DuplicateKeyException("Booking already exists for customer with ID "
                    + customer_id + " and reservation key " + reservationKeyDTO);
        }
        Booking booking =
                new Booking(booking_creationTimestamp, customer, reservationsByAvaibility);
        Booking savedBooking = bookingRepository.save(booking);
        updateSeatAvailability(ticketReservation, "booked");
        BookingDTO bookingDTOSaved = bookingMapper.toDTO(savedBooking);
        return bookingDTOSaved;
    }



    public BookingDTO addReservation(Long booking_id, Ticket_ReservationKeyDTO reservationKeyDTO)
            throws FinderException, IllegalArgumentException {
        if (booking_id == null || booking_id == 0) {
            throw new IllegalArgumentException("No booking id found");
        }
        if (reservationKeyDTO == null) {
            throw new IllegalArgumentException("No reservations found");
        }

        Booking booking = bookingRepository.findById(booking_id).orElseThrow(
                () -> new FinderException("Booking with id {" + booking_id + "} not found"));
        Ticket_ReservationKey reservationKey =
                ticket_ReservationKeyMapper.toEntity(reservationKeyDTO);
        Ticket_Reservation ticket_Reservation = ticket_ReservationRepository
                .findById(reservationKey).orElseThrow(() -> new FinderException(
                        "Ticket_Reservation with id {" + reservationKeyDTO + "} not found"));
        // checks if the reservation is expired or the seat is available
        checkIfReservationIsSessionExpired(booking.getBooking_creationTimestamp(),
                ticket_Reservation);
        checkIfSeatStatusIsAvailable(ticket_Reservation);
        if (booking.getReservations().contains(ticket_Reservation)) {
            throw new IllegalArgumentException("Ticket_Reservation with id {" + reservationKeyDTO
                    + "} already exists in booking with id {" + booking_id + "}");
        }

        booking.addReservation(ticket_Reservation);
        Booking savedBooking = bookingRepository.save(booking);
        updateSeatAvailability(ticket_Reservation, "booked");
        return bookingMapper.toDTO(savedBooking);
    }

    public BookingDTO deleteReservation(Long booking_id, Ticket_ReservationKeyDTO reservationKeyDTO)
            throws FinderException, IllegalArgumentException {

        if (booking_id == null) {
            throw new IllegalArgumentException("No booking id is null");
        }
        if (reservationKeyDTO == null) {
            throw new IllegalArgumentException("Ticket_ReservationKeyDTO is null");
        }
        Booking booking = bookingRepository.findById(booking_id).orElseThrow(
                () -> new FinderException("Booking with id {" + booking_id + "} not found"));
        if (checkIfPaymentIsPaid(booking_id)) {
            throw new RemoveException("Booking with id {" + booking_id
                    + "} cannot be deleted because it has a paid status");
        }
        if (booking.getReservations() == null || booking.getReservations().size() == 0) {
            throw new IllegalArgumentException(
                    "Booking with id {" + booking_id + "} has no reservation");
        }
        Ticket_ReservationKey reservationKey =
                ticket_ReservationKeyMapper.toEntity(reservationKeyDTO);
        Ticket_Reservation ticket_Reservation = ticket_ReservationRepository
                .findById(reservationKey).orElseThrow(() -> new FinderException(
                        "Ticket_Reservation with id {" + reservationKeyDTO + "} not found"));
        if (!booking.getReservations().contains(ticket_Reservation)) {
            throw new IllegalArgumentException("Ticket_Reservation with id {" + reservationKeyDTO
                    + "} not found in booking with id {" + booking_id + "}");
        }
        booking.removeReservation(ticket_Reservation);
        updateSeatAvailability(ticket_Reservation, "available");
        deleteTicket_Reservation(ticket_Reservation);
        if (booking.getReservations().isEmpty()) {
            deleteById(booking_id);
            return null;
        }
        Booking savedBooking = bookingRepository.save(booking);
        return bookingMapper.toDTO(savedBooking);
    }


    /**
     * Delete a booking and its reservations from the database, rollback the seat status to
     * available. You cannot delete a booking if the payment has been made.
     * 
     * @param booking_id
     * @throws FinderException
     * @throws IllegalArgumentException
     * @throws EntityNotFoundException
     * @throws RemoveException
     */
    public void deleteById(Long booking_id) throws FinderException, IllegalArgumentException,
            EntityNotFoundException, RemoveException {
        if (booking_id == null) {
            throw new IllegalArgumentException("Booking id cannot be null");
        }
        if (checkIfPaymentIsPaid(booking_id)) {
            throw new RemoveException("Booking with id {" + booking_id
                    + "} cannot be deleted because it has a paid status");
        }
        Booking booking = bookingRepository.findById(booking_id).orElseThrow(
                () -> new FinderException("Booking with id {" + booking_id + "} not found"));
        if (booking.getReservations().isEmpty() || booking.getReservations() == null) {
            bookingRepository.delete(booking);
        } else {
            updateSeatAvailability(booking.getReservations(), "available");
            deleteTicket_Reservation(booking.getReservations());
            bookingRepository.delete(booking);
        }
    }


    // -----------------------------------------------------------------------------------------
    // UTILITIES METHODS
    // -----------------------------------------------------------------------------------------

    /**
     * Update the seat status of a set of reservations.
     * 
     * @param reservations
     * @param status
     */
    public void updateSeatAvailability(Set<Ticket_Reservation> reservations, String status) {
        if (reservations == null || reservations.isEmpty()) {
            throw new IllegalArgumentException("Ticket_Reservation Set is null or empty");
        }
        if (status == null || status.isEmpty()) {
            throw new IllegalArgumentException("Status is null or empty");
        }
        for (Ticket_Reservation reservation : reservations) {
            updateSeatAvailability(reservation, status);
        }
    }

    /**
     * Update the seat status of a reservation.
     * 
     * Example of status : "available", "booked", "sold", "unavailable"
     * 
     * @param reservation
     * @param status
     */
    public void updateSeatAvailability(Ticket_Reservation reservation, String status) {
        ticket_ReservationService.changeSeatsStatus(reservation, status);
    }

    /**
     * Checks if the specified set of reservations are expired based on their associated session's
     * expiration time. A reservation is considered expired if its session has expired.
     * 
     * @param booking_creationTimestamp
     * @param reservations
     * @return false if all the reservations in the set are not expired
     * @throws IllegalArgumentException
     */
    public Boolean checkIfReservationIsSessionExpired(Timestamp booking_creationTimestamp,
            Set<Ticket_Reservation> reservations) throws IllegalArgumentException {
        for (Ticket_Reservation reservation : reservations) {
            checkIfReservationIsSessionExpired(booking_creationTimestamp, reservation);
        }
        return false;
    }

    /**
     * Checks if the specified reservation is expired based on its associated session's expiration
     * time. A reservation is considered expired if its session has expired (session expiration time
     * has passed).
     * 
     * @param booking_creationTimestamp
     * @param reservation
     * @return false if the reservation is not expired
     * @throws IllegalArgumentException
     */
    public Boolean checkIfReservationIsSessionExpired(Timestamp booking_creationTimestamp,
            Ticket_Reservation reservation) throws IllegalArgumentException {
        if (reservation == null) {
            throw new IllegalArgumentException("Ticket_Reservation is null");
        }
        if (booking_creationTimestamp == null) {
            throw new IllegalArgumentException("Booking creation timestamp is null");
        }
        LocalDateTime sessionEventDate =
                reservation.getId().getSessionEventId().getDateAndTimeStartSessionEvent();
        Timestamp sessionEventTimestamp = Timestamp.valueOf(sessionEventDate);
        if (booking_creationTimestamp.after(sessionEventTimestamp)) {
            throw new IllegalArgumentException("Booking Failed : SessionEvent with id "
                    + reservation.getId().getSessionEventId() + " is finished + Ticket_Reservation "
                    + reservation.getId() + " is expired");
        }
        return false;
    }

    /**
     * Check if all seat status of the reservation set are of status "available". If one is not
     * available, it throws an exception
     * 
     * @param reservations
     * @return true if all seat status of the set are "available" or throw an
     *         IllegalArgumentException if the seat status is not "available"
     * @throws IllegalArgumentException
     */
    private Boolean checkIfSeatStatusIsAvailable(Set<Ticket_Reservation> reservations)
            throws IllegalArgumentException {
        if (reservations == null || reservations.isEmpty()) {
            throw new IllegalArgumentException("Ticket_Reservation set is null or empty");
        }
        for (Ticket_Reservation reservation : reservations) {
            checkIfSeatStatusIsAvailable(reservation);
        }
        return true;
    }

    /**
     * Check if the seat status of a reservation is of status "available".If not available, it
     * throws an exception
     * 
     * @param reservation
     * @return true if the seat status is "available" or throw an IllegalArgumentException if the
     *         seat status is not "available"
     * @throws IllegalArgumentException
     */
    private Boolean checkIfSeatStatusIsAvailable(Ticket_Reservation reservation)
            throws IllegalArgumentException {
        if (reservation == null) {
            throw new IllegalArgumentException("Ticket_Reservation is null");
        }
        Boolean available = ticket_ReservationService.checkIfSeatStatusIsAvailable(reservation);
        if (!available) {
            throw new IllegalArgumentException("Booking Failed : Seat with id "
                    + reservation.getId().getSeatId() + " is not available");
        }
        return true;
    }

    /**
     * Delete a ticket_reservation from the database
     * 
     * @param ticket_Reservation
     */
    private void deleteTicket_Reservation(Ticket_Reservation ticket_Reservation) {
        if (ticket_Reservation == null) {
            throw new IllegalArgumentException("Ticket_Reservation is null");
        }
        ticket_ReservationService.deleteById(ticket_Reservation.getId());
    }

    /**
     * Delete a set of ticket_reservations from the database
     * 
     * @param ticket_Reservations
     */
    private void deleteTicket_Reservation(Set<Ticket_Reservation> reservations) {
        if (reservations == null || reservations.isEmpty()) {
            throw new IllegalArgumentException("Ticket_Reservation Set is null or empty");
        }
        for (Ticket_Reservation reservation : reservations) {
            ticket_ReservationService.deleteById(reservation.getId());
        }
    }

    /**
     * 
     * @param booking_id
     * @return true if payment has been made, false if the payment is not made
     */
    public Boolean checkIfPaymentIsPaid(Long booking_id) {
        if (booking_id == null) {
            throw new IllegalArgumentException("Booking id is null");
        }
        Optional<Payment> payment = paymentRepository.findByBookingId(booking_id);
        if (payment.isEmpty()) {
            return false;
        }
        String paymentStatus = payment.get().getPaymentStatus_category().getPaymentStatusName();
        if (payment.isPresent() && paymentStatus.equals("paid")) {
            return true;
        }
        return false;
    }

}
