package com.vf.tickettothemoon_BackEnd.domain.model;

import java.io.Serializable;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class Ticket_ReservationKey implements Serializable {

    @Column(name = "seat_id")
    private Long seatId;
    @Column(name = "session_event_id")
    private Long sessionEventId;

    public Ticket_ReservationKey() {}

    public Ticket_ReservationKey(Long seatId, Long sessionEventId) {
        this.seatId = seatId;
        this.sessionEventId = sessionEventId;
    }

    // the fields within the embeddable class should be made immutable (i.e., no
    // setters, or the
    // setters should be protected/private) to ensure that the key remains
    // consistent throughout the
    // entity's lifecycle.

    public Long getSeatId() {
        return seatId;
    }

    public Long getSessionEventId() {
        return sessionEventId;
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


}
