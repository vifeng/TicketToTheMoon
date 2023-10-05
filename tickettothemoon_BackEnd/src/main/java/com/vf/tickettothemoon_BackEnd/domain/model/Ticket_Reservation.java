package com.vf.tickettothemoon_BackEnd.domain.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;

/**
 * This is an association class between Seat and SessionEvent. It has a composite keys which can be
 * found in Ticket_ReservationId class.
 */
@Entity
public class Ticket_Reservation implements Serializable {

    @EmbeddedId
    private final Ticket_ReservationId id;


    private Timestamp reservation_creationTimestamp;
    /**
     * @Description: the resulting date and time to track the expiryTime (set in minute) reservation
     *               expiration
     */
    private LocalDateTime reservation_expiryDateTime;
    // TODO_LOW : ? add a method to calculate the expiryDateTime based on the
    // reservation_creationTimestamp and the expiryTime in the service and put the constant
    // ExpiryTime in the service. we might need to have to SessionEvent to check if the event is
    // passed.
    /**
     * @Description: the 00-minute reservation expiration
     */
    private LocalDateTime expiryTime;

    // TODO_LOW: ? this variable should be in service maybe?
    /**
     * @Description: the calculated total price for the reservation, calculated based on the event's
     *               pricing
     */
    private double total_price_ht;

    // relationships
    @ManyToOne
    @JoinColumn(name = "Customer_FK")
    private Customer customer;

    // composite keys
    @ManyToOne
    @MapsId("seatId")
    private Seat seat;

    @ManyToOne
    @MapsId("sessionId")
    private SessionEvent sessionEvent;



    public Ticket_Reservation() {
        this.id = null;
    }



    public Ticket_Reservation(Ticket_ReservationId id, Timestamp reservation_creationTimestamp,
            LocalDateTime reservation_expiryDateTime, LocalDateTime expiryTime,
            double total_price_ht, Customer customer, SessionEvent sessionEvent, Seat seat) {
        this.id = id;
        setReservation_creationTimestamp(reservation_creationTimestamp);
        setReservation_expiryDateTime(reservation_expiryDateTime);
        setExpiryTime(expiryTime);
        setTotal_price_ht(total_price_ht);
        setCustomer(customer);
        setSessionEvent(sessionEvent);
    }

    public Ticket_Reservation(Timestamp reservation_creationTimestamp,
            LocalDateTime reservation_expiryDateTime, LocalDateTime expiryTime,
            double total_price_ht, Customer customer, SessionEvent sessionEvent, Seat seat) {
        setReservation_creationTimestamp(reservation_creationTimestamp);
        setReservation_expiryDateTime(reservation_expiryDateTime);
        setExpiryTime(expiryTime);
        setTotal_price_ht(total_price_ht);
        setCustomer(customer);
        setSessionEvent(sessionEvent);
        addSeats(seat);
    }



    public Ticket_ReservationId getId() {
        return id;
    }



    public Timestamp getReservation_creationTimestamp() {
        return reservation_creationTimestamp;
    }



    public void setReservation_creationTimestamp(Timestamp reservation_creationTimestamp) {
        this.reservation_creationTimestamp = new Timestamp(System.currentTimeMillis());
    }



    public LocalDateTime getReservation_expiryDateTime() {
        return reservation_expiryDateTime;
    }



    public void setReservation_expiryDateTime(LocalDateTime reservation_expiryDateTime) {
        this.reservation_expiryDateTime = reservation_expiryDateTime;
    }



    public LocalDateTime getExpiryTime() {
        return expiryTime;
    }



    public void setExpiryTime(LocalDateTime expiryTime) {
        this.expiryTime = expiryTime;
    }



    public double getTotal_price_ht() {
        return total_price_ht;
    }



    public void setTotal_price_ht(double total_price_ht) {
        this.total_price_ht = total_price_ht;
    }


    ///////////////////
    // RELATIONSHIPS //
    ///////////////////

    public Customer getCustomer() {
        return customer;
    }



    public void setCustomer(Customer customer) {
        this.customer = customer;
    }



    public SessionEvent getSessionEvent() {
        return sessionEvent;
    }



    public void setSessionEvent(SessionEvent sessionEvent) {
        this.sessionEvent = sessionEvent;
    }



    public Seat getSeat() {
        return seat;
    }



    public void setSeat(Seat seat) {
        this.seat = seat;
    }



    @Override
    public String toString() {
        return "Ticket_Reservation [id=" + id + ", reservation_creationTimestamp="
                + reservation_creationTimestamp + ", reservation_expiryDateTime="
                + reservation_expiryDateTime + ", expiryTime=" + expiryTime + ", total_price_ht="
                + total_price_ht + ", customer=" + customer + ", seat=" + seat + ", sessionEvent="
                + sessionEvent + "]";
    }



}
