package com.vf.eventhubserver.order;

import com.vf.eventhubserver.exception.DuplicateKeyException;
import com.vf.eventhubserver.exception.FinderException;
import com.vf.eventhubserver.exception.NullException;
import com.vf.eventhubserver.exception.RemoveException;
import com.vf.eventhubserver.payment.Payment;
import com.vf.eventhubserver.payment.PaymentRepository;
import com.vf.eventhubserver.persona.Customer;
import com.vf.eventhubserver.persona.CustomerRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class BookingService {

  BookingRepository bookingRepository;
  BookingMapper bookingMapper;
  CustomerRepository customerRepository;
  TicketReservationMapper ticketReservationMapper;
  TicketReservationRepository ticketReservationRepository;
  TicketReservationService ticketReservationService;
  TicketReservationKeyMapper ticketReservationKeyMapper;
  PaymentRepository paymentRepository;

  static final String BKMSG = "Booking with id {";
  static final String TRMSG = "ticketReservation with id {";
  static final String NOTFOUNDMSG = "} not found";
  static final String TRNULLMSG = "ticketReservation is null";
  static final String BKNULLMSG = "Booking is null";

  public BookingService(
      BookingRepository bookingRepository,
      BookingMapper bookingMapper,
      CustomerRepository customerRepository,
      TicketReservationMapper ticketReservationMapper,
      TicketReservationRepository ticketReservationRepository,
      TicketReservationService ticketReservationService,
      TicketReservationKeyMapper ticketReservationKeyMapper,
      PaymentRepository paymentRepository) {
    this.bookingRepository = bookingRepository;
    this.bookingMapper = bookingMapper;
    this.customerRepository = customerRepository;
    this.ticketReservationMapper = ticketReservationMapper;
    this.ticketReservationRepository = ticketReservationRepository;
    this.ticketReservationService = ticketReservationService;
    this.ticketReservationKeyMapper = ticketReservationKeyMapper;
    this.paymentRepository = paymentRepository;
  }

  public Set<BookingDTO> findAll() throws FinderException {
    Iterable<Booking> bookings = bookingRepository.findAll();
    int size = ((Collection<Booking>) bookings).size();
    if (size == 0) {
      throw new FinderException("No Bookings in the database");
    }
    return bookingMapper.toDTOs(bookings);
  }

  public BookingDTO findById(Long id) throws FinderException {
    Booking booking =
        bookingRepository
            .findById(id)
            .orElseThrow(() -> new FinderException(BKMSG + id + NOTFOUNDMSG));
    if (booking == null) {
      throw new NullException(BKMSG + id + BKNULLMSG);
    }
    return bookingMapper.toDTO(booking);
  }

  public Long createBooking(Long customerId, TicketReservationKeyDTO reservationKeyDTO)
      throws FinderException, IllegalArgumentException {
    Customer customer =
        customerRepository
            .findById(customerId)
            .orElseThrow(
                () -> new FinderException("Customer with id {" + customerId + NOTFOUNDMSG));
    TicketReservationKey reservationKey = ticketReservationKeyMapper.toEntity(reservationKeyDTO);

    TicketReservation ticketReservation =
        ticketReservationRepository
            .findById(reservationKey)
            .orElseThrow(() -> new FinderException(TRMSG + reservationKeyDTO + NOTFOUNDMSG));
    if (ticketReservation == null) {
      throw new NullException(TRMSG + reservationKeyDTO + TRNULLMSG);
    }
    // checks if the reservation is expired or the seat is available
    Timestamp bookingCreationTimestamp = new Timestamp(System.currentTimeMillis());

    checkIfReservationIsSessionExpired(bookingCreationTimestamp, ticketReservation);
    checkIfSeatStatusIsAvailable(ticketReservation);

    Set<TicketReservation> reservationsByAvaibility = new TreeSet<>(new TRIsBookedComparator());
    reservationsByAvaibility.add(ticketReservation);
    Optional<Booking> existingBooking =
        bookingRepository.findByCustomerAndReservations(customer, ticketReservation);
    if (existingBooking.isPresent()) {
      throw new DuplicateKeyException(
          "Booking already exists for customer with ID "
              + customerId
              + " and reservation key "
              + reservationKeyDTO);
    }
    Booking booking = new Booking(bookingCreationTimestamp, customer, reservationsByAvaibility);
    Booking savedBooking = bookingRepository.save(booking);
    updateSeatAvailability(ticketReservation, "booked");
    return savedBooking.getId();
  }

  public Long addReservation(Long bookingId, TicketReservationKeyDTO reservationKeyDTO)
      throws FinderException, IllegalArgumentException {
    Booking booking =
        bookingRepository
            .findById(bookingId)
            .orElseThrow(() -> new FinderException(BKMSG + bookingId + NOTFOUNDMSG));
    if (booking == null) {
      throw new NullException(BKNULLMSG);
    }
    TicketReservationKey reservationKey = ticketReservationKeyMapper.toEntity(reservationKeyDTO);
    TicketReservation ticketReservation = null;
    if (reservationKey != null) {
      ticketReservation =
          ticketReservationRepository
              .findById(reservationKey)
              .orElseThrow(() -> new FinderException(TRMSG + reservationKeyDTO + NOTFOUNDMSG));
    }
    if (booking.getReservations().contains(ticketReservation)) {
      throw new DuplicateKeyException(
          "Reservation with id "
              + reservationKeyDTO
              + " already exists in booking with id "
              + bookingId);
    }
    // checks if the reservation is expired or the seat is available
    if (ticketReservation != null) {
      booking.addReservation(ticketReservation);
      updateSeatAvailability(ticketReservation, "booked");
    }
    Booking savedBooking = bookingRepository.save(booking);
    return savedBooking.getId();
  }

  /**
   * Returns null if the booking has no reservations after the deletion of the reservation.
   *
   * @param bookingId
   * @param reservationKeyDTO
   * @return
   * @throws FinderException
   * @throws IllegalArgumentException
   */
  @Nullable
  public BookingDTO deleteReservation(Long bookingId, TicketReservationKeyDTO reservationKeyDTO)
      throws FinderException, IllegalArgumentException {
    Booking booking =
        bookingRepository
            .findById(bookingId)
            .orElseThrow(() -> new FinderException(BKMSG + bookingId + NOTFOUNDMSG));
    if (Boolean.TRUE.equals(checkIfPaymentIsPaid(bookingId))) {
      throw new RemoveException(
          BKMSG + bookingId + "} cannot be deleted because it has a paid status");
    }
    if (booking.getReservations() == null || booking.getReservations().isEmpty()) {
      throw new IllegalArgumentException(BKMSG + bookingId + "} has no reservation");
    }
    TicketReservationKey reservationKey = ticketReservationKeyMapper.toEntity(reservationKeyDTO);
    TicketReservation ticketReservation =
        ticketReservationRepository
            .findById(reservationKey)
            .orElseThrow(() -> new FinderException(TRMSG + reservationKeyDTO + NOTFOUNDMSG));
    if (!booking.getReservations().contains(ticketReservation)) {
      throw new IllegalArgumentException(
          TRMSG + reservationKeyDTO + "} not found in booking with id {" + bookingId + "}");
    }
    if (ticketReservation == null) {
      throw new IllegalArgumentException(TRMSG + reservationKeyDTO + NOTFOUNDMSG);
    }
    booking.removeReservation(ticketReservation);
    updateSeatAvailability(ticketReservation, "available");
    deleteticketReservation(ticketReservation);
    if (booking.getReservations().isEmpty()) {
      deleteById(bookingId);
      return null;
    }
    Booking savedBooking = bookingRepository.save(booking);
    return bookingMapper.toDTO(savedBooking);
  }

  /**
   * Delete a booking and its reservations from the database, rollback the seat status to available.
   * You cannot delete a booking if the payment has been made.
   *
   * @param bookingId
   * @throws FinderException
   * @throws IllegalArgumentException
   * @throws EntityNotFoundException
   * @throws RemoveException
   */
  public void deleteById(Long bookingId)
      throws FinderException, IllegalArgumentException, EntityNotFoundException, RemoveException {
    if (Boolean.TRUE.equals(checkIfPaymentIsPaid(bookingId))) {
      throw new RemoveException(
          BKMSG + bookingId + "} cannot be deleted because it has a paid status");
    }
    Booking booking =
        bookingRepository
            .findById(bookingId)
            .orElseThrow(() -> new FinderException(BKMSG + bookingId + NOTFOUNDMSG));
    Set<TicketReservation> reservations = booking.getReservations();
    if (reservations.isEmpty()) {
      bookingRepository.delete(booking);
    } else {
      updateSeatAvailability(reservations, "available");
      deleteticketReservation(reservations);
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
  public void updateSeatAvailability(Set<TicketReservation> reservations, String status) {
    for (TicketReservation reservation : reservations) {
      if (reservation != null) updateSeatAvailability(reservation, status);
    }
  }

  /**
   * Update the seat status of a reservation. Example of status : "available", "booked", "sold",
   * "unavailable"
   *
   * @param reservation
   * @param status
   */
  public void updateSeatAvailability(TicketReservation reservation, String status) {
    ticketReservationService.changeSeatsStatus(reservation, status);
  }

  /**
   * Checks if the specified set of reservations are expired based on their associated session's
   * expiration time. A reservation is considered expired if its session has expired.
   *
   * @param bookingCreationTimestamp
   * @param reservations
   * @return false if all the reservations in the set are not expired
   * @throws IllegalArgumentException
   */
  public Boolean checkIfReservationIsSessionExpired(
      Timestamp bookingCreationTimestamp, Set<TicketReservation> reservations)
      throws IllegalArgumentException {
    for (TicketReservation reservation : reservations) {
      if (reservation != null)
        checkIfReservationIsSessionExpired(bookingCreationTimestamp, reservation);
    }
    return false;
  }

  /**
   * Checks if the specified reservation is expired based on its associated session's expiration
   * time. A reservation is considered expired if its session has expired (session expiration time
   * has passed).
   *
   * @param bookingCreationTimestamp
   * @param reservation
   * @return false if the reservation is not expired
   * @throws IllegalArgumentException
   */
  public Boolean checkIfReservationIsSessionExpired(
      Timestamp bookingCreationTimestamp, TicketReservation reservation)
      throws IllegalArgumentException {
    LocalDateTime sessionEventDate =
        reservation.getId().getSessionEventId().getDateAndTimeStartSessionEvent();
    Timestamp sessionEventTimestamp = Timestamp.valueOf(sessionEventDate);
    if (bookingCreationTimestamp.after(sessionEventTimestamp)) {
      throw new IllegalArgumentException(
          "Booking Failed : SessionEvent with id "
              + reservation.getId().getSessionEventId()
              + " is finished + ticketReservation "
              + reservation.getId()
              + " is expired");
    }
    return false;
  }

  /**
   * Check if the seat status of a reservation is of status "available".If not available, it throws
   * an exception
   *
   * @param reservation
   * @return true if the seat status is "available" or throw an IllegalArgumentException if the seat
   *     status is not "available"
   * @throws IllegalArgumentException
   */
  private Boolean checkIfSeatStatusIsAvailable(TicketReservation reservation)
      throws IllegalArgumentException {
    Boolean available = ticketReservationService.checkIfSeatStatusIsAvailable(reservation);
    if (Boolean.FALSE.equals(available)) {
      throw new IllegalArgumentException(
          "Booking Failed : Seat with id " + reservation.getId().getSeatId() + " is not available");
    }
    return true;
  }

  /**
   * Delete a ticketReservation from the database
   *
   * @param ticketReservation
   */
  private void deleteticketReservation(TicketReservation ticketReservation) {
    TicketReservationKey key = ticketReservation.getId();
    if (key == null) throw new NullException(TRNULLMSG + " or it's key");
    ticketReservationService.deleteById(key);
  }

  /**
   * Delete a set of ticketReservations from the database
   *
   * @param ticketReservations
   */
  private void deleteticketReservation(Set<TicketReservation> reservations) {
    if (reservations.isEmpty()) {
      throw new IllegalArgumentException("ticketReservation Set is empty");
    }
    for (TicketReservation reservation : reservations) {
      if (reservation != null) {
        TicketReservationKey key = reservation.getId();
        if (key != null) ticketReservationService.deleteById(key);
      }
    }
  }

  /**
   * @param bookingId
   * @return true if payment has been made, false if the payment is not made
   */
  public Boolean checkIfPaymentIsPaid(Long bookingId) {
    Optional<Payment> payment = paymentRepository.findByBookingId(bookingId);
    if (payment.isEmpty()) {
      return false;
    }
    String paymentStatus = payment.get().getPaymentStatus().getPaymentStatusName();
    return paymentStatus.equals("paid");
  }
}
