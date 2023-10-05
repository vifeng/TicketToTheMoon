package com.vf.tickettothemoon_BackEnd.domain.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class PaymentStatus_category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String paymentStatus;


    public PaymentStatus_category() {}

    public PaymentStatus_category(Long id, String paymentStatus) {
        setId(id);
        setPaymentStatus(paymentStatus);
    }

    public PaymentStatus_category(String paymentStatus) {
        setPaymentStatus(paymentStatus);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    @Override
    public String toString() {
        return "PaymentStatus_category [id=" + id + ", paymentStatus=" + paymentStatus + "]";
    }


}
