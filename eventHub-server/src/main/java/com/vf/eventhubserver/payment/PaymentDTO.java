package com.vf.eventhubserver.payment;

import com.vf.eventhubserver.order.BookingDTO;
import java.time.LocalDateTime;

public record PaymentDTO(
    Long id, LocalDateTime paymentDateTime, BookingDTO booking, PaymentStatusDTO paymentStatus) {}
