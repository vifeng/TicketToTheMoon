package com.vf.eventhubserver.payment;

import java.time.LocalDateTime;
import com.vf.eventhubserver.order.BookingDTO;

public record PaymentDTO(Long id, LocalDateTime paymentDateTime, BookingDTO booking,
        PaymentStatusDTO paymentStatus) {
}
