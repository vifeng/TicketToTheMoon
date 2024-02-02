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
import com.vf.tickettothemoon_BackEnd.exception.FinderException;
import com.vf.tickettothemoon_BackEnd.exception.NullException;
import com.vf.tickettothemoon_BackEnd.exception.RemoveException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

// REFACTOR : all services should depend on an abstract service that has the common methods.
@Service
@Transactional
public class BookingService {

    BookingRepository bookingRepository;
    BookingMapper bookingMapper;
    // TONOTE: this line actually throw a circular dependency error
    // so I should not use services in other services
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
            throws FinderException, IllegalArgumentException, NullException {
        if (customer_id == null || customer_id == 0) {
            throw new NullException("No customer id found");
        }
        if (reservationKeyDTO == null) {
            throw new NullException("No reservations found");
        }
        Customer customer = customerRepository.findById(customer_id).orElseThrow(
                () -> new FinderException("Customer with id {" + customer_id + "} not found"));
        Ticket_ReservationKey reservationKey =
                ticket_ReservationKeyMapper.toEntity(reservationKeyDTO);
        Ticket_Reservation ticketReservation = ticket_ReservationRepository.findById(reservationKey)
                .orElseThrow(() -> new FinderException(
                        "Ticket_Reservation with id {" + reservationKeyDTO + "} not found"));
        Timestamp booking_creationTimestamp = new Timestamp(System.currentTimeMillis());
        LocalDateTime sessionEventDate =
                reservationKey.getSessionEventId().getDateAndTimeStartSessionEvent();
        Timestamp sessionEventTimestamp = Timestamp.valueOf(sessionEventDate);
        if (booking_creationTimestamp.after(sessionEventTimestamp)) {
            throw new IllegalArgumentException("Booking Failed : SessionEvent with id "
                    + reservationKey.getSessionEventId() + " is finished");
        }
        // FIXME : comparator is not implemented in the mapper
        Set<Ticket_Reservation> reservationsByAvaibility =
                new TreeSet<Ticket_Reservation>(new TR_isBookedComparator());
        reservationsByAvaibility.add(ticketReservation);
        Booking booking =
                new Booking(booking_creationTimestamp, customer, reservationsByAvaibility);
        Booking savedBooking = bookingRepository.save(booking);
        updateSeatAvailability(booking.getId(), "booked");
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
        booking.addReservation(ticket_Reservation);
        Booking savedBooking = bookingRepository.save(booking);
        updateSeatAvailability(booking_id, "booked");
        return bookingMapper.toDTO(savedBooking);
    }

    public BookingDTO deleteReservation(Long booking_id, Ticket_ReservationKeyDTO reservationKeyDTO)
            throws FinderException, IllegalArgumentException {
        // FIXME: it is not deleting the TR when deleting one Reservation of 2. it writes null in
        // the foreign key
        if (booking_id == null) {
            throw new IllegalArgumentException("No booking id is null");
        }
        if (reservationKeyDTO == null) {
            throw new IllegalArgumentException("Ticket_ReservationKeyDTO is null");
        }
        Booking booking = bookingRepository.findById(booking_id).orElseThrow(
                () -> new FinderException("Booking with id {" + booking_id + "} not found"));
        if (booking.getReservations().size() < 0) {
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
        // FIXME : should only make available the seats that are deleted. right now it's all seats
        // of the booking
        updateSeatAvailability(booking_id, "available");
        booking.removeReservation(ticket_Reservation);
        if (booking.getReservations().isEmpty()) {
            deleteById(booking_id);
            return null;
        }
        Booking savedBooking = bookingRepository.save(booking);
        return bookingMapper.toDTO(savedBooking);
    }


    public void deleteById(Long booking_id) throws FinderException, IllegalArgumentException,
            EntityNotFoundException, RemoveException {
        if (booking_id == null) {
            throw new IllegalArgumentException("Booking id cannot be null");
        }
        Booking booking = bookingRepository.getReferenceById(booking_id);
        Optional<Payment> payment = paymentRepository.findByBookingId(booking_id);
        if (payment != null) {
            if (payment.get().getPaymentStatus_category().equals("paid")) {
                throw new RemoveException("Booking with id {" + booking_id
                        + "} cannot be deleted because it is paid");
            }
            // write other cases
            // if all other cases are not met but payment has been created, delete the payment
            paymentRepository.deleteById(payment.get().getId());
        } ;
        rollOverSeatsAvailability(booking_id);
        // bookingRepository.deleteById(booking_id);
    }

    /**
     * This method is used to roll over the seats availability to available when the booking is
     * expired or deleted. It also deletes the ticket_reservations that are related to the booking
     * and the booking itself.
     * 
     * @param booking_id
     * @throws FinderException
     * @throws IllegalArgumentException
     */
    public void rollOverSeatsAvailability(Long booking_id)
            throws FinderException, IllegalArgumentException {
        if (booking_id == null) {
            throw new NullException("Booking id cannot be null");
        }
        Booking booking = bookingRepository.findById(booking_id).orElseThrow(
                () -> new FinderException("Booking with id {" + booking_id + "} not found"));
        Set<Ticket_Reservation> reservations = booking.getReservations();
        for (Ticket_Reservation reservation : reservations) {
            ticket_ReservationService.changeSeatsStatus(reservation, "available");
            ticket_ReservationRepository.delete(reservation);
        }
        // FIXME: maybe should not be in this method
        bookingRepository.delete(booking);
    }

    public void updateSeatAvailability(Long booking_id, String status) {
        if (booking_id == null) {
            throw new NullException("Booking id cannot be null");
        }
        Booking booking = bookingRepository.findById(booking_id).orElseThrow(
                () -> new FinderException("Booking with id {" + booking_id + "} not found"));
        Set<Ticket_Reservation> reservations = booking.getReservations();
        for (Ticket_Reservation reservation : reservations) {
            ticket_ReservationService.changeSeatsStatus(reservation, status);
        }
    }



}
