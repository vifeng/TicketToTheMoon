package com.vf.tickettothemoon_BackEnd.domain.model;

import java.sql.Timestamp;
import java.util.Set;
import org.springframework.lang.NonNull;
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


    // TODO_LOW: ? this variable should be in service maybe?
    /**
     * @Description: the calculated total price for the booking, calculated based on the event's
     *               pricing
     */
    private double total_price_ht;

    // relationships
    // OneToMany unidirectional with Ticket_Reservation
    // TODO_HIGH: tester les cascades. Si on supprime un booking, ça doit supprimer les
    // Ticket_reservation sauf si le payment a été fait auquel cas il est impossible de supprimer
    // car on veux garder un historique des achats. Pour info, le cascade.all ne marche pas-je ne
    // sais pas pourquoi pour l'instant
    @OneToMany(cascade = {CascadeType.MERGE, CascadeType.REMOVE}, orphanRemoval = true)
    @JoinColumn(name = "Booking_FK")
    private Set<Ticket_Reservation> reservations;

    // TOCHECK: ManyToOne ?
    // ManyToOne unidirectional with Customer
    @ManyToOne
    @JoinColumn(name = "Customer_FK")
    private Customer customer;



    // constructors
    public Booking() {}


    public Booking(Long id, Timestamp booking_creationTimestamp, Customer customer,
            @NonNull Set<Ticket_Reservation> reservations) {
        setId(id);
        setBooking_creationTimestamp(booking_creationTimestamp);
        setCustomer(customer);
        setReservations(reservations);
    }

    public Booking(Timestamp booking_creationTimestamp, Customer customer,
            @NonNull Set<Ticket_Reservation> reservations) {
        setBooking_creationTimestamp(booking_creationTimestamp);
        setCustomer(customer);
        setReservations(reservations);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public double getTotal_price_ht() {
        return total_price_ht;
    }

    public Timestamp getBooking_creationTimestamp() {
        return booking_creationTimestamp;
    }

    public void setBooking_creationTimestamp(Timestamp booking_creationTimestamp) {
        this.booking_creationTimestamp = booking_creationTimestamp;
    }


    ///////////////////
    // RELATIONSHIPS //
    ///////////////////


    // OneToMany unidirectional setup with Ticket_Reservation

    public Set<Ticket_Reservation> getReservations() {
        return reservations;
    }

    public void setReservations(@NonNull Set<Ticket_Reservation> reservations) {
        this.reservations = reservations;
        total_price_ht = 0;
        for (Ticket_Reservation reservation : reservations) {
            // TODO_LOW: DISCOUNT - how should we calculate the discount ?
            total_price_ht += reservation.getId().getSeatId().getCategoryTariff().getTarification()
                    .getBasePrice();
        }
    }

    public void addReservation(@NonNull Ticket_Reservation reservation) {
        this.reservations.add(reservation);
        // TODO_LOW: DISCOUNT - how should we calculate the discount ?
        total_price_ht += reservation.getId().getSeatId().getCategoryTariff().getTarification()
                .getBasePrice();
    }

    public void removeReservation(@NonNull Ticket_Reservation reservation) {
        if (reservation != null) {
            this.reservations.remove(reservation);
            // TODO_LOW: DISCOUNT - how should we calculate the discount ?
            total_price_ht -= reservation.getId().getSeatId().getCategoryTariff().getTarification()
                    .getBasePrice();
        }
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }



    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }


    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Booking other = (Booking) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }


    @Override
    public String toString() {
        return "Booking [id=" + id + ", booking_creationTimestamp=" + booking_creationTimestamp
                + ", total_price_ht=" + total_price_ht + ", reservations=" + reservations
                + ", customer=" + customer + "]";
    }



}
