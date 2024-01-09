package com.vf.tickettothemoon_BackEnd.domain.model;

import java.io.Serializable;
import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Embeddable
public class Ticket_ReservationKey implements Serializable {

    // composite keys
    @ManyToOne
    @JoinColumn(name = "seat_id")
    private Seat seatId;

    @ManyToOne
    @JoinColumn(name = "session_event_id")
    private SessionEvent sessionEventId;

    public Ticket_ReservationKey() {}

    public Ticket_ReservationKey(Seat seatId, SessionEvent sessionEventId) {
        this.seatId = seatId;
        this.sessionEventId = sessionEventId;
    }

    // the fields within the embeddable class should be made immutable (i.e., no
    // setters, or the
    // setters should be protected/private) to ensure that the key remains
    // consistent throughout the
    // entity's lifecycle.

    public Seat getSeatId() {
        return seatId;
    }

    public SessionEvent getSessionEventId() {
        return sessionEventId;
    }

    public void setSeatId(Seat seatId) {
        this.seatId = seatId;
    }

    public void setSessionEventId(SessionEvent sessionEventId) {
        this.sessionEventId = sessionEventId;
    }

    // hashCode and equals are needed for composite keys
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((seatId == null) ? 0 : seatId.hashCode());
        result = prime * result + ((sessionEventId == null) ? 0 : sessionEventId.hashCode());
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
        Ticket_ReservationKey other = (Ticket_ReservationKey) obj;
        if (seatId == null) {
            if (other.seatId != null)
                return false;
        } else if (!seatId.equals(other.seatId))
            return false;
        if (sessionEventId == null) {
            if (other.sessionEventId != null)
                return false;
        } else if (!sessionEventId.equals(other.sessionEventId))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Ticket_ReservationKey [seatId=" + seatId + ", sessionEventId=" + sessionEventId
                + "]";
    }


}
