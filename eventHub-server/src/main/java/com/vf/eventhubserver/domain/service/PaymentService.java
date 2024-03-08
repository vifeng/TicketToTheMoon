package com.vf.eventhubserver.domain.service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.springframework.stereotype.Service;
import com.vf.eventhubserver.domain.dao.BookingRepository;
import com.vf.eventhubserver.domain.dao.PaymentRepository;
import com.vf.eventhubserver.domain.dao.PaymentStatus_categoryRepository;
import com.vf.eventhubserver.domain.dto.PaymentDTO;
import com.vf.eventhubserver.domain.model.Booking;
import com.vf.eventhubserver.domain.model.Payment;
import com.vf.eventhubserver.domain.model.PaymentStatus_category;
import com.vf.eventhubserver.domain.model.Ticket_Reservation;
import com.vf.eventhubserver.domain.service.mapper.BookingMapper;
import com.vf.eventhubserver.domain.service.mapper.PaymentMapper;
import com.vf.eventhubserver.exception.CreateException;
import com.vf.eventhubserver.exception.FinderException;
import com.vf.eventhubserver.exception.NullException;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class PaymentService {

    PaymentRepository paymentRepository;
    PaymentMapper paymentMapper;
    BookingRepository bookingRepository;
    BookingService bookingService;
    BookingMapper bookingMapper;
    final int BOOKING_EXPIRYDATETIME = 30;
    PaymentStatus_categoryRepository payment_StatusRepository;

    public PaymentService(PaymentRepository paymentRepository, PaymentMapper paymentMapper,
            BookingMapper bookingMapper, BookingRepository bookingRepository,
            BookingService bookingService,
            PaymentStatus_categoryRepository Payment_StatusRepository) {
        this.paymentRepository = paymentRepository;
        this.paymentMapper = paymentMapper;
        this.bookingMapper = bookingMapper;
        this.bookingRepository = bookingRepository;
        this.bookingService = bookingService;
        this.payment_StatusRepository = Payment_StatusRepository;
    }

    public List<PaymentDTO> findAll() throws FinderException {
        Iterable<Payment> payments = paymentRepository.findAll();
        int size = ((Collection<Payment>) payments).size();
        if (size == 0) {
            throw new FinderException("No Payments in the database");
        }
        List<PaymentDTO> paymentDTOs = paymentMapper.toDTOs(payments);
        return paymentDTOs;
    }

    public PaymentDTO findById(Long id) throws FinderException {
        if (id == null) {
            throw new NullException("Id cannot be null");
        }
        Payment payment = paymentRepository.findById(id)
                .orElseThrow(() -> new FinderException("Payment with id {" + id + "} not found"));
        return paymentMapper.toDTO(payment);
    }


    public PaymentDTO createPayment(Long bookingId)
            throws FinderException, CreateException, NullException {
        if (bookingId == null) {
            throw new NullException("BookingId cannot be null");
        }
        Optional<Booking> optionalBooking = bookingRepository.findById(bookingId);
        if (optionalBooking.isEmpty()) {
            throw new FinderException(
                    "Booking with id {" + bookingId + "} not found, cannot create payment");
        }
        Booking booking = optionalBooking.get();
        Boolean timeExpired = checkIfReservationTimeIsExpired(
                booking.getBooking_creationTimestamp(), booking.getReservations());

        Boolean sessionExpired = bookingService.checkIfReservationIsSessionExpired(
                booking.getBooking_creationTimestamp(), booking.getReservations());

        if (timeExpired && sessionExpired) {
            // booking is still valid
            Optional<PaymentStatus_category> paymentStatus =
                    payment_StatusRepository.findByPaymentStatusName("paid");
            Payment payment = new Payment(LocalDateTime.now(), booking, paymentStatus.get());
            // change seat availability to sold
            bookingService.updateSeatAvailability(booking.getReservations(), "sold");
            Payment savedPayment = paymentRepository.save(payment);
            return paymentMapper.toDTO(savedPayment);
        } else {
            // booking has expired
            bookingService.deleteById(bookingId);
            throw new CreateException("Payment has not been made, booking with id {" + bookingId
                    + "} has expired. Booking and its reservations has been deleted. Please try again.");
        }
    }

    public void deleteById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("Id cannot be null");
        }
        paymentRepository.deleteById(id);
    }

    /**
     * Check if the reservation time is expired based on the booking creation time and the
     * BOOKING_EXPIRYDATETIME
     * 
     * @param booking_creationTimestamp
     * @param reservations
     * @return true if the reservation time is expired, false if the reservation time is still valid
     */
    public Boolean checkIfReservationTimeIsExpired(Timestamp booking_creationTimestamp,
            Set<Ticket_Reservation> reservations) {
        LocalDateTime booking_creationLocalDateTime = booking_creationTimestamp.toLocalDateTime();
        LocalDateTime expiryTimeForBooking =
                booking_creationLocalDateTime.plusMinutes(BOOKING_EXPIRYDATETIME);
        Boolean res;
        if (expiryTimeForBooking.isAfter(LocalDateTime.now())) {
            // booking is still valid
            return false;
        }
        return true;
    }

}
