package com.vf.eventhubserver.payment;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class PaymentStatus {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String paymentStatusName;

    public PaymentStatus() {}

    public PaymentStatus(Long id, String paymentStatusName) {
        setId(id);
        setPaymentStatusName(paymentStatusName);
    }

    public PaymentStatus(String paymentStatusName) {
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
        return "PaymentStatus [id=" + id + ", paymentStatus=" + paymentStatusName + "]";
    }

}
