package com.vf.eventhubserver.payment;

import com.vf.eventhubserver.exception.FinderException;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
@RequestMapping("/api/paymentStatus")
@Validated
public class PaymentStatusController {

  private final PaymentStatusService paymentStatusService;

  public PaymentStatusController(PaymentStatusService paymentStatusService) {
    this.paymentStatusService = paymentStatusService;
  }

  @GetMapping
  public ResponseEntity<List<PaymentStatusDTO>> getAllPaymentStatus() throws FinderException {
    return ResponseEntity.ok(paymentStatusService.findAll());
  }

  @GetMapping("/{id}")
  public ResponseEntity<PaymentStatusDTO> getPaymentStatusById(@PathVariable Long id)
      throws FinderException {
    return ResponseEntity.ok(paymentStatusService.findById(id));
  }

  @GetMapping("/status/{status}")
  public ResponseEntity<PaymentStatusDTO> getPaymentStatusByName(@PathVariable String status)
      throws FinderException {
    return ResponseEntity.ok(paymentStatusService.findByPaymentStatusName(status));
  }
}
