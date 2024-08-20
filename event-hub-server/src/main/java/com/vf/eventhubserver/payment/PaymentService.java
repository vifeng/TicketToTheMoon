package com.vf.eventhubserver.payment;

import com.vf.eventhubserver.exception.CreateException;
import com.vf.eventhubserver.exception.FinderException;
import com.vf.eventhubserver.exception.NullException;
import com.vf.eventhubserver.order.Booking;
import com.vf.eventhubserver.order.BookingMapper;
import com.vf.eventhubserver.order.BookingRepository;
import com.vf.eventhubserver.order.BookingService;
import com.vf.eventhubserver.order.TicketReservation;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class PaymentService {

  PaymentRepository paymentRepository;
  PaymentMapper paymentMapper;
  BookingRepository bookingRepository;
  BookingService bookingService;
  BookingMapper bookingMapper;

  /**
   * @Description: The time in minutes that a booking is valid for before it expires
   */
  static final int BOOKING_EXPIRYDATETIME = 30;

  PaymentStatusRepository paymentStatusRepository;

  public PaymentService(
      PaymentRepository paymentRepository,
      PaymentMapper paymentMapper,
      BookingMapper bookingMapper,
      BookingRepository bookingRepository,
      BookingService bookingService,
      PaymentStatusRepository paymentStatusRepository) {
    this.paymentRepository = paymentRepository;
    this.paymentMapper = paymentMapper;
    this.bookingMapper = bookingMapper;
    this.bookingRepository = bookingRepository;
    this.bookingService = bookingService;
    this.paymentStatusRepository = paymentStatusRepository;
  }

  public List<PaymentDTO> findAll() throws FinderException {
    Iterable<Payment> payments = paymentRepository.findAll();
    int size = ((Collection<Payment>) payments).size();
    if (size == 0) {
      throw new FinderException("No Payments in the database");
    }
    return paymentMapper.toDTOs(payments);
  }

  public PaymentDTO findById(Long id) throws FinderException {
    Payment payment =
        paymentRepository
            .findById(id)
            .orElseThrow(() -> new FinderException("Payment with id {" + id + "} not found"));
    if (payment == null) {
      throw new FinderException("Payment with id {" + id + "} not found");
    }
    return paymentMapper.toDTO(payment);
  }

  public Long createPayment(Long bookingId) throws FinderException, CreateException, NullException {
    Optional<Booking> optionalBooking = bookingRepository.findById(bookingId);
    if (optionalBooking.isEmpty()) {
      throw new FinderException(
          "Booking with id {" + bookingId + "} not found, cannot create payment");
    }
    Booking booking = optionalBooking.get();
    Timestamp bookingCreationTimestamp = booking.getBookingCreationTimestamp();
    Set<TicketReservation> reservations = booking.getReservations();
    if (bookingCreationTimestamp == null || reservations.isEmpty()) {
      throw new NullException(
          "Booking creation timestamp or reservation is null, cannot create payment");
    }
    Boolean timeExpired = checkIfReservationTimeIsExpired(bookingCreationTimestamp);
    Boolean sessionExpired =
        bookingService.checkIfReservationIsSessionExpired(bookingCreationTimestamp, reservations);
    if (timeExpired && sessionExpired) {
      // booking has expired
      bookingService.deleteById(bookingId);
      throw new CreateException(
          "Payment has not been made, booking with id {"
              + bookingId
              + "} has expired. Booking and its reservations has been deleted. Please try again. timeExpired: "
              + timeExpired
              + " sessionExpired: "
              + sessionExpired);
    } else {
      // booking is still valid
      Optional<PaymentStatus> paymentStatus =
          paymentStatusRepository.findByPaymentStatusName("paid");
      if (paymentStatus.isEmpty()) {
        throw new FinderException("Payment status paid doesn't exist");
      }
      PaymentStatus paid = paymentStatus.get();
      Payment payment = new Payment(LocalDateTime.now(), booking, paid);
      bookingService.updateSeatAvailability(reservations, "sold");
      Payment savedPayment = paymentRepository.save(payment);
      return savedPayment.getId();
    }
  }

  public void deleteById(Long id) {
    paymentRepository.deleteById(id);
  }

  /**
   * Check if the reservation time is expired based on the booking creation time and the
   * BOOKING_EXPIRYDATETIME
   *
   * @param bookingCreationTimestamp
   * @return true if the reservation time is expired, false if the reservation time is still valid
   */
  public Boolean checkIfReservationTimeIsExpired(Timestamp bookingCreationTimestamp) {
    LocalDateTime bookingCreationLocalDateTime = bookingCreationTimestamp.toLocalDateTime();
    LocalDateTime expiryTimeForBooking =
        bookingCreationLocalDateTime.plusMinutes(BOOKING_EXPIRYDATETIME);
    return LocalDateTime.now().isAfter(expiryTimeForBooking);
  }
}
