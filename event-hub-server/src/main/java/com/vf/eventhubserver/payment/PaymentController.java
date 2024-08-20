package com.vf.eventhubserver.payment;

import com.vf.eventhubserver.LocationResponseBuilder;
import com.vf.eventhubserver.exception.FinderException;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
@RequestMapping("/api/payments")
@Validated
public class PaymentController implements LocationResponseBuilder<Long> {

  private final PaymentService paymentService;

  public PaymentController(PaymentService paymentService) {
    this.paymentService = paymentService;
  }

  @GetMapping
  public ResponseEntity<List<PaymentDTO>> getAllPayments() throws FinderException {
    return ResponseEntity.ok(paymentService.findAll());
  }

  @GetMapping("/{id}")
  public ResponseEntity<PaymentDTO> getPaymentById(@PathVariable Long id) throws FinderException {
    return ResponseEntity.ok(paymentService.findById(id));
  }

  @PostMapping("/booking/{bookingId}")
  public ResponseEntity<Void> createPayment(@PathVariable Long bookingId) throws FinderException {
    Long id = paymentService.createPayment(bookingId);
    String resourcePath = "/api/payments/{id}";
    return entityWithCustomLocation(id, resourcePath);
  }
}
