package com.vf.tickettothemoon_BackEnd.domain.model;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

@Entity
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Timestamp booking_creationTimestamp;
    /**
     * @Description: the resulting date and time to track the expiryTime (set in minute) booking
     *               expiration
     */
    private LocalDateTime booking_expiryDateTime;
    // TODO_LOW : ? add a method to calculate the expiryDateTime based on the
    // booking_creationTimestamp and the expiryTime in the service and put the
    // constant
    // ExpiryTime in the service. we might need to have to SessionEvent to check if
    // the event is
    // passed.
    /**
     * @Description: the 00-minute booking expiration
     */
    private LocalDateTime expiryTime;

    // TODO_LOW: ? this variable should be in service maybe?
    /**
     * @Description: the calculated total price for the booking, calculated based on the event's
     *               pricing
     */
    private double total_price_ht;

    // relationships
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "Booking_FK")
    private Set<Ticket_Reservation> reservations = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "Customer_FK")
    private Customer customer;

    // constructors
    public Booking() {}

    public Booking(Long id, Timestamp booking_creationTimestamp,
            LocalDateTime booking_expiryDateTime, LocalDateTime expiryTime, double total_price_ht,
            Customer customer, Set<Ticket_Reservation> reservations) {
        setId(id);
        setBooking_creationTimestamp(booking_creationTimestamp);
        setBooking_expiryDateTime(booking_expiryDateTime);
        setExpiryTime(expiryTime);
        setTotal_price_ht(total_price_ht);
        setCustomer(customer);
        setReservations(reservations);
    }

    public Booking(Timestamp booking_creationTimestamp, LocalDateTime booking_expiryDateTime,
            LocalDateTime expiryTime, double total_price_ht, Customer customer,
            Set<Ticket_Reservation> reservations) {
        setBooking_creationTimestamp(booking_creationTimestamp);
        setBooking_expiryDateTime(booking_expiryDateTime);
        setExpiryTime(expiryTime);
        setTotal_price_ht(total_price_ht);
        setCustomer(customer);
        setReservations(reservations);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Timestamp getBooking_creationTimestamp() {
        return booking_creationTimestamp;
    }

    public void setBooking_creationTimestamp(Timestamp booking_creationTimestamp) {
        this.booking_creationTimestamp = booking_creationTimestamp;
    }

    public LocalDateTime getBooking_expiryDateTime() {
        return booking_expiryDateTime;
    }

    public void setBooking_expiryDateTime(LocalDateTime booking_expiryDateTime) {
        this.booking_expiryDateTime = booking_expiryDateTime;
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

    public Set<Ticket_Reservation> getReservations() {
        return reservations;
    }

    public void setReservations(Set<Ticket_Reservation> reservations) {
        this.reservations = reservations;
    }

    public void addReservation(Ticket_Reservation reservation) {
        this.reservations.add(reservation);
    }

    public void removeReservation(Ticket_Reservation reservation) {
        if (reservation != null)
            this.reservations.remove(reservation);
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    @Override
    public String toString() {
        return "Booking [id=" + id + ", booking_creationTimestamp=" + booking_creationTimestamp
                + ", booking_expiryDateTime=" + booking_expiryDateTime + ", expiryTime="
                + expiryTime + ", total_price_ht=" + total_price_ht + ", reservations="
                + reservations + ", customer=" + customer + "]";
    }

}
