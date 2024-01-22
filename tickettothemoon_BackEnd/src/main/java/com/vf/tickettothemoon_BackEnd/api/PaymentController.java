package com.vf.tickettothemoon_BackEnd.api;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.vf.tickettothemoon_BackEnd.domain.dto.PaymentDTO;
import com.vf.tickettothemoon_BackEnd.domain.service.PaymentService;
import com.vf.tickettothemoon_BackEnd.exception.FinderException;



@CrossOrigin
@RestController
@RequestMapping("/api")
@Validated
public class PaymentController {

    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @GetMapping("/payments")
    public ResponseEntity<List<PaymentDTO>> getAllPayments() throws FinderException {
        return ResponseEntity.ok(paymentService.findAll());
    }

    @GetMapping("/payments/{id}")
    public ResponseEntity<PaymentDTO> getPaymentById(@PathVariable Long id) throws FinderException {
        return ResponseEntity.ok(paymentService.findById(id));
    }


    // TODO : add a comment to explain URL
    // relation with booking : create payment for booking
    @PostMapping("/bookings/{booking_id}/payments")
    public ResponseEntity<PaymentDTO> createPayment(@PathVariable Long booking_id,
            @RequestBody PaymentDTO paymentDTO) throws FinderException {
        return ResponseEntity.ok(paymentService.createPayment(booking_id, paymentDTO));
    }


}
