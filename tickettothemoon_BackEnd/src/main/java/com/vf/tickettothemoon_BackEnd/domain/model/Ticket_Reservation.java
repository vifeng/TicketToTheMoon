package com.vf.tickettothemoon_BackEnd.domain.model;

import java.io.Serializable;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;

/**
 * This is an association class between Seat and SessionEvent. It has a composite keys which can be
 * found in Ticket_ReservationId class.
 */
@Entity
public class Ticket_Reservation implements Serializable {

    // in the repository dont forget to change Long to Ticket_ReservationKey as such
    // JpaRepository<Ticket_Reservation, Ticket_ReservationKey>
    @EmbeddedId
    private Ticket_ReservationKey id;

    // attributes
    private boolean isBooked = false;

    public Ticket_Reservation() {}

    public Ticket_Reservation(Ticket_ReservationKey ticket_ReservationKey, boolean isBooked) {
        this.id = ticket_ReservationKey;
        setIsBooked(isBooked);
    }

    // TOCHECK: is this constructor needed?
    public Ticket_Reservation(boolean isBooked) {
        setIsBooked(isBooked);
    }
    ///////////////////////////////
    // Composite key getters //
    /////////////////////////////

    public Ticket_ReservationKey getId() {
        return id;
    }

    public void setId(Ticket_ReservationKey id) {
        this.id = id;
    }

    public Seat getSeatId() {
        return this.id.getSeatId();
    }

    public SessionEvent getSessionEventId() {
        return this.id.getSessionEventId();
    }


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
        Ticket_Reservation other = (Ticket_Reservation) obj;
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
