package com.vf.tickettothemoon_BackEnd.domain.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;

@Entity
public class Ticket_Reservation implements Serializable {

    @Id
    @GeneratedValue
    private Long id;

    private Timestamp reservation_creationTimestamp;
    /**
     * @Description: the resulting date and time to track the expiryTime (set in minute) reservation
     *               expiration
     */
    private LocalDate reservation_expiryDateTime;
    // TODO_LOW : ? add a method to calculate the expiryDateTime based on the
    // reservation_creationTimestamp and the expiryTime in the service and put the constant
    // ExpiryTime in the service. we might need to have to SessionEvent to check if the event is
    // passed.
    /**
     * @Description: the 00-minute reservation expiration
     */
    private LocalDate expiryTime;

    // TODO_LOW: ? should be in service ?
    /**
     * @Description: the calculated total price for the reservation, calculated based on the event's
     *               pricing
     */
    private double total_price_ht;

    // TODO: finish the relationship setup
    @ManyToOne
    @JoinColumn(name = "Customer_FK")
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "SessionEvent_FK")
    private SessionEvent sessionEvent;


    // TODO_HIGH : add sessionEvent_id to the joinTable

    /**
     * constraint : if a Reservation is deleted the associated seat should not be deleted and vice
     * versa. many-to-many Bidirectional owner side of the relationship (@JoinTable)
     */
    @ManyToMany(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH})
    @JoinTable(name = "Ticket_Reservation_Seat",
            joinColumns = @JoinColumn(name = "Ticket_Reservation_FK"),
            inverseJoinColumns = @JoinColumn(name = "Seat_FK"))
    @JsonIgnoreProperties("ticket_Reservations")
    private List<Seat> seats = new ArrayList<>();



    public Ticket_Reservation() {}



    public Ticket_Reservation(Long id, Timestamp reservation_creationTimestamp,
            LocalDate reservation_expiryDateTime, LocalDate expiryTime, double total_price_ht,
            Customer customer, SessionEvent sessionEvent) {
        setId(id);
        setReservation_creationTimestamp(reservation_creationTimestamp);
        setReservation_expiryDateTime(reservation_expiryDateTime);
        setExpiryTime(expiryTime);
        setTotal_price_ht(total_price_ht);
        setCustomer(customer);
        setSessionEvent(sessionEvent);
    }

    public Ticket_Reservation(Timestamp reservation_creationTimestamp,
            LocalDate reservation_expiryDateTime, LocalDate expiryTime, double total_price_ht,
            Customer customer, SessionEvent sessionEvent) {
        setReservation_creationTimestamp(reservation_creationTimestamp);
        setReservation_expiryDateTime(reservation_expiryDateTime);
        setExpiryTime(expiryTime);
        setTotal_price_ht(total_price_ht);
        setCustomer(customer);
        setSessionEvent(sessionEvent);
    }



    public Long getId() {
        return id;
    }



    public void setId(Long id) {
        this.id = id;
    }



    public Timestamp getReservation_creationTimestamp() {
        return reservation_creationTimestamp;
    }



    public void setReservation_creationTimestamp(Timestamp reservation_creationTimestamp) {
        this.reservation_creationTimestamp = new Timestamp(System.currentTimeMillis());
    }



    public LocalDate getReservation_expiryDateTime() {
        return reservation_expiryDateTime;
    }



    public void setReservation_expiryDateTime(LocalDate reservation_expiryDateTime) {
        this.reservation_expiryDateTime = reservation_expiryDateTime;
    }



    public LocalDate getExpiryTime() {
        return expiryTime;
    }



    public void setExpiryTime(LocalDate expiryTime) {
        this.expiryTime = expiryTime;
    }



    public double getTotal_price_ht() {
        return total_price_ht;
    }



    public void setTotal_price_ht(double total_price_ht) {
        this.total_price_ht = total_price_ht;
    }



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


    // set up many to many bidirectionnal relationship

    public void addSeats(Seat seat) {
        seats.add(seat);
        seat.getTicket_Reservations().add(this);
    }



    public void removeSeats(Seat seat) {
        if (seats != null)
            seats.remove(seat);
        seat.getTicket_Reservations().remove(this);
    }



}
