package com.vf.tickettothemoon_BackEnd.api;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.vf.tickettothemoon_BackEnd.domain.dto.PaymentStatus_categoryDTO;
import com.vf.tickettothemoon_BackEnd.domain.service.PaymentStatus_categoryService;
import com.vf.tickettothemoon_BackEnd.exception.FinderException;



@CrossOrigin
@RestController
@RequestMapping("/api/paymentStatus_category")
@Validated
public class PaymentStatus_categoryController {

    private final PaymentStatus_categoryService paymentStatus_categoryService;

    public PaymentStatus_categoryController(
            PaymentStatus_categoryService paymentStatus_categoryService) {
        this.paymentStatus_categoryService = paymentStatus_categoryService;
    }

    @GetMapping
    public ResponseEntity<List<PaymentStatus_categoryDTO>> getAllPaymentStatus_categorys()
            throws FinderException {
        return ResponseEntity.ok(paymentStatus_categoryService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PaymentStatus_categoryDTO> getPaymentStatus_categoryById(
            @PathVariable Long id) throws FinderException {
        return ResponseEntity.ok(paymentStatus_categoryService.findById(id));
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<PaymentStatus_categoryDTO> getPaymentStatus_categoryByName(
            @PathVariable String status) throws FinderException {
        return ResponseEntity.ok(paymentStatus_categoryService.findByPaymentStatusName(status));
    }


}
