package com.vf.eventhubserver.payment;

import com.vf.eventhubserver.order.Booking;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.time.LocalDateTime;

@Entity
public class Payment {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private LocalDateTime paymentDateTime;

  @ManyToOne
  @JoinColumn(name = "Booking_FK")
  private Booking booking;

  @ManyToOne
  @JoinColumn(name = "PaymentStatus_category_FK")
  private PaymentStatus paymentStatus;

  public Payment() {}

  public Payment(
      Long id, LocalDateTime paymentDateTime, Booking booking, PaymentStatus paymentStatus) {
    setId(id);
    setPaymentDateTime(paymentDateTime);
    setBooking(booking);
    setPaymentStatus(paymentStatus);
  }

  public Payment(LocalDateTime paymentDateTime, Booking booking, PaymentStatus paymentStatus) {
    setPaymentDateTime(paymentDateTime);
    setBooking(booking);
    setPaymentStatus(paymentStatus);
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

  public Booking getBooking() {
    return this.booking;
  }

  public void setBooking(Booking booking) {
    this.booking = booking;
  }

  public PaymentStatus getPaymentStatus() {
    return paymentStatus;
  }

  public void setPaymentStatus(PaymentStatus paymentStatus) {
    this.paymentStatus = paymentStatus;
  }

  @Override
  public String toString() {
    return "Payment [id="
        + id
        + ", paymentDateTime="
        + paymentDateTime
        + ", booking="
        + booking
        + ", paymentStatus="
        + paymentStatus
        + "]";
  }
}
