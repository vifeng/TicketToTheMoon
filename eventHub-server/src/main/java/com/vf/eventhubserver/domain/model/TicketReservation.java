package com.vf.eventhubserver.domain.model;

import java.io.Serializable;
import org.springframework.lang.NonNull;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;

/**
 * This is an association class between Seat and SessionEvent. It has a composite keys which can be
 * found in Ticket_ReservationId class.
 */
@Entity
public class TicketReservation implements Serializable {

    // in the repository dont forget to change Long to Ticket_ReservationKey as such
    // JpaRepository<Ticket_Reservation, Ticket_ReservationKey>
    @EmbeddedId
    private TicketReservationKey id;

    // attributes
    private boolean isBooked = false;

    public TicketReservation() {}

    public TicketReservation(TicketReservationKey ticket_ReservationKey, boolean isBooked) {
        this.id = ticket_ReservationKey;
        setIsBooked(isBooked);
    }

    // TOCHECK: is this constructor needed?
    public TicketReservation(boolean isBooked) {
        setIsBooked(isBooked);
    }
    ///////////////////////////////
    // Composite key getters //
    /////////////////////////////

    public TicketReservationKey getId() {
        return id;
    }

    public void setId(@NonNull TicketReservationKey id) {
        this.id = id;
    }

    // public Seat getSeatId() {
    // return this.id.getSeatId();
    // }

    // public SessionEvent getSessionEventId() {
    // return this.id.getSessionEventId();
    // }


    // end of composite key getters


    public boolean getIsBooked() {
        return isBooked;
    }

    public void setIsBooked(boolean isBooked) {
        this.isBooked = isBooked;
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
        TicketReservation other = (TicketReservation) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Ticket_Reservation [id=" + id + ", isBooked=" + isBooked + "]";
    }

}
