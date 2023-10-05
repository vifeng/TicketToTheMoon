package com.vf.tickettothemoon_BackEnd.domain.model;

import java.time.LocalDateTime;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime paymentDateTime;

    @ManyToOne
    @JoinColumn(name = "Ticket_Reservation_FK")
    private Ticket_Reservation ticket_Reservation;

    @ManyToOne
    @JoinColumn(name = "PaymentStatus_category_FK")
    private PaymentStatus_category paymentStatus_category;


    public Payment() {}

    public Payment(Long id, LocalDateTime paymentDateTime, Ticket_Reservation ticket_Reservation,
            PaymentStatus_category paymentStatus_category) {
        setId(id);
        setPaymentDateTime(paymentDateTime);
        setTicket_Reservation(ticket_Reservation);
        setPaymentStatus_category(paymentStatus_category);
    }

    public Payment(LocalDateTime paymentDateTime, Ticket_Reservation ticket_Reservation,
            PaymentStatus_category paymentStatus_category) {
        setPaymentDateTime(paymentDateTime);
        setTicket_Reservation(ticket_Reservation);
        setPaymentStatus_category(paymentStatus_category);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getPaymentDateTime() {
        return paymentDateTime;
    }

    public void setPaymentDateTime(LocalDateTime paymentDateTime) {
        this.paymentDateTime = paymentDateTime;
    }

    public Ticket_Reservation getTicket_Reservation() {
        return ticket_Reservation;
    }

    public void setTicket_Reservation(Ticket_Reservation ticket_Reservation) {
        this.ticket_Reservation = ticket_Reservation;
    }

    public PaymentStatus_category getPaymentStatus_category() {
        return paymentStatus_category;
    }

    public void setPaymentStatus_category(PaymentStatus_category paymentStatus_category) {
        this.paymentStatus_category = paymentStatus_category;
    }

    @Override
    public String toString() {
        return "Payment [id=" + id + ", paymentDateTime=" + paymentDateTime
                + ", ticket_Reservation=" + ticket_Reservation + ", paymentStatus_category="
                + paymentStatus_category + "]";
    }



}
