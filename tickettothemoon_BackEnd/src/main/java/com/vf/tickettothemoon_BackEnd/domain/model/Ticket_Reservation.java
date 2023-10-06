package com.vf.tickettothemoon_BackEnd.domain.model;

import java.io.Serializable;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;

/**
 * This is an association class between Seat and SessionEvent. It has a composite keys which can be
 * found in Ticket_ReservationId class.
 */
@Entity
public class Ticket_Reservation implements Serializable {

    @EmbeddedId
    private Ticket_ReservationId id;
    private boolean isBooked = false;

    // relationships

    // composite keys
    @ManyToOne
    @MapsId("seatId")
    private Seat seat;

    @ManyToOne
    @MapsId("sessionEventId")
    private SessionEvent sessionEvent;

    public Ticket_Reservation() {}

    public Ticket_Reservation(Ticket_ReservationId id, SessionEvent sessionEvent, Seat seat,
            boolean isBooked) {
        this.id = id;
        setSessionEvent(sessionEvent);
        setSeat(seat);
        setIsBooked(isBooked);
    }



    public Ticket_ReservationId getId() {
        return id;
    }

    public void setId(Ticket_ReservationId id) {
        this.id = id;
    }

    public boolean getIsBooked() {
        return isBooked;
    }

    public void setIsBooked(boolean isBooked) {
        this.isBooked = isBooked;
    }

    ///////////////////
    // RELATIONSHIPS //
    ///////////////////

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
        return "Ticket_Reservation [id=" + id + ", seat=" + seat + ", sessionEvent=" + sessionEvent
                + "]";
    }

}
