package com.vf.eventhubserver.domain.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class PaymentStatus_category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String paymentStatusName;


    public PaymentStatus_category() {}

    public PaymentStatus_category(Long id, String paymentStatusName) {
        setId(id);
        setPaymentStatusName(paymentStatusName);
    }

    public PaymentStatus_category(String paymentStatusName) {
        setPaymentStatusName(paymentStatusName);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPaymentStatusName() {
        return paymentStatusName;
    }

    public void setPaymentStatusName(String paymentStatusName) {
        this.paymentStatusName = paymentStatusName;
    }

    @Override
    public String toString() {
        return "PaymentStatus_category [id=" + id + ", paymentStatus=" + paymentStatusName + "]";
    }


}
