package com.vf.eventhubserver.domain.model;

import java.io.Serializable;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;

@Entity
public class TicketReservation implements Serializable {

    @EmbeddedId
    private TicketReservationKey id;

    private boolean isBooked = false;

    public TicketReservation() {}

    public TicketReservation(TicketReservationKey ticketReservationKey, boolean isBooked) {
        this.id = ticketReservationKey;
        setIsBooked(isBooked);
    }

    public TicketReservation(boolean isBooked) {
        setIsBooked(isBooked);
    }

    public TicketReservationKey getId() {
        return id;
    }

    public void setId(TicketReservationKey id) {
        this.id = id;
    }

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
