package com.vf.eventhubserver.order;

import java.sql.Timestamp;
import java.util.Set;
import com.vf.eventhubserver.persona.Customer;
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
    private Timestamp bookingCreationTimestamp;

    /**
     * @Description: the calculated total price for the booking, calculated based on the event's
     *               pricing
     */
    private double totalPriceHt;

    @OneToMany(cascade = {CascadeType.MERGE, CascadeType.REMOVE}, orphanRemoval = true)
    @JoinColumn(name = "Booking_FK")
    private Set<TicketReservation> reservations;

    @ManyToOne
    @JoinColumn(name = "Customer_FK")
    private Customer customer;

    public Booking() {}

    public Booking(Long id, Timestamp bookingCreationTimestamp, Customer customer,
            Set<TicketReservation> reservations) {
        setId(id);
        setBookingCreationTimestamp(bookingCreationTimestamp);
        setCustomer(customer);
        setReservations(reservations);
    }

    public Booking(Timestamp bookingCreationTimestamp, Customer customer,
            Set<TicketReservation> reservations) {
        setBookingCreationTimestamp(bookingCreationTimestamp);
        setCustomer(customer);
        setReservations(reservations);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public double getTotalPriceHt() {
        return totalPriceHt;
    }

    public Timestamp getBookingCreationTimestamp() {
        return bookingCreationTimestamp;
    }

    public void setBookingCreationTimestamp(Timestamp bookingCreationTimestamp) {
        this.bookingCreationTimestamp = bookingCreationTimestamp;
    }

    public Set<TicketReservation> getReservations() {
        return reservations;
    }

    public void setReservations(Set<TicketReservation> reservations) {
        this.reservations = reservations;
        totalPriceHt = 0;
        for (TicketReservation reservation : reservations) {
            totalPriceHt += reservation.getId().getSeatId().getCategoryTariff().getTarification()
                    .getBasePrice();
        }
    }

    public void addReservation(TicketReservation reservation) {
        this.reservations.add(reservation);
        totalPriceHt += reservation.getId().getSeatId().getCategoryTariff().getTarification()
                .getBasePrice();
    }

    public void removeReservation(TicketReservation reservation) {
        this.reservations.remove(reservation);
        totalPriceHt -= reservation.getId().getSeatId().getCategoryTariff().getTarification()
                .getBasePrice();
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
        return "Booking [id=" + id + ", bookingCreationTimestamp=" + bookingCreationTimestamp
                + ", totalPriceHt=" + totalPriceHt + ", reservations=" + reservations
                + ", customer=" + customer + "]";
    }

}
