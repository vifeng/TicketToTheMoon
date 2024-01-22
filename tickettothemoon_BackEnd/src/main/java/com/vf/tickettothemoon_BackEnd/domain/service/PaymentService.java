package com.vf.tickettothemoon_BackEnd.domain.service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import com.vf.tickettothemoon_BackEnd.domain.dao.BookingRepository;
import com.vf.tickettothemoon_BackEnd.domain.dao.PaymentRepository;
import com.vf.tickettothemoon_BackEnd.domain.dto.PaymentDTO;
import com.vf.tickettothemoon_BackEnd.domain.model.Booking;
import com.vf.tickettothemoon_BackEnd.domain.model.Payment;
import com.vf.tickettothemoon_BackEnd.domain.service.mappers.BookingMapper;
import com.vf.tickettothemoon_BackEnd.domain.service.mappers.PaymentMapper;
import com.vf.tickettothemoon_BackEnd.exception.CreateException;
import com.vf.tickettothemoon_BackEnd.exception.FinderException;
import com.vf.tickettothemoon_BackEnd.exception.NullException;
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

    public PaymentService(PaymentRepository paymentRepository, PaymentMapper paymentMapper,
            BookingMapper bookingMapper, BookingRepository bookingRepository,
            BookingService bookingService) {
        this.paymentRepository = paymentRepository;
        this.paymentMapper = paymentMapper;
        this.bookingMapper = bookingMapper;
        this.bookingRepository = bookingRepository;
        this.bookingService = bookingService;
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


    public PaymentDTO createPayment(Long bookingId, PaymentDTO paymentDTO)
            throws FinderException, CreateException, NullException {
        if (paymentDTO == null) {
            throw new NullException("PaymentDTO cannot be null");
        }
        if (bookingId == null) {
            throw new NullException("BookingId cannot be null");
        }
        if (paymentDTO.id() != null) {
            throw new IllegalArgumentException("PaymentDTO id must be null");
        }
        Optional<Booking> optionalBooking = bookingRepository.findById(bookingId);
        if (optionalBooking.isEmpty()) {
            throw new FinderException(
                    "Booking with id {" + bookingId + "} not found, cannot create payment");
        }
        Booking booking = optionalBooking.get();
        Timestamp booking_creationTimestamp = booking.getBooking_creationTimestamp();
        LocalDateTime booking_creationLocalDateTime = booking_creationTimestamp.toLocalDateTime();
        LocalDateTime expiryTime =
                booking_creationLocalDateTime.plusMinutes(BOOKING_EXPIRYDATETIME);

        if (expiryTime.isAfter(LocalDateTime.now())) {
            // booking is still valid
            Payment payment = paymentMapper.toEntity(paymentDTO);
            payment.setPaymentStatus_category(null);
            // change seat availability to sold
            bookingService.updateSeatAvailability(bookingId, "sold");
            Payment savedPayment = paymentRepository.save(payment);
            return paymentMapper.toDTO(savedPayment);
        } else {
            // booking has expired
            bookingService.rollOverSeatsAvailability(bookingId);
            bookingService.deleteBooking(bookingId);
            throw new CreateException("Payment has not been made, booking with id {" + bookingId
                    + "} has expired. Booking has been deleted. Please try again.");
        }
    }

}
