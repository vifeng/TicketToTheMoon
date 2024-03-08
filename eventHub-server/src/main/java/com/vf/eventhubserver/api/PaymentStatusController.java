package com.vf.eventhubserver.api;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.vf.eventhubserver.domain.dto.PaymentStatusDTO;
import com.vf.eventhubserver.domain.service.PaymentStatusService;
import com.vf.eventhubserver.exception.FinderException;



@CrossOrigin
@RestController
@RequestMapping("/api/paymentStatus_category")
@Validated
public class PaymentStatusController {

    private final PaymentStatusService paymentStatus_categoryService;

    public PaymentStatusController(PaymentStatusService paymentStatus_categoryService) {
        this.paymentStatus_categoryService = paymentStatus_categoryService;
    }

    @GetMapping
    public ResponseEntity<List<PaymentStatusDTO>> getAllPaymentStatus_categorys()
            throws FinderException {
        return ResponseEntity.ok(paymentStatus_categoryService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PaymentStatusDTO> getPaymentStatus_categoryById(@PathVariable Long id)
            throws FinderException {
        return ResponseEntity.ok(paymentStatus_categoryService.findById(id));
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<PaymentStatusDTO> getPaymentStatus_categoryByName(
            @PathVariable String status) throws FinderException {
        return ResponseEntity.ok(paymentStatus_categoryService.findByPaymentStatusName(status));
    }


}
